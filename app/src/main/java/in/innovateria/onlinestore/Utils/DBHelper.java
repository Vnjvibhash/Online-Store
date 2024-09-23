package in.innovateria.onlinestore.Utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import in.innovateria.onlinestore.Activities.MainActivity;
import in.innovateria.onlinestore.Models.BannerModel;
import in.innovateria.onlinestore.Models.CategoryModel;
import in.innovateria.onlinestore.Models.OrderModel;
import in.innovateria.onlinestore.Models.ProductModel;
import in.innovateria.onlinestore.Models.UserModel;

public class DBHelper {
    private Context context;
    private DatabaseReference bannerReference;
    private DatabaseReference categoryReference;
    private DatabaseReference productReference;
    private DatabaseReference ordersReference;
    private DatabaseReference usersReference;
    private CartManager cartManager;

    public DBHelper(Context context) {
        this.context = context;
        this.bannerReference = FirebaseDatabase.getInstance().getReference("Banner");
        this.categoryReference = FirebaseDatabase.getInstance().getReference("Category");
        this.productReference = FirebaseDatabase.getInstance().getReference("Items");
        this.ordersReference = FirebaseDatabase.getInstance().getReference("Orders");
        this.usersReference = FirebaseDatabase.getInstance().getReference("Users");
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

    public void submitOrderToFirebase(OrderModel orderModel) {
        cartManager = new CartManager(context);
        String orderId = ordersReference.push().getKey();
        if (orderId != null) {
            orderModel.setOrderId(orderId);
            ordersReference.child(orderId).setValue(orderModel)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Order placed successfully!", Toast.LENGTH_SHORT).show();
                            cartManager.clearCart();
                            context.startActivity(new Intent(context, MainActivity.class));
                        } else {
                            Toast.makeText(context, "Failed to place order.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    public void createUserToFirebase(Map<String, Object> userMap) {
        String email = (String) userMap.get("email");

        // Query to check if the email already exists
        usersReference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Constant.saveUserMapToPreferences(userMap, context);
                    Toast.makeText(context, "Logged In successfully!", Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, MainActivity.class));
                } else {
                    String id = usersReference.push().getKey();
                    if (id != null) {
                        userMap.put("id", id); // Add the ID to the map
                        usersReference.child(id).setValue(userMap)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        Constant.saveUserMapToPreferences(userMap, context);
                                        Toast.makeText(context, "Logged In successfully!", Toast.LENGTH_SHORT).show();
                                        context.startActivity(new Intent(context, MainActivity.class));
                                    } else {
                                        Toast.makeText(context, "Failed to Log in", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors
                Toast.makeText(context, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Callback interface
    public interface DataCallback<T> {
        void onSuccess(T data);
        void onError(String error);
    }
}

