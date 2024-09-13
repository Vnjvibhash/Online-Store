package in.innovateria.onlinestore.Utils;

import android.app.Activity;
import android.os.Build;
import android.view.Window;

import androidx.core.content.ContextCompat;

public class Constant {
    public static void setStatusBarColor(Activity activity, int colorResId) {
        Window window = activity.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(activity, colorResId));
    }
}
