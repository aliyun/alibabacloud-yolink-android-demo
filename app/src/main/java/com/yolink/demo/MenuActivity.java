package com.yolink.demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.noah.api.GlobalConfig;
import com.noah.api.NoahSdk;
import com.noah.api.NoahSdkConfig;

public class MenuActivity extends AppCompatActivity {

    private static final String APP_KEY = "JJBfwzew";
    private static final boolean DEBUG_MODE = true; //如果需要拿 facebook 或admob的测试广告，可以开启 debug mdoe, 不能确保一定拿到，但拿到的概率比较大，取决于 facebook 和 admob

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Demo");
        setContentView(R.layout.activity_bidding);

        if (DEBUG_MODE) {
            initYolinkSdkForDebug();
        } else {
            initYolinkSdkForRelease();
        }

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

    private void initYolinkSdkForRelease() {
        NoahSdk.init(this.getApplication(),APP_KEY);
    }

    private void initYolinkSdkForDebug() {
        NoahSdkConfig.Builder builder = new NoahSdkConfig.Builder();
        builder.setAppKey(APP_KEY);

        SparseArray<String> devices = new SparseArray<>(2);
        devices.put(10006, "02869FBC5F6E91D8CBB53F43A54472F1"); //如果要拿 admob 的测试广告，这里添加 admob 的testDevices id， 注意正式包一定要删除！！！！！
                                        //先请求一次admob广告，logcat中会打出来这样的日志：
                                       //I/Ads: Use RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("02869FBC5F6E91D8CBB53F43A54472F1") to get test ads on this device.
                                       //其中  849588B65ECAE8F7A1771A6236C82E1B  就是admob的test devices id

        devices.put(10007, "sdfsdfds");//如果要拿 facebook 的测试广告，这里添加 facebook 的testDevices id， 注意正式包一定要删除！！！！！
                                       //facebook 的testDevices id可以随便填一个，只要不空就会拿测试广告
        builder.setTestDevices(devices);

        GlobalConfig globalConfig = GlobalConfig.newBuilder().build();
        NoahSdk.init(this.getApplication(), builder.build(), globalConfig);
    }
}
