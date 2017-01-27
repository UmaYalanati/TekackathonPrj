package com.tek.ems.callback;

import android.graphics.Bitmap;

/**
 * Created by Mayank Tiwari on 28/10/16.
 */

public interface NavigationDrawerCallback {

    public void navigateToItem(int itemId);

    public void updateImage(Bitmap bitmap);

}
