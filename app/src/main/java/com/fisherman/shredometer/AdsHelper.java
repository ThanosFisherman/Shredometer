package com.fisherman.shredometer;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.NativeExpressAdView;

import java.util.Random;

public class AdsHelper
{
    public static final String INTERSTITIAL_ID = "ca-app-pub-4071471797950020/3831805995";
    public static final String BANNER_ID = "ca-app-pub-4071471797950020/5352238390";
    public static final String NATIVE_EXPRESS_ID = "ca-app-pub-4071471797950020/8131429991";
    public static final String ADMOB_APP_ID = "ca-app-pub-4071471797950020~2355072794";
    private AdView mBannerAdView;
    private InterstitialAd mInterstitialAd;
    private NativeExpressAdView mNativeExpressAdView;
    private Context context;
    private final int MIN = 8;
    private final int MAX = 20;
    private final int FREQ = new Random().nextInt((MAX - MIN) + 1) + MIN;
    private int showAd;
    private final SharedPreferences prefs;

    public AdsHelper(Context context)
    {
        this.context = context;
        this.prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        showAd = prefs.getInt("showAd", 0);
        initializeInterstitial();
        initializeNativeExpress();
        //initializeBanner();
    }

    private void initializeInterstitial()
    {
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(INTERSTITIAL_ID);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    private void initializeBanner()
    {
        mBannerAdView = new AdView(context);
        mBannerAdView.setAdUnitId(BANNER_ID);
        mBannerAdView.setAdSize(AdSize.SMART_BANNER);
    }

    private void initializeNativeExpress()
    {
        mNativeExpressAdView = new NativeExpressAdView(context);
        mNativeExpressAdView.setAdUnitId(NATIVE_EXPRESS_ID);
        mNativeExpressAdView.setAdSize(new AdSize(AdSize.FULL_WIDTH, 132));
    }

    public void showInterstitialIfNeeded()
    {
        if (mInterstitialAd == null)
            return;
        if (showAd == 0)
        {
            if (mInterstitialAd.isLoaded())
            {
                mInterstitialAd.show();
                showAd++;
            }
            else
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
        }
        else if (showAd >= FREQ)
            showAd = 0;
        else
            showAd++;

        prefs.edit().putInt("showAd", showAd).apply();
        Log.i("AdsHelper", "FREQ= " + FREQ + " showAd= " + showAd);
    }


    public void showBanner(final LinearLayout llAds)
    {
        mBannerAdView.setAdListener(new AdListener()
        {
            @Override
            public void onAdLoaded()
            {
                super.onAdLoaded();
                llAds.removeAllViews();
                llAds.addView(mBannerAdView);
            }

            @Override
            public void onAdFailedToLoad(int errorCode)
            {
                super.onAdFailedToLoad(errorCode);
                mBannerAdView.loadAd(new AdRequest.Builder().build());
            }
        });
        mBannerAdView.loadAd(new AdRequest.Builder().build());
    }

    public void showNativeExpress(final LinearLayout llAds)
    {
        llAds.addView(mNativeExpressAdView);
        mNativeExpressAdView.loadAd(new AdRequest.Builder().build());
    }

    public void destroyBanner()
    {
        if (mBannerAdView != null)
            mBannerAdView.destroy();
    }

    public void pauseNativeExpress()
    {
        if (mNativeExpressAdView != null)
            mNativeExpressAdView.pause();
    }

    public void resumeNativeExpress()
    {
        if (mNativeExpressAdView != null)
            mNativeExpressAdView.resume();
    }

    public void destroyNativeExpress()
    {
        if (mNativeExpressAdView != null)
            mNativeExpressAdView.destroy();
    }
}
