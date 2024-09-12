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
    int TIME =3000;
    public AutoSlider(int timer) {
        this.TIME=timer;
    }

    public void startAutoSlide(ViewPager2 viewPager, List<BannerModel> bannerList) {
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
                handler.postDelayed(this, TIME);
            }
        };
        handler.postDelayed(runnable, TIME);
    }
}
