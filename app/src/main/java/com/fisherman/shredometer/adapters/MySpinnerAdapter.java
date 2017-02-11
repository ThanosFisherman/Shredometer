package com.fisherman.shredometer.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fisherman.shredometer.R;
import com.fisherman.shredometer.SpinnerBean;

public class MySpinnerAdapter extends ArrayAdapter<SpinnerBean>
{

    private int resource;
    private LayoutInflater inflater;
    private SpinnerBean[] items;

    public MySpinnerAdapter(Context context, int resource, SpinnerBean[] objects)
    {
        super(context, resource, objects);
        this.resource = resource;
        this.items = objects;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent)
    {
        return getCustomView(position, convertView);
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent)
    {
        return getCustomView(position, convertView);
    }

    private View getCustomView(int position, View convertView)
    {
        // super optimized adapter version. About 15%-20% better performance!
        View v = convertView;
        ViewHolder holder;

        if (v == null)
        {
            v = inflater.inflate(resource, null);
            holder = new ViewHolder();
            holder.smallText = (TextView) v.findViewById(R.id.spinner_smallText);
            holder.bigText = (TextView) v.findViewById(R.id.spinner_bigText);
            holder.notes = (ImageView) v.findViewById(R.id.spinner_notes);
            v.setTag(holder);
        }
        holder = (ViewHolder) v.getTag();
        SpinnerBean item = items[position];
        holder.bigText.setText(item.getBigText());
        holder.smallText.setText(item.getSmallText());
        holder.notes.setImageResource(item.getImgNote());

        return v;

    }

    private static class ViewHolder
    {
        private TextView bigText;
        private TextView smallText;
        private ImageView notes;
    }

}
