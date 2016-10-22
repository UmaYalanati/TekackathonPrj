package com.csn.ems.com.csn.ems;

import android.app.Application;
import android.content.Context;
import android.view.MenuItem;

import com.csn.ems.R;

/**
 * Created by uyalanat on 22-10-2016.
 */

public class Common extends Application{
    Context context;
    public void changeIcon(){
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (menu != null) {
                    MenuItem item = menu.findItem(R.id.test_menu_item);
                    if (item != null) {
                        item.setIcon(android.R.drawable.ic_menu_edit);
                    }
                }
            }
        });
    }
}
