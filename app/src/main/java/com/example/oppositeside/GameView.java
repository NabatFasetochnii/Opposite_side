package com.example.oppositeside;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private DrawThread drawThread;
    private static final int RECT_SIZE = 2;
    public Rectangles rectangles;
    private boolean isStart;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        isStart = true;
    }



    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(getHolder());
        drawThread.setRunning(true);
        drawThread.start();

        if(isStart){
            rectangles = new Rectangles(this, RECT_SIZE);
            isStart = false;
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        // завершаем работу потока
        drawThread.setRunning(false);

        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
                // если не получилось, то будем пытаться еще и еще
            }
        }
    }

    class DrawThread extends Thread {
        private boolean runFlag = false;
        private final SurfaceHolder surfaceHolder;

        public DrawThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;

        }

        public void setRunning(boolean run) {
            runFlag = run;
        }

        @Override
        public void run() {
            Canvas canvas;
            while (runFlag) {
                canvas = null;
                try {
                    // получаем объект Canvas и выполняем отрисовку
                    canvas = surfaceHolder.lockCanvas(null);
                    synchronized (surfaceHolder) {
                        if (canvas != null) draw(canvas);
                    }
                } finally {
                    if (canvas != null) {
                        // отрисовка выполнена. выводим результат на экран
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }

        private void draw(Canvas c) {
            if (c != null) {
                c.drawColor(Color.YELLOW);
                rectangles.onDraw(c);
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event){



        return true;
    }


}