package in.innovateria.onlinestore.Utils;

import android.content.Context;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

import in.innovateria.onlinestore.Models.BannerModel;
import in.innovateria.onlinestore.Models.CategoryModel;
import in.innovateria.onlinestore.Models.ProductModel;

public class DBHelper {
    private Context context;
    private DatabaseReference bannerReference;
    private DatabaseReference categoryReference;
    private DatabaseReference productReference;

    public DBHelper(Context context) {
        this.context = context;
        this.bannerReference = FirebaseDatabase.getInstance().getReference("Banner");
        this.categoryReference = FirebaseDatabase.getInstance().getReference("Category");
        this.productReference = FirebaseDatabase.getInstance().getReference("Items");
    }

    public void fetchBannerData(DataCallback<List<BannerModel>> callback) {
        bannerReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<BannerModel> bannerList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    BannerModel banner = snapshot.getValue(BannerModel.class);
                    if (banner != null) {
                        bannerList.add(banner);
                    }
                }
                if (callback != null) {
                    callback.onSuccess(bannerList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                if (callback != null) {
                    callback.onError(databaseError.getMessage());
                }
            }
        });
    }

    public void fetchCategoryData(DataCallback<List<CategoryModel>> callback) {
        categoryReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<CategoryModel> categoryList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CategoryModel category = snapshot.getValue(CategoryModel.class);
                    if (category != null) {
                        categoryList.add(category);
                    }
                }
                if (callback != null) {
                    callback.onSuccess(categoryList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                if (callback != null) {
                    callback.onError(databaseError.getMessage());
                }
            }
        });
    }

    public void fetchProductData(DataCallback<List<ProductModel>> callback) {
        productReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ProductModel> productList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ProductModel product = snapshot.getValue(ProductModel.class);
                    if (product != null) {
                        productList.add(product);
                    }
                }
                if (callback != null) {
                    callback.onSuccess(productList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                if (callback != null) {
                    callback.onError(databaseError.getMessage());
                }
            }
        });
    }

    // Callback interface
    public interface DataCallback<T> {
        void onSuccess(T data);
        void onError(String error);
    }
}
