package in.innovateria.onlinestore.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.List;

import in.innovateria.onlinestore.Adapters.BannerAdapter;
import in.innovateria.onlinestore.Adapters.CategoryAdapter;
import in.innovateria.onlinestore.Adapters.ProductAdapter;
import in.innovateria.onlinestore.Models.BannerModel;
import in.innovateria.onlinestore.Models.CategoryModel;
import in.innovateria.onlinestore.Models.ProductModel;
import in.innovateria.onlinestore.R;
import in.innovateria.onlinestore.Utils.AutoSlider;
import in.innovateria.onlinestore.Utils.CartManager;
import in.innovateria.onlinestore.Utils.Constant;
import in.innovateria.onlinestore.Utils.DBHelper;
import in.innovateria.onlinestore.Utils.PageTransformer;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private BannerAdapter bannerAdapter;
    private CategoryAdapter categoryAdapter;
    private ProductAdapter productAdapter;
    private ProgressBar bannerProgressBar, categoryProgressBar, recommendationProgressBar;
    private RecyclerView categoryRecyclerView, recommendationRecyclerView;
    private DotsIndicator dotIndicator;
    private AutoSlider autoSlider;
    private DBHelper dbHelper;
    TextView cartBadgeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Constant.setStatusBarColor(this, R.color.puerto_rico);

        cartBadgeTextView = findViewById(R.id.cartBadge);

        bannerProgressBar = findViewById(R.id.bannerProgressBar);
        categoryProgressBar = findViewById(R.id.categoryProgressBar);
        recommendationProgressBar = findViewById(R.id.recommendationProgressBar);
        categoryRecyclerView = findViewById(R.id.categoryRecyclerView);
        recommendationRecyclerView = findViewById(R.id.recommendationRecyclerView);
        RelativeLayout cartBtn = findViewById(R.id.cartBtn);

        viewPager = findViewById(R.id.viewPager);
        dotIndicator = findViewById(R.id.dotIndicator);

        dbHelper = new DBHelper(this);

        getBannerData();
        getCategoryData();
        getRecommendationData();

        cartBtn.setOnClickListener(v->{
            startActivity(new Intent(this,CartActivity.class));
        });
    }

    private void getBannerData() {
        bannerProgressBar.setVisibility(View.VISIBLE);

        dbHelper.fetchBannerData(new DBHelper.DataCallback<List<BannerModel>>() {
            @Override
            public void onSuccess(List<BannerModel> bannerList) {
                bannerProgressBar.setVisibility(View.GONE);
                bannerAdapter = new BannerAdapter(MainActivity.this, bannerList);
                viewPager.setAdapter(bannerAdapter);

                viewPager.setPageTransformer(new PageTransformer());

                dotIndicator.attachTo(viewPager);
                dotIndicator.setVisibility(View.VISIBLE);

                autoSlider = new AutoSlider(5000);
                autoSlider.startAutoSlide(viewPager, bannerList);
            }

            @Override
            public void onError(String error) {
                bannerProgressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCategoryData() {
        categoryProgressBar.setVisibility(View.VISIBLE);
        dbHelper.fetchCategoryData(new DBHelper.DataCallback<List<CategoryModel>>() {
            @Override
            public void onSuccess(List<CategoryModel> categoryList) {
                categoryProgressBar.setVisibility(View.GONE);
                categoryRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                categoryAdapter = new CategoryAdapter(MainActivity.this, categoryList);
                categoryRecyclerView.setAdapter(categoryAdapter);
            }

            @Override
            public void onError(String error) {
                Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getRecommendationData() {
        recommendationProgressBar.setVisibility(View.VISIBLE);
        dbHelper.fetchProductData(new DBHelper.DataCallback<List<ProductModel>>() {
            @Override
            public void onSuccess(List<ProductModel> productList) {
                recommendationProgressBar.setVisibility(View.GONE);
                List<ProductModel> filteredProductList = new ArrayList<>();
                for (ProductModel product : productList) {
                    if (product.getShowRecommended()) {
                        filteredProductList.add(product);
                    }
                }
                productAdapter = new ProductAdapter(MainActivity.this, filteredProductList);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
                recommendationRecyclerView.setLayoutManager(gridLayoutManager);
                recommendationRecyclerView.setAdapter(productAdapter);
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        autoSlider.stopAutoSlide(); // Stop auto-slide when activity is destroyed
    }

}