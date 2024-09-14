package in.innovateria.onlinestore.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import in.innovateria.onlinestore.Models.ProductModel;

public class CartManager {
    private SharedPreferences sharedPreferences;
    private static final String CART_PREFS = "CartPrefs";

    public CartManager(Context context) {
        sharedPreferences = context.getSharedPreferences(CART_PREFS, Context.MODE_PRIVATE);
    }

    // Save the product to the cart (JSON serialization can be used here)
    public void addToCart(ProductModel product) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String productKey = "product_" + product.getId();
        editor.putString(productKey, new Gson().toJson(product));
        editor.apply();
    }

    // Update the product in the cart
    public void updateCart(ProductModel product) {
        addToCart(product);
    }

    // Get cart items (deserialization)
    public List<ProductModel> getCartItems() {
        Map<String, ?> allEntries = sharedPreferences.getAll();
        List<ProductModel> cartItems = new ArrayList<>();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            ProductModel product = new Gson().fromJson(entry.getValue().toString(), ProductModel.class);
            cartItems.add(product);
        }
        return cartItems;
    }

    // Get a specific item from the cart by categoryId (or unique ID)
    public ProductModel getCartItemById(int id) {
        String productKey = "product_" + id;
        String productJson = sharedPreferences.getString(productKey, null);
        if (productJson != null) {
            return new Gson().fromJson(productJson, ProductModel.class);
        }
        return null;
    }

    // Remove an item from the cart
    public void removeFromCart(ProductModel product) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("product_" + product.getId());
        editor.apply();
    }

    // Clear the cart
    public void clearCart() {
        sharedPreferences.edit().clear().apply();
    }
}


