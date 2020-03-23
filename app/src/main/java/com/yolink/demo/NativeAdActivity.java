package com.yolink.demo;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.noah.api.AdError;
import com.noah.api.AdIconView;
import com.noah.api.IAdPreloadListener;
import com.noah.api.MediaView;
import com.noah.api.NativeAd;
import com.noah.api.NativeAdView;

public class NativeAdActivity extends AppCompatActivity {

    private static final String SLOT = "DB2R357n";

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
                if (NativeAd.isReady(SLOT)) {
                    tipsView.setText("get ad from cache..."); //获取缓存池中的广告
                } else {
                    tipsView.setText("get ad from adn..."); //实时请求adn获取广告
                }
                NativeAd.getAd(NativeAdActivity.this, SLOT, new NativeAd.AdListener() {
                    @Override
                    public void onAdError(final AdError adError) {
                        tipsView.setText("load error, error code:" + adError.getErrorCode() + " error message: " + adError
                                                                                                                     .getErrorMessage());
                    }

                    @Override
                    public void onAdLoaded(final NativeAd ad) {
                        tipsView.setText("load success.");
                        LinearLayout container = findViewById(R.id.nativead_container);
                        container.removeAllViews();
                        final NativeAdView nativeAdView = new NativeAdView(NativeAdActivity.this);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(NativeAdActivity.dip2px(NativeAdActivity.this, 320), ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.gravity = Gravity.CENTER_HORIZONTAL;
                        container.addView(nativeAdView, params);

                        View customView = LayoutInflater.from(NativeAdActivity.this).inflate(R.layout.native_ad, null);
                        nativeAdView.setCustomView(customView);
                        nativeAdView.setNativeAd(ad);
                        nativeAdView.setVisibility(View.VISIBLE);

                        Button cta = customView.findViewById(R.id.native_ad_call_to_action);
                        TextView title = customView.findViewById(R.id.native_ad_title);
                        TextView content = customView.findViewById(R.id.native_ad_content);
                        AdIconView iconView = customView.findViewById(R.id.native_ad_icon);
                        MediaView coverView = customView.findViewById(R.id.native_ad_media_view);

                        ImageView closeView = customView.findViewById(R.id.icon_close);
                        if (closeView != null) {
                            closeView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    nativeAdView.setVisibility(View.GONE);
                                }
                            });
                        }

                        NativeAd.NativeAssets assets = ad.getAdAssets();
                        if (assets != null) {
                            cta.setText(assets.getCallToAction());
                            title.setText(assets.getTitle());
                            content.setText(assets.getDescription());
                            iconView.setNativeAd(ad);
                            coverView.setNativeAd(ad);

                            cta.setTag(NativeAd.AD_CALL_TO_ACTION_VIEW);
                            title.setTag(NativeAd.AD_HEADLINE_VIEW);
                            content.setTag(NativeAd.AD_BODY_VIEW);
                            iconView.setTag(NativeAd.AD_ICON_VIEW);
                            coverView.setTag(NativeAd.AD_IMAGE_VIEW);

                            ad.registerViewForInteraction(nativeAdView,
                                                          cta,
                                                          title,
                                                          content,
                                                          iconView,
                                                          coverView);
                        }
                    }

                    @Override
                    public void onAdShown(final NativeAd ad) {
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

    private static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
