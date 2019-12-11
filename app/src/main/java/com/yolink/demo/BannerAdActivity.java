package com.yolink.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.noah.api.AdError;
import com.noah.api.BannerAd;
import com.noah.api.IAdPreloadListener;

public class BannerAdActivity extends AppCompatActivity {

    private static final String SLOT = "bidding_banner";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_ad);
        setTitle("BannerAd");

        final TextView tipsView = findViewById(R.id.bannerad_tips);
        findViewById(R.id.bannerad_preload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                tipsView.setText("preloading......");
                BannerAd.preloadAd(BannerAdActivity.this, SLOT, new IAdPreloadListener() {
                    @Override
                    public void onAdLoaded() {
                        tipsView.setText("preload success.");
                    }

                    @Override
                    public void onAdError(final AdError adError) {
                        tipsView.setText("preload error, error code:" + adError.getErrorCode() + " error message: " + adError.getErrorMessage());
                    }
                });

            }
        });

        findViewById(R.id.bannerad_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                tipsView.setText("loading......");
                BannerAd.getAd(BannerAdActivity.this, SLOT, new BannerAd.AdListener() {
                    @Override
                    public void onAdError(final AdError adError) {
                        tipsView.setText("load error, error code:" + adError.getErrorCode() + " error message: " + adError.getErrorMessage());
                    }

                    @Override
                    public void onAdLoaded(final BannerAd ad) {
                        tipsView.setText("load success.");
                        LinearLayout container = findViewById(R.id.bannerad_container);
                        container.removeAllViews();
                        container.addView(ad.getView(), new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    }

                    @Override
                    public void onAdShowed(final BannerAd ad) {
                        tipsView.setText("show success.");
                    }

                    @Override
                    public void onAdClosed(final BannerAd ad) {
                    }

                    @Override
                    public void onAdClicked(final BannerAd ad) {
                    }
                });
            }
        });

    }
}
