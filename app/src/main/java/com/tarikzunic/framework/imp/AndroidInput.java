package com.tarikzunic.framework.imp;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;

import com.tarikzunic.framework.Input;

import java.util.List;

/**
 * Created by Tarik on 20.4.2015.
 */
public class AndroidInput implements Input {
    AccelerometerHandler accelHandler;
    TouchHandler touchHandler;

    public AndroidInput(Context context, View view, float scaleX, float scaleY) {
        accelHandler = new AccelerometerHandler(context);
        if (VERSION.SDK_INT < 5) {
            touchHandler = new SingleTouchHandler(view, scaleX, scaleY);
        } else
            touchHandler = new MultiTouchHandler(view, scaleX, scaleY);
    }

    @Override
    public boolean isTouchDown(int pointer) {
        return touchHandler.isTouchDown(pointer);
    }

    @Override
    public int getTouchX(int pointer) {
        return touchHandler.getTouchX(pointer);
    }

    @Override
    public int getTouchY(int pointer) {
        return touchHandler.getTouchY(pointer);
    }

    @Override
    public float getAccelX() {
        return accelHandler.getAccelX();
    }

    @Override
    public float getAccelY() {
        return accelHandler.getAccelY();
    }

    @Override
    public float getAccelZ() {
        return accelHandler.getAccelZ();
    }

    @Override
    public List<TouchEvent> getTouchEvents() {
        return touchHandler.getTouchEvents();
    }
}
