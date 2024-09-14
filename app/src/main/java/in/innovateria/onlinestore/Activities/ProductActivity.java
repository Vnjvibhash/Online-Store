package in.innovateria.onlinestore.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import in.innovateria.onlinestore.Adapters.ProductAdapter;
import in.innovateria.onlinestore.Models.ProductModel;
import in.innovateria.onlinestore.R;
import in.innovateria.onlinestore.Utils.Constant;
import in.innovateria.onlinestore.Utils.DBHelper;

public class ProductActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private ProductAdapter productAdapter;
    private RecyclerView productListRecyclerView;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Constant.setStatusBarColor(this, R.color.puerto_rico);

        Intent intent = getIntent();
        String title = intent.getStringExtra("categoryTitle");
        int categoryID = intent.getIntExtra("categoryID", 0);

        dbHelper = new DBHelper(this);
        TextView titleText = findViewById(R.id.titleText);
        titleText.setText(title);
        ImageView backBtn = findViewById(R.id.backBtn);

        progressBar = findViewById(R.id.progressBar);
        productListRecyclerView = findViewById(R.id.productListRecyclerView);

        getProductData(categoryID);

        backBtn.setOnClickListener(v->{
            finish();
        });

    }

    private void getProductData(int categoryID) {
        progressBar.setVisibility(View.VISIBLE);
        dbHelper.fetchProductData(new DBHelper.DataCallback<List<ProductModel>>() {
            @Override
            public void onSuccess(List<ProductModel> productList) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ProductActivity.this, "Data Found", Toast.LENGTH_SHORT).show();
                List<ProductModel> filteredProductList = new ArrayList<>();
                for (ProductModel product : productList) {
                    if (product.getCategoryId() == categoryID) {
                        filteredProductList.add(product);
                    }
                }
                productAdapter = new ProductAdapter(ProductActivity.this, filteredProductList);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(ProductActivity.this, 2);
                productListRecyclerView.setLayoutManager(gridLayoutManager);
                productListRecyclerView.setAdapter(productAdapter);
            }

            @Override
            public void onError(String error) {
                Toast.makeText(ProductActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

}