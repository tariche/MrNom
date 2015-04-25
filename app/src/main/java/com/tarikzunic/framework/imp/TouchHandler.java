package com.tarikzunic.framework.imp;

import android.view.View;

import com.tarikzunic.framework.Input.TouchEvent;

import java.util.List;

/**
 * Created by Tarik on 20.4.2015.
 */
public interface TouchHandler extends View.OnTouchListener {
    public boolean isTouchDown(int pointer);

    public int getTouchX(int pointer);

    public int getTouchY(int pointer);

    public List<TouchEvent> getTouchEvents();
}
