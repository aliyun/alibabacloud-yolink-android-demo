package com.yolink.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.noah.api.AdError;
import com.noah.api.IAdPreloadListener;
import com.noah.api.NativeAd;
import com.noah.api.NativeAdView;

public class NativeAdActivity extends AppCompatActivity {

    private static final String SLOT = "bidding_native";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_ad);
        setTitle("NativeAd");

        final TextView tipsView = findViewById(R.id.nativead_tips);
        findViewById(R.id.nativead_preload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                tipsView.setText("preloading......");
                NativeAd.preloadAd(NativeAdActivity.this, SLOT, new IAdPreloadListener() {
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

        findViewById(R.id.nativead_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                tipsView.setText("loading......");
                NativeAd.getAd(NativeAdActivity.this, SLOT, new NativeAd.AdListener() {
                    @Override
                    public void onAdError(final AdError adError) {
                        tipsView.setText("load error, error code:" + adError.getErrorCode() + " error message: " + adError.getErrorMessage());
                    }

                    @Override
                    public void onAdLoaded(final NativeAd ad) {
                        tipsView.setText("load success.");
                        LinearLayout container = findViewById(R.id.nativead_container);
                        container.removeAllViews();
                        NativeAdView nativeAdView = new NativeAdView(NativeAdActivity.this);
                        nativeAdView.bindAdView(ad, R.layout.native_continer);
                        container.addView(nativeAdView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    }

                    @Override
                    public void onAdShowed(final NativeAd ad) {
                        tipsView.setText("show success.");
                    }

                    @Override
                    public void onAdClosed(final NativeAd ad) {

                    }

                    @Override
                    public void onAdClicked(final NativeAd ad) {

                    }

                    @Override
                    public void onAdEvent(final NativeAd ad, final int eventId, final Object extInfo) {

                    }
                });
            }
        });

    }
}
