package com.fisherman.shredometer;

import android.content.Context;

public class NpsCalc
{
    private static final float[] noteValues = {1 / 4f, 1 / 2f, 1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 12f, 16f};

    public static String calcNps(Context ctx, String bpm, int position)
    {

        if (bpm.length() == 0)
        {
            return ctx.getResources().getString(R.string.wrong);
        }
        float result = noteValues[position] * (Float.parseFloat(bpm) / 60f);
        result = Math.round(result * 100.0f) / 100.0f;
        return Float.toString(result) + " NPS";
    }
}
