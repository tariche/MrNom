package com.tarikzunic.framework.imp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Tarik on 21.4.2015.
 */
public class AndroidFastRenderView extends SurfaceView implements Runnable {
    AndroidGame game;
    Bitmap frameBuffer;
    Thread renderThread = null;
    SurfaceHolder holder;
    volatile boolean running = false;

    public AndroidFastRenderView(AndroidGame game, Bitmap frameBuffer) {
        super(game);
        this.game = game;
        this.frameBuffer = frameBuffer;
        this.holder = getHolder();
    }

    public void resume() {
        running = true;
        renderThread = new Thread(this);
        renderThread.start();
    }

    @Override
    public void run() {
        Rect dstRect = new Rect();
        long startTime = System.nanoTime();
        while (running) {
            if (!holder.getSurface().isValid())
                continue;
            float deltaTime = (System.nanoTime() - startTime) / 1000000000f;

            game.getCurrentScreen().update(deltaTime);
            game.getCurrentScreen().present(deltaTime);

            Canvas canvas = holder.lockCanvas();
            dstRect = canvas.getClipBounds();
            canvas.drawBitmap(frameBuffer, null, dstRect, null);
            holder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause() {
        running = false;
        while (true) {
            try {
                renderThread.join();
                return;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
