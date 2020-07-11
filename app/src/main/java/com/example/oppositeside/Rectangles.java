package com.example.oppositeside;

import android.graphics.Canvas;
import android.graphics.Color;

import java.util.concurrent.ThreadLocalRandom;

public class Rectangles {

    GameView gameView;

//    final int COLOR_SIZE = 6;

    ThreadLocalRandom random = ThreadLocalRandom.current();
//    Paint paint;

    int x; //координаты прямоугольничков
    int y;
    int param; //размер прямоугольничков (пока будут квадратики)
    RectZone[] r;
    //    int[] colors;
//    Paint[] paints;
    int arr_size;
    double divisionX = 20.0;
    double divisionY = 20.0;
    double divisionParam = 30.0;

    public Rectangles(GameView gameView, int arr_size) {
        this.gameView = gameView;
        this.arr_size = arr_size;

        r = new RectZone[arr_size];
//        paints = new Paint[COLOR_SIZE];
/*
        colors = new int[COLOR_SIZE];

        colors[0] = Color.GREEN;
        colors[1] = Color.RED;
        colors[2] = Color.WHITE;
        colors[3] = Color.BLACK;
        colors[4] = Color.BLUE;
*/

        setRandom();

    }

    public void setRandom() {
        for (int i = 0; i < arr_size; i++) {

            if (i > 0) {
                boolean b;
                do {
                    b = true;
                    setRandomParam(divisionX, divisionY, divisionParam);

                    for (int j = 0; j < i; j++) {
                        b = b && r[j].isNotOverlay(x, y, x + param, y + param);
                    }
                } while (!b);

            } else {
                setRandomParam(divisionX, divisionY, divisionParam);
            }

            r[i] = new RectZone(x, y, x + param, y + param, Color.RED);
        }

    }

    private void setRandomParam(double X, double Y, double param) {

        this.param = random.nextInt((int) (gameView.getWidth() / param), (int) (gameView.getWidth() / 1.2));
        this.x = random.nextInt(0, gameView.getWidth() - this.param - 1);
        this.y = random.nextInt(0, gameView.getHeight() - this.param - 1);
    }

    public void onDraw(Canvas c) {
        for (int i = 0; i < arr_size; i++) {
            r[i].rectDraw(c);
        }

    }

}
