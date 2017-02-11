package com.fisherman.shredometer.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import com.fisherman.shredometer.R;

public class MyAlertDialog extends DialogFragment
{
    public static MyAlertDialog newInstance(int title, int text)
    {
        MyAlertDialog frag = new MyAlertDialog();
        Bundle args = new Bundle();
        args.putInt("title", title);
        args.putInt("text", text);
        frag.setArguments(args);
        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final int title = getArguments().getInt("title");
        final int text = getArguments().getInt("text");

        AlertDialog.Builder alertCompat = new AlertDialog.Builder(getActivity(), R.style.MyDialogStyle);
        alertCompat.setTitle(title).setMessage(text);
        alertCompat.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
        {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dismiss();
            }
        });
        return alertCompat.create();
    }

    @Override
    public void onStart()
    {
        super.onStart();
        final TextView textView = (TextView) getDialog().findViewById(android.R.id.message);
        textView.setTextSize(15);
    }
}
