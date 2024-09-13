package in.innovateria.onlinestore.Utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

public class AnimationUtils {

    // Method to slide a view from the left side of the screen with fade-in
    public static void slideFromStart(View view, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidth = displayMetrics.widthPixels;

        // Start position outside the screen on the left
        float startPositionX = -view.getWidth() - screenWidth;
        float endPositionX = view.getX();

        // Create a TranslateAnimation for sliding in
        TranslateAnimation slideAnimation = new TranslateAnimation(startPositionX, endPositionX, 0f, 0f);
        slideAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        slideAnimation.setDuration(1000);

        // Create an AlphaAnimation for fading in
        AlphaAnimation fadeInAnimation = new AlphaAnimation(0f, 1f);
        fadeInAnimation.setDuration(1500);

        // Create an AnimationSet to combine both animations
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(slideAnimation);
        animationSet.addAnimation(fadeInAnimation);

        view.startAnimation(animationSet);
    }

    // Method to slide a view out to the right side of the screen with fade-out
    public static void slideToEndWithFadeOut(View view, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidth = displayMetrics.widthPixels;

        // Current position on the screen
        float startPositionX = view.getX();
        // End position outside the screen on the right
        float endPositionX = screenWidth;

        // Create a TranslateAnimation for sliding out
        TranslateAnimation slideAnimation = new TranslateAnimation(startPositionX, endPositionX, 0f, 0f);
        slideAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        slideAnimation.setDuration(1000);

        // Create an AlphaAnimation for fading out
        AlphaAnimation fadeOutAnimation = new AlphaAnimation(1f, 0f);
        fadeOutAnimation.setDuration(1500);

        // Create an AnimationSet to combine both animations
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(slideAnimation);
        animationSet.addAnimation(fadeOutAnimation);

        view.startAnimation(animationSet);
    }
}
