package com.yolink.demo;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.noah.api.AdError;
import com.noah.api.BannerAd;
import com.noah.api.IAdPreloadListener;

public class BannerAdActivity extends AppCompatActivity {

    private static final String SLOT_300x250 = "rKwOCtRU";
    private static final String SLOT_320x100 = "aaG0Mbqc";
    private static final String SLOT_320x50 = "GX0y8pag";
    private String mSlot = SLOT_300x250;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_ad);
        setTitle("BannerAd");

        int bannerType = getIntent().getIntExtra("banner_type", 1);
        switch (bannerType) {
            case 1:
                mSlot = SLOT_300x250;
                break;
            case 2:
                mSlot = SLOT_320x100;
                break;
            case 3:
                mSlot = SLOT_320x50;
                break;
        }

        final TextView tipsView = findViewById(R.id.bannerad_tips);
        findViewById(R.id.bannerad_preload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                tipsView.setText("preloading......");
                BannerAd.preloadAd(BannerAdActivity.this, mSlot, new IAdPreloadListener() {
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
                if (BannerAd.isReady(mSlot)) {
                    tipsView.setText("get ad from cache..."); //获取缓存池中的广告
                } else {
                    tipsView.setText("get ad from adn..."); //实时请求adn获取广告
                }

                BannerAd.getAd(BannerAdActivity.this, mSlot, new BannerAd.AdListener() {
                    @Override
                    public void onAdError(final AdError adError) {
                        tipsView.setText("load error, error code:" + adError.getErrorCode() + " error message: " + adError.getErrorMessage());
                    }

                    @Override
                    public void onAdLoaded(final BannerAd ad) {
                        tipsView.setText("load success.");
                        LinearLayout container = findViewById(R.id.bannerad_container);
                        container.removeAllViews();
                        LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        lp.gravity = Gravity.CENTER_HORIZONTAL;
                        container.addView(ad.getView(), lp);
                    }

                    @Override
                    public void onAdShown(final BannerAd ad) {
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
