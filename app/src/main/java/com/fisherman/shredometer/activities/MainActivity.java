package com.fisherman.shredometer.activities;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.fisherman.shredometer.NpsCalc;
import com.fisherman.shredometer.R;
import com.fisherman.shredometer.SpinnerBean;
import com.fisherman.shredometer.adapters.MySpinnerAdapter;
import com.fisherman.shredometer.fragments.TapDialog;
import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends BaseActivity implements OnClickListener, TapDialog.ITapSend
{
    private SpinnerBean[] bean = null;
    private Handler handler;
    private Spinner spinner;
    private EditText editBpm;
    private TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mAdsHelper.showNativeExpress((LinearLayout) findViewById(R.id.ll_forads));
        handler = new Handler();
        spinner = (Spinner) findViewById(R.id.notes_spinner);
        editBpm = (EditText) findViewById(R.id.edit_bpm);
        txtResult = (TextView) findViewById(R.id.text_result);
        Button btnTap = (Button) findViewById(R.id.btn_tap);
        Button btnCalc = (Button) findViewById(R.id.btn_calc);
        btnCalc.setOnClickListener(this);
        btnTap.setOnClickListener(this);

        initArray();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (mAdsHelper != null)
            mAdsHelper.destroyNativeExpress();
    }

    private void initArray()
    {
        new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                TypedArray img = getResources().obtainTypedArray(R.array.notes_values);
                String bigText[] = getResources().getStringArray(R.array.notes_array);
                String smallText[] = getResources().getStringArray(R.array.explanation);
                bean = new SpinnerBean[bigText.length];
                for (int i = 0; i < bigText.length; i++)
                {
                    bean[i] = new SpinnerBean();
                    bean[i].setBigText(bigText[i]);
                    bean[i].setSmallText(smallText[i]);
                    bean[i].setImgNote(img.getResourceId(i, -1));
                }
                fillSpinner();
                img.recycle();

            }
        }).start();

    }

    private void fillSpinner()
    {
        handler.post(new Runnable()
        {

            @Override
            public void run()
            {
                spinner.setAdapter(new MySpinnerAdapter(getApplicationContext(), R.layout.spinner_rows, bean));
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        final Bundle bundle = new Bundle();
        switch (v.getId())
        {
            case R.id.btn_tap:
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Tap Tempo Button");
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, getResources().getResourceEntryName(R.id.btn_tap));
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Button");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                showMyDialog(TapDialog.newInstance(R.string.tapToMeasure));
                break;
            case R.id.btn_calc:
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Calc Button");
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, getResources().getResourceEntryName(R.id.btn_calc));
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Button");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                txtResult.setText(NpsCalc.calcNps(MainActivity.this, editBpm.getText().toString(), spinner.getSelectedItemPosition()));
                mAdsHelper.showInterstitialIfNeeded();
                break;
        }

    }

    @Override
    public void sendToActivity(int bpm)
    {
        try
        {
            if (bpm != 0)
            {
                editBpm.setText(String.valueOf(bpm));
            }
        }
        catch (NumberFormatException e)
        {
        }

    }
}
