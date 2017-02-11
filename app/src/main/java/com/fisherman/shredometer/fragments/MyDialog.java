package com.fisherman.shredometer.fragments;

import com.fisherman.shredometer.R;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MyDialog extends DialogFragment
{
	private int title;
	private int text;

public static MyDialog newInstance(int title, int text)
{
	MyDialog f = new MyDialog();
	// Supply num input as an argument.
    Bundle args = new Bundle();
    args.putInt("title", title);
    args.putInt("text", text);
    f.setArguments(args);
    return f;
}

@Override
public void onCreate(Bundle savedInstanceState)
{
	super.onCreate(savedInstanceState);
	title = getArguments().getInt("title");
	text = getArguments().getInt("text");
	setStyle(DialogFragment.STYLE_NORMAL, 0);	
}

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
{
	
	View v = inflater.inflate(R.layout.dialog_fragment, container, false);
	TextView txtabout = (TextView)v.findViewById(R.id.txt_about);
	Button btnOk = (Button)v.findViewById(R.id.btn_about_ok);
	txtabout.setText(text);
	getDialog().setTitle(title);
	btnOk.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v)
		{
			dismiss();
			
		}
	});
	
	
	return v;
}



}
