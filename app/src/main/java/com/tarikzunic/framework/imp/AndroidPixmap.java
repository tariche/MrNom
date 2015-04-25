package com.tarikzunic.framework.imp;

import android.graphics.Bitmap;

import com.tarikzunic.framework.Graphics.PixmapFormat;
import com.tarikzunic.framework.Pixmap;

/**
 * Created by Tarik on 21.4.2015.
 */
public class AndroidPixmap implements Pixmap {
    Bitmap bitmap;
    PixmapFormat format;

    public AndroidPixmap(Bitmap bitmap, PixmapFormat format) {
        this.bitmap = bitmap;
        this.format = format;
    }

    @Override
    public int getWidth() {
        return bitmap.getWidth();
    }

    @Override
    public int getHight() {
        return bitmap.getHeight();
    }

    @Override
    public PixmapFormat getFormat() {
        return format;
    }

    @Override
    public void dispose() {
        bitmap.recycle();
    }
}
