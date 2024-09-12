package in.innovateria.onlinestore.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.List;

import in.innovateria.onlinestore.Adapters.BannerAdapter;
import in.innovateria.onlinestore.Models.BannerModel;
import in.innovateria.onlinestore.R;
import in.innovateria.onlinestore.Utils.AutoSlider;
import in.innovateria.onlinestore.Utils.PageTransformer;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private BannerAdapter bannerAdapter;
    private List<BannerModel> bannerList = new ArrayList<>();
    private ProgressBar bannerProgressBar;
    private DotsIndicator dotIndicator;
    private AutoSlider autoSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bannerProgressBar = findViewById(R.id.bannerProgressBar);
        viewPager = findViewById(R.id.viewPager);
        dotIndicator = findViewById(R.id.dotIndicator);

        fetchBannerData();
    }

    private void fetchBannerData() {
        bannerProgressBar.setVisibility(View.VISIBLE);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Banner");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bannerList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    BannerModel banner = snapshot.getValue(BannerModel.class);
                    if (banner != null) {
                        bannerList.add(banner);
                    }
                }
                bannerProgressBar.setVisibility(View.GONE);
                bannerAdapter = new BannerAdapter(MainActivity.this, bannerList);
                viewPager.setAdapter(bannerAdapter);

                viewPager.setPageTransformer(new PageTransformer());

                dotIndicator.attachTo(viewPager);
                bannerProgressBar.setVisibility(View.GONE);
                dotIndicator.setVisibility(View.VISIBLE);

                autoSlider = new AutoSlider(5000);
                autoSlider.startAutoSlide(viewPager, bannerList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}