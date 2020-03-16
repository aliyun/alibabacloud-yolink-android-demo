package com.yolink.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.noah.api.AdError;
import com.noah.api.IAdPreloadListener;
import com.noah.api.RewardedVideoAd;

public class RewardedVideoAdActivity extends AppCompatActivity {

    private static final String SLOT = "pmsdxV2V";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewarded_video_ad);
        setTitle("RewardedVideoAd");

        final TextView tipsView = findViewById(R.id.rewardedvideoad_tips);
        findViewById(R.id.rewardedvideoad_preload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                tipsView.setText("preloading......");
                RewardedVideoAd.preloadAd(RewardedVideoAdActivity.this, SLOT, new IAdPreloadListener() {
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

        findViewById(R.id.rewardedvideoad_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                tipsView.setText("loading......");
                if (RewardedVideoAd.isReady(SLOT)) {
                    tipsView.setText("get ad from cache..."); //获取缓存池中的广告
                } else {
                    tipsView.setText("get ad from adn..."); //实时请求adn获取广告
                }
                RewardedVideoAd.getAd(RewardedVideoAdActivity.this, SLOT, new RewardedVideoAd.AdListener() {
                    @Override
                    public void onAdError(final AdError adError) {
                        tipsView.setText("load error, error code:" + adError.getErrorCode() + " error message: " + adError.getErrorMessage());
                    }

                    @Override
                    public void onAdLoaded(final RewardedVideoAd ad) {
                        tipsView.setText("load success.");
                        ad.show();
                    }

                    @Override
                    public void onAdShown(final RewardedVideoAd ad) {
                        tipsView.setText("show success.");
                    }

                    @Override
                    public void onAdClosed(final RewardedVideoAd ad) {

                    }

                    @Override
                    public void onAdClicked(final RewardedVideoAd ad) {

                    }

                    @Override
                    public void onVideoStart(final RewardedVideoAd ad) {

                    }

                    @Override
                    public void onVideoEnd(final RewardedVideoAd ad) {

                    }

                    @Override
                    public void onRewarded(final RewardedVideoAd ad) {

                    }
                });
            }
        });
    }
}
