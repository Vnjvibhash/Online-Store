package in.innovateria.onlinestore.Utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

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
}
