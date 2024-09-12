package in.innovateria.onlinestore.Utils;

import android.view.View;

import androidx.viewpager2.widget.ViewPager2;

public class PageTransformer implements ViewPager2.PageTransformer {

    @Override
    public void transformPage(View page, float position) {
        int pageWidth = page.getWidth();
        page.setTranslationX(-position * pageWidth);
        page.setAlpha(1 - Math.abs(position));
        page.setScaleX(1 - 0.3f * Math.abs(position));
        page.setScaleY(1 - 0.3f * Math.abs(position));
    }
}

