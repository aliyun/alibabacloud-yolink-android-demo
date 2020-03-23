package com.yolink.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.noah.api.AdError;
import com.noah.api.IAdPreloadListener;
import com.noah.api.InterstitialAd;

public class InterstitialAdActivity extends AppCompatActivity {

    private static final String SLOT = "6AOsSLFE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial_ad);
        setTitle("InterstitialAd");

        final TextView tipsView = findViewById(R.id.interstitialad_tips);
        findViewById(R.id.interstitialad_preload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                tipsView.setText("preloading......");
                InterstitialAd.preloadAd(InterstitialAdActivity.this, SLOT, new IAdPreloadListener() {
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

        findViewById(R.id.interstitialad_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                tipsView.setText("loading......");
                if (InterstitialAd.isReady(SLOT)) {
                    tipsView.setText("get ad from cache..."); //获取缓存池中的广告
                } else {
                    tipsView.setText("get ad from adn..."); //实时请求adn获取广告
                }

                InterstitialAd.getAd(InterstitialAdActivity.this, SLOT, new InterstitialAd.AdListener() {
                    @Override
                    public void onAdError(final AdError adError) {
                        tipsView.setText("load error, error code:" + adError.getErrorCode() + " error message: " + adError.getErrorMessage());
                    }

                    @Override
                    public void onAdLoaded(final InterstitialAd ad) {
                        tipsView.setText("load success.");
                        ad.show();
                    }

                    @Override
                    public void onAdShown(final InterstitialAd ad) {
                        tipsView.setText("show success.");
                    }

                    @Override
                    public void onAdClosed(final InterstitialAd ad) {

                    }

                    @Override
                    public void onAdClicked(final InterstitialAd ad) {

                    }

                    @Override
                    public void onVideoStart(final InterstitialAd ad) {

                    }

                    @Override
                    public void onVideoEnd(final InterstitialAd ad) {

                    }
                });
            }
        });

    }
}
