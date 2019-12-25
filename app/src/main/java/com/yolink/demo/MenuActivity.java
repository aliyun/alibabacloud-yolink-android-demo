package com.yolink.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.facebook.ads.AdSettings;
import com.noah.api.NoahSdk;

public class MenuActivity extends AppCompatActivity {

    private static final String APP_KEY = "JJBfwzew";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Demo");
        setContentView(R.layout.activity_bidding);

        initYolinkSdk();

        AdSettings.setDebugBuild(true);

        findViewById(R.id.bidding_native).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                startActivity(new Intent(MenuActivity.this, NativeAdActivity.class));
            }
        });

        findViewById(R.id.bidding_inter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                startActivity(new Intent(MenuActivity.this, InterstitialAdActivity.class));
            }
        });

        findViewById(R.id.bidding_reward).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                startActivity(new Intent(MenuActivity.this, RewardedVideoAdActivity.class));
            }
        });

        findViewById(R.id.bidding_banner1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent intent = new Intent(MenuActivity.this, BannerAdActivity.class);
                intent.putExtra("banner_type", 1);
                startActivity(intent);
            }
        });

        findViewById(R.id.bidding_banner2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent intent = new Intent(MenuActivity.this, BannerAdActivity.class);
                intent.putExtra("banner_type", 2);
                startActivity(intent);
            }
        });

        findViewById(R.id.bidding_banner3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent intent = new Intent(MenuActivity.this, BannerAdActivity.class);
                intent.putExtra("banner_type", 3);
                startActivity(intent);
            }
        });
    }

    private void initYolinkSdk() {
        NoahSdk.init(this.getApplication(),APP_KEY);
    }
}
