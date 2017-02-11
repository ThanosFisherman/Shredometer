package com.fisherman.shredometer.activities;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.fisherman.shredometer.AdsHelper;
import com.fisherman.shredometer.R;
import com.fisherman.shredometer.fragments.MyAlertDialog;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

public abstract class BaseActivity extends AppCompatActivity
{
    protected FirebaseAnalytics mFirebaseAnalytics;
    protected AdsHelper mAdsHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        MobileAds.initialize(getApplicationContext(), AdsHelper.ADMOB_APP_ID);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mAdsHelper = new AdsHelper(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_about:
                showMyDialog(MyAlertDialog.newInstance(R.string.action_about, R.string.text_about));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

	/*protected void showAlertDialog()
    {
		DialogFragment frag = MyAlertDialog.newInstance(R.string.soon);
		frag.show(getSupportFragmentManager(), "dialog");
	} */

    protected void showMyDialog(DialogFragment diag)
    {
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null)
        {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        diag.show(ft, "dialog");
    }
}
