package com.example.administrator.kib_3plus.mode;

import android.graphics.Bitmap;

/**
 * Created by cui on 2017/7/4.
 */

public class PictureDialogMode {
    String url;
    Bitmap bitmap;

    public PictureDialogMode(String url, Bitmap bitmap) {
        this.url = url;
        this.bitmap = bitmap;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
