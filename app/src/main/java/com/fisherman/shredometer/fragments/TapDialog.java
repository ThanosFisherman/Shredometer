package com.fisherman.shredometer.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.fisherman.shredometer.R;

public class TapDialog extends AppCompatDialogFragment implements View.OnTouchListener, View.OnClickListener
{
    private TextView txtTap;
    private float counter = 0;
    private long firstbeat;
    private float avgbeats = 0;

    public interface ITapSend
    {
        void sendToActivity(int bpm);
    }

    private ITapSend sendBack;

    public static TapDialog newInstance(int title)
    {
        TapDialog tapDiag = new TapDialog();
        Bundle args = new Bundle();
        args.putInt("title", title);
        tapDiag.setArguments(args);
        return tapDiag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setStyle(AppCompatDialogFragment.STYLE_NORMAL, R.style.MyDialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.tap_tempo_fragment, container, false);
        int title = getArguments().getInt("title");
        getDialog().setTitle(title);

        txtTap = (TextView) view.findViewById(R.id.txt_tap);
        Button btnSend = (Button) view.findViewById(R.id.btn_tap_send);
        Button btnReset = (Button) view.findViewById(R.id.btn_tap_reset);
        txtTap.setOnTouchListener(this);
        btnReset.setOnClickListener(this);
        btnSend.setOnClickListener(this);
        return view;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        sendBack = (ITapSend) activity;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            long msec = System.currentTimeMillis();
            if (counter == 0)
            {
                firstbeat = msec;
                txtTap.setText("first");
                avgbeats = 0;
            }
            else
            {
                avgbeats = 60000 * counter / (msec - firstbeat); // beats per
                // second
            }
            counter++;
            // System.out.println("Beats: " + ((int)counter));
            avgbeats = (float) Math.round(avgbeats * 100) / 100;
            txtTap.setText(avgbeats + " BPM");
        }
        return true;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_tap_reset:
                counter = 0;
                avgbeats = 0;
                txtTap.setText(R.string.tapHere);
                break;
            case R.id.btn_tap_send:
                sendBack.sendToActivity((int) avgbeats);
                dismiss();
                break;

        }

    }

}
