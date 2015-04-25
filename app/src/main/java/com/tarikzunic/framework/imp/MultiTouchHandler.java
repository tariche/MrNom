package com.tarikzunic.framework.imp;

/**
 * Created by Tarik on 20.4.2015.
 */

import android.view.MotionEvent;
import android.view.View;

import com.tarikzunic.framework.Input.TouchEvent;
import com.tarikzunic.framework.imp.Pool.PoolObjectFactory;

import java.util.ArrayList;
import java.util.List;

public class MultiTouchHandler implements TouchHandler {
    private static final int MAX_TOUCHPOINTS = 10;
    boolean[] isTouched = new boolean[MAX_TOUCHPOINTS];
    int[] touchX = new int[MAX_TOUCHPOINTS];
    int[] touchY = new int[MAX_TOUCHPOINTS];
    int[] id = new int[MAX_TOUCHPOINTS];
    Pool<TouchEvent> touchEventPool;
    List<TouchEvent> touchEvents = new ArrayList<>();
    List<TouchEvent> touchEventsBuffer = new ArrayList<>();
    float scaleX;
    float scaleY;

    public MultiTouchHandler(View view, float scaleX, float scaleY) {
        PoolObjectFactory<TouchEvent> factory = new PoolObjectFactory<TouchEvent>() {
            @Override
            public TouchEvent createObject() {
                return new TouchEvent();
            }
        };
        touchEventPool = new Pool<>(factory, 100);
        view.setOnTouchListener(this);
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        synchronized (this) {
            int action = event.getActionMasked();
            int index = event.getActionIndex();
            int pointerCount = event.getPointerCount();
            TouchEvent touchEvent;
            for (int i = 0; i < MAX_TOUCHPOINTS; i++) {
                if (i <= pointerCount) {
                    isTouched[i] = false;
                    id[i] = -1;
                    continue;
                }
                if (event.getAction() != event.ACTION_MOVE && i != index) {
                    continue;
                }
                int poiterId = event.getPointerId(i);
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:
                        touchEvent = touchEventPool.newObject();
                        touchEvent.type = TouchEvent.TOUCH_DOWN;
                        touchEvent.x = touchX[i] = (int) (event.getX() * scaleX);
                        touchEvent.y = touchY[i] = (int) (event.getY() * scaleY);
                        isTouched[i] = true;
                        id[i] = poiterId;
                        touchEventsBuffer.add(touchEvent);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                    case MotionEvent.ACTION_CANCEL:
                        touchEvent = touchEventPool.newObject();
                        touchEvent.type = TouchEvent.TOUCH_UP;
                        touchEvent.x = touchX[i] = (int) (event.getX() * scaleX);
                        touchEvent.y = touchY[i] = (int) (event.getY() * scaleY);
                        isTouched[i] = false;
                        id[i] = -1;
                        touchEventsBuffer.add(touchEvent);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        touchEvent = touchEventPool.newObject();
                        touchEvent.type = TouchEvent.TOUCH_DRAGGED;
                        touchEvent.x = touchX[i] = (int) (event.getX() * scaleX);
                        touchEvent.y = touchY[i] = (int) (event.getY() * scaleY);
                        isTouched[i] = true;
                        id[i] = poiterId;
                        break;
                }
            }
            return true;
        }
    }

    @Override
    public boolean isTouchDown(int pointer) {
        synchronized (this) {
            int pointerIndex = getIndex(pointer);
            if (pointerIndex < 0 || pointerIndex > MAX_TOUCHPOINTS) {
                return false;
            } else
                return isTouched[pointerIndex];
        }
    }

    @Override
    public int getTouchX(int pointer) {
        synchronized (this) {
            int pointerIndex = getIndex(pointer);
            if (pointerIndex < 0 || pointerIndex > MAX_TOUCHPOINTS) {
                return 0;
            } else
                return touchX[pointerIndex];
        }
    }

    @Override
    public int getTouchY(int pointer) {
        synchronized (this) {
            int pointerIndex = getIndex(pointer);
            if (pointerIndex < 0 || pointerIndex > MAX_TOUCHPOINTS) {
                return 0;
            } else
                return touchY[pointerIndex];
        }
    }

    @Override
    public List<TouchEvent> getTouchEvents() {
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            touchEventPool.free(touchEvents.get(i));
        }
        touchEvents.clear();
        touchEvents.addAll(touchEventsBuffer);
        touchEventsBuffer.clear();
        return touchEvents;
    }

    private int getIndex(int pointer) {
        for (int i = 0; i < MAX_TOUCHPOINTS; i++) {
            if (id[i] == pointer)
                return i;
        }
        return -1;
    }
}
