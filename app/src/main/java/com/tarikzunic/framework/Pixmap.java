package com.tarikzunic.framework;

import static com.tarikzunic.framework.Graphics.PixmapFormat;

/**
 * Created by Tarik on 2.4.2015.
 */
public interface Pixmap {
    public int getWidth();
    public int getHight();
    public PixmapFormat getFormat();
    public void dispose();
}
