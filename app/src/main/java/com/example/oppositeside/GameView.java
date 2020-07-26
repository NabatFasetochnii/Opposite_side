package com.example.oppositeside;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import static java.lang.Thread.sleep;

@SuppressLint("ViewConstructor")
public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {


    /**
     * Time per frame for 60 FPS
     */
    private static final int MAX_FRAME_TIME = (int) (1000.0 / 60.0);
    private static final String LOG_TAG = "surface";
    float x, y;
    boolean init = true;
    /**
     * All the constructors are overridden to ensure functionality if one of the different constructors are used through an XML file or programmatically
     */
    private SurfaceHolder holder;
    private Thread drawThread;
    /**
     * True when the surface is ready to draw
     */
    private boolean surfaceReady = false;
    /**
     * Drawing thread flag
     */
    private boolean drawingActive = false;
    //boolean isLevelRun;
    private Activity activity;
    private Rectangles rectangles;
    private Context context;
    private double scaleFactorX;
    private double scaleFactorY;
    private boolean isLevelFirstRun;

    public GameView(Context context, Activity activity) {
        super(context);

        this.context = context;
        this.activity = activity;

        holder = getHolder();
        holder.addCallback(this);
        isLevelFirstRun = true;


    }

    public void tick() {
        //Game logic here

        if (init) {
            rectangles = new Rectangles(this, activity);
            rectangles.setStart(true);
            init = false;
        }


    }

    public void render(Canvas c) {
        //Game rendering here

        if (c != null) {
            c.drawColor(Color.YELLOW);
            rectangles.onDraw(c);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {


        // resize your UI
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {


        this.holder = holder;

        if (drawThread != null) {
            Log.d(LOG_TAG, "draw thread still active..");
            drawingActive = false;
            try {
                drawThread.join();
            } catch (InterruptedException ignored) {
            }
        }

        surfaceReady = true;
        startDrawThread();
        Log.d(LOG_TAG, "Created");

        scaleFactorX = getWidth() / 1080.0;
        scaleFactorY = getHeight() / 1920.0;

        rectangles = new Rectangles(this, activity);


    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // Surface is not used anymore - stop the drawing thread
        stopDrawThread();
        // and release the surface
        holder.getSurface().release();

        this.holder = null;
        surfaceReady = false;
        Log.d(LOG_TAG, "Destroyed");
    }

    /**
     * Stops the drawing thread
     */
    public void stopDrawThread() {
        if (drawThread == null) {
            Log.d(LOG_TAG, "DrawThread is null");
            return;
        }
        drawingActive = false;
        while (true) {
            try {
                Log.d(LOG_TAG, "Request last frame");
                drawThread.join(5000);
                break;
            } catch (Exception e) {
                Log.e(LOG_TAG, "Could not join with draw thread");
            }
        }
        drawThread = null;
    }

    /**
     * Creates a new draw thread and starts it.
     */
    public void startDrawThread() {
        if (surfaceReady && drawThread == null) {
            drawThread = new Thread(this, "Draw thread");
            drawingActive = true;
            drawThread.start();
        }
    }


    @Override
    public void run() {
        Log.d(LOG_TAG, "Draw thread started");
        long frameStartTime;
        long frameTime;

        /*
         * In order to work reliable on Nexus 7, we place ~500ms delay at the start of drawing thread
         * (AOSP - Issue 58385)
         */
        if (android.os.Build.BRAND.equalsIgnoreCase("google") && android.os.Build.MANUFACTURER.equalsIgnoreCase("asus") && android.os.Build.MODEL.equalsIgnoreCase("Nexus 7")) {
            Log.w(LOG_TAG, "Sleep 500ms (Device: Asus Nexus 7)");
            try {
                sleep(500);
            } catch (InterruptedException ignored) {
            }
        }

        while (drawingActive) {
            if (holder == null) {
                return;
            }

            frameStartTime = System.nanoTime();
            Canvas canvas = holder.lockCanvas();
            if (canvas != null) {
                try {
                    synchronized (holder) {
                        tick();
                        render(canvas);
                    }
                } finally {

                    holder.unlockCanvasAndPost(canvas);
                }
            }

            // calculate the time required to draw the frame in ms
            frameTime = (System.nanoTime() - frameStartTime) / 1000000;

            if (frameTime < MAX_FRAME_TIME) {
                try {
                    sleep(MAX_FRAME_TIME - frameTime);
                } catch (InterruptedException e) {
                    // ignore
                }
            }

        }
        Log.d(LOG_TAG, "Draw thread finished");
    }

    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {

        x = event.getX();
        y = event.getY();
        if (isLevelFirstRun) {

            if (rectangles.startZone.isTouch(x, y)) {
                rectangles.setStart(false);
                setLevelFirstRun(false);
            }
        } else {

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

    public boolean isLevelFirstRun() {
        return isLevelFirstRun;
    }

    public void setLevelFirstRun(boolean levelFirstRun) {
        isLevelFirstRun = levelFirstRun;
    }

}