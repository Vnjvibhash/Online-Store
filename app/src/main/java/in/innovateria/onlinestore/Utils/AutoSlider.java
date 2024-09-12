package in.innovateria.onlinestore.Utils;
import android.os.Handler;
import android.os.Looper;
import androidx.viewpager2.widget.ViewPager2;
import java.util.List;

import in.innovateria.onlinestore.Models.BannerModel;

public class AutoSlider {
    private Handler handler;
    private Runnable runnable;
    private int currentPage = 0;
    private int time;

    public AutoSlider(int time) {
        this.time = time;
    }

    public void startAutoSlide(ViewPager2 viewPager, List<BannerModel> bannerList) {
        if (viewPager == null || bannerList == null) {
            throw new IllegalArgumentException("ViewPager2 and BannerList cannot be null");
        }

        if (bannerList.isEmpty()) {
            return; // No banners to display, exit early
        }

        // Cancel any existing handler to prevent multiple auto-slide tasks
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }

        handler = new Handler(Looper.getMainLooper());
        runnable = new Runnable() {
            @Override
            public void run() {
                if (viewPager.getAdapter() != null && !bannerList.isEmpty()) {
                    if (currentPage >= bannerList.size()) {
                        currentPage = 0;
                    }
                    viewPager.setCurrentItem(currentPage++, true);
                }
                handler.postDelayed(this, time);
            }
        };

        handler.postDelayed(runnable, time);
    }

    public void stopAutoSlide() {
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }
}
