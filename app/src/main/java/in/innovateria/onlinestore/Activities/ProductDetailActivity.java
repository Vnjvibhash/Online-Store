package in.innovateria.onlinestore.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import in.innovateria.onlinestore.Adapters.ImageAdapter;
import in.innovateria.onlinestore.Adapters.ModelAdapter;
import in.innovateria.onlinestore.Models.ProductModel;
import in.innovateria.onlinestore.R;
import in.innovateria.onlinestore.Utils.Constant;

public class ProductDetailActivity extends AppCompatActivity {
    private String selectedModel;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Constant.setStatusBarColor(this, R.color.puerto_rico);

        TextView titleText = findViewById(R.id.titleText);
        TextView priceText = findViewById(R.id.priceText);
        TextView ratingText = findViewById(R.id.ratingText);
        TextView descriptionText = findViewById(R.id.descriptionText);
        ImageView backBtn = findViewById(R.id.backBtn);
        ImageView productImageView = findViewById(R.id.productImage);
        RecyclerView productImgList = findViewById(R.id.productImgList);
        RecyclerView modelList = findViewById(R.id.modelList);
        AppCompatButton addToCartBtn = findViewById(R.id.addToCartBtn);

        ProductModel product = (ProductModel) getIntent().getSerializableExtra("productObject");

        if(product != null){
            // Set the main product details
            Glide.with(this)
                    .load(product.getPicUrl().get(0))
                    .apply(new RequestOptions().centerInside())
                    .into(productImageView);
            titleText.setText(product.getTitle());
            priceText.setText(String.format("$%d", product.getPrice()));
            ratingText.setText(String.valueOf(product.getRating()));
            descriptionText.setText(product.getDescription());

            // Setup the image RecyclerView
            ImageAdapter imageAdapter = new ImageAdapter(this, product.getPicUrl(), productImageView);
            productImgList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            productImgList.setAdapter(imageAdapter);

            // Setup the model RecyclerView
            ModelAdapter modelAdapter = new ModelAdapter(this, product.getModel(), this);
            modelList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            modelList.setAdapter(modelAdapter);
        }

        addToCartBtn.setOnClickListener(v->{
            Toast.makeText(this, "Model "+selectedModel+" is Selected", Toast.LENGTH_SHORT).show();
        });

        backBtn.setOnClickListener(v->{
            finish();
        });

    }

    public void setModel(String selectedModel) {
        this.selectedModel = selectedModel;
    }

}