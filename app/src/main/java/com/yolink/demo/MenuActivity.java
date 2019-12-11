package com.yolink.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.noah.api.NoahSdk;
import com.noah.api.NoahSdkConfig;

public class MenuActivity extends AppCompatActivity {

    private static final String APP_KEY = "APP_KEY100";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Demo");
        setContentView(R.layout.activity_bidding);

        initYolinkSdk();

        findViewById(R.id.bidding_native).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                startActivity(new Intent(MenuActivity.this, NativeAdActivity.class));
            }
        });

        findViewById(R.id.bidding_banner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                startActivity(new Intent(MenuActivity.this, BannerAdActivity.class));
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
    }

    private void initYolinkSdk() {
        NoahSdkConfig.Builder builder = new NoahSdkConfig.Builder();
        builder.setAppKey(APP_KEY);
        NoahSdk.init(this.getApplication(), builder.build(), null);
    }
}
