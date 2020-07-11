package com.example.oppositeside;

import android.graphics.Canvas;
import android.graphics.Color;
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
        paint2.setColor(Color.BLACK);
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

    boolean isNotOverlay(int X, int Y, int RIGHT, int BOTTOM) {
        return ((right < X || RIGHT < x) && (bottom < Y || BOTTOM < y));//
    }
/*
*
* (x<X)&&(y<Y)&&(RIGHT<right)&&(BOTTOM<bottom))
                ||((X<x)&&(Y<y)&&(right<RIGHT)&&(bottom<BOTTOM)
* */

}
