package com.csn.ems.utils;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Mayank Tiwari on 19/09/16.
 */

public class MenuUtils {

    public static void colorToolbarMenuIcons(Context context, Menu menu, int color) {
        for (int i = 0; i < menu.size(); i++) {
            Drawable drawable = menu.getItem(i).getIcon();
            if (drawable != null) {
                drawable.mutate();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    drawable.setColorFilter(context.getResources().getColor(color, context.getTheme()), PorterDuff.Mode.SRC_ATOP);
                } else {
                    drawable.setColorFilter(context.getResources().getColor(color), PorterDuff.Mode.SRC_ATOP);
                }
            }
        }
    }

    public static void setBadgeCount(TextView badgeTextView, int count) {
        if (count > 0) {
            badgeTextView.setText(String.valueOf(count));
            if (badgeTextView.getVisibility() != View.VISIBLE) {
                badgeTextView.setVisibility(View.VISIBLE);
            }
        } else {
            badgeTextView.setText(String.valueOf(0));
            badgeTextView.setVisibility(View.GONE);
        }
    }

    public static void decrementBadgeCount(TextView badgeTextView) {
        int count = Integer.parseInt(badgeTextView.getText().toString());
        if (count > 1) {
            badgeTextView.setText(String.valueOf(count - 1));
        } else {
            badgeTextView.setVisibility(View.GONE);
            badgeTextView.setText(String.valueOf(0));
        }
    }

    public static void incrementBadgeCount(TextView badgeTextView) {
        int count = Integer.parseInt(badgeTextView.getText().toString());
        if (count == 0) {
            badgeTextView.setVisibility(View.VISIBLE);
        }
        badgeTextView.setText(String.valueOf(count + 1));
    }

}
