package com.example.oppositeside;

import android.graphics.Canvas;
import android.graphics.Paint;

public class RectZone {

    int x;
    int y;
    int right;
    int bottom;
    int color;
    Paint paint;
    Paint paint2;

    public RectZone(int X, int Y, int right, int bottom, int color) {

        this.x = X;
        this.y = Y;
        this.right = right;
        this.bottom = bottom;
        this.color = color;
        paint = new Paint();
        paint2 = new Paint();

        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint2.setColor(color);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeWidth(7);

    }

    public void rectDraw(Canvas c) {

        c.drawRect(x, y, right, bottom, paint);
        c.drawRect(x-10, y-10, right+10, bottom+10, paint2);

    }

    boolean isTouch(int X, int Y) {

        return (x <= X) && (X <= right) && (y <= Y) && (Y <= bottom);
    }
    boolean isTouch(float X, float Y) {

        return (x <= X) && (X <= right) && (y <= Y) && (Y <= bottom);
    }

    boolean isNotOverlay(int X, int Y, int RIGHT, int BOTTOM) {
        return ((right < X || RIGHT < x) && (bottom < Y || BOTTOM < y));//
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public Paint getPaint2() {
        return paint2;
    }

    public void setPaint2(Paint paint2) {
        this.paint2 = paint2;
    }
/*
*
* (x<X)&&(y<Y)&&(RIGHT<right)&&(BOTTOM<bottom))
                ||((X<x)&&(Y<y)&&(right<RIGHT)&&(bottom<BOTTOM)
* */

}
