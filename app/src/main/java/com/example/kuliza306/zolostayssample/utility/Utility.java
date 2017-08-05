package com.example.kuliza306.zolostayssample.utility;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.example.kuliza306.zolostayssample.R;

/**
 * Created by kuliza306 on 05/08/17.
 */

public class Utility {


    public static void showSnackbar(Context context, String message, CoordinatorLayout coordinatorLayout, boolean positiveType)
    {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, message, Snackbar.LENGTH_SHORT);

        View sbView = snackbar.getView();
        if(positiveType)
        sbView.setBackgroundColor(context.getResources().getColor(R.color.color_green));
        else
        sbView.setBackgroundColor(context.getResources().getColor(R.color.color_red));
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();

    }
}
