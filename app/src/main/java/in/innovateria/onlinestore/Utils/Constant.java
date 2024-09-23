package in.innovateria.onlinestore.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

public class Constant {
    public static void setStatusBarColor(Activity activity, int colorResId) {
        Window window = activity.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(activity, colorResId));
    }

    public static void updateCartBadge(Context context, TextView cartBadgeTextView) {
        CartManager cartManager = new CartManager(context);

        // Get the current number of items in the cart
        int itemCount = cartManager.getCartItemCount();
        updateBadgeText(cartBadgeTextView, itemCount);

        // Set up a listener for future cart updates
        cartManager.setOnCartUpdateListener(new CartManager.OnCartUpdateListener() {
            @Override
            public void onCartUpdated(int itemCount) {
                updateBadgeText(cartBadgeTextView, itemCount);
            }
        });
    }

    private static void updateBadgeText(TextView cartBadgeTextView, int itemCount) {
        if (itemCount > 0) {
            cartBadgeTextView.setVisibility(View.VISIBLE);
            cartBadgeTextView.setText(String.valueOf(itemCount));
        } else {
            cartBadgeTextView.setVisibility(View.GONE);
        }
    }

    public static void saveUserMapToPreferences(Map<String, Object> userMap, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userMap);

        editor.putString("userMap", json);
        editor.apply();
    }

    public Map<String, Object> getUserMapFromPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("userMap", null);

        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, Object>>() {}.getType();
            return gson.fromJson(json, type);
        }
        return null;
    }

    public static void clearUserPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
