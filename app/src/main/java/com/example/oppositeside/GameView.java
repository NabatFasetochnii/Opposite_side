package com.example.oppositeside;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@SuppressLint("ViewConstructor")
public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private DrawThread drawThread;
    float x, y;
    boolean isLevelRun;
    private Activity activity;
    private Rectangles rectangles;
    private Context context;
    private double scaleFactorX;
    private double scaleFactorY;
    private boolean isStart;
    private boolean isLevelFirstRun;


    public GameView(Context context, Activity activity) {
        super(context);
        getHolder().addCallback(this);

        this.context = context;
        this.activity = activity;

        isStart = true;
        isLevelRun = false;
        isLevelFirstRun = true;
    }



    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(getHolder());
        drawThread.setRunning(true);
        drawThread.start();

        scaleFactorX = getWidth() / 1080.0;
        scaleFactorY = getHeight() / 1920.0;

        try {
            rectangles = new Rectangles(this, activity);
        } catch (InterruptedException ignored) {
        }

        if (isStart) {

            rectangles.setStart(true);
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
    public boolean onTouchEvent(MotionEvent event) {

        x = event.getX();
        y = event.getY();
        if(isLevelFirstRun){

            if(rectangles.startZone.isTouch(x, y)){
                rectangles.setStart(false);
                setLevelFirstRun(false);
            }
        }else {

            rectangles.onTouch(event);
        }



        return true;
    }




    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Rectangles getRectangles() {
        return rectangles;
    }

    public void setRectangles(Rectangles rectangles) {
        this.rectangles = rectangles;
    }

    public double getScaleFactorX() {
        return scaleFactorX;
    }

    public void setScaleFactorX(double scaleFactorX) {
        this.scaleFactorX = scaleFactorX;


    }

    public double getScaleFactorY() {
        return scaleFactorY;
    }

    public void setScaleFactorY(double scaleFactorY) {
        this.scaleFactorY = scaleFactorY;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public boolean isLevelRun() {
        return isLevelRun;
    }

    public void setLevelRun(boolean levelRun) {
        isLevelRun = levelRun;
    }

    public boolean isLevelFirstRun() {
        return isLevelFirstRun;
    }

    public void setLevelFirstRun(boolean levelFirstRun) {
        isLevelFirstRun = levelFirstRun;
    }

}