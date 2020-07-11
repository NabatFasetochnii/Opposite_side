package com.example.oppositeside;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Rectangles {

    GameView gameView;

    int LEVELS = 1_000_000;
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
//    double divisionX = 20.0;
//    double divisionY = 20.0;
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

        ArrayList<ArrayList<double[]>> screens1 = new ArrayList<>();
        ArrayList<ArrayList<double[]>> screens2 = new ArrayList<>();
        ArrayList<ArrayList<double[]>> screens3 = new ArrayList<>();
        ArrayList<ArrayList<double[]>> screens4 = new ArrayList<>();
        for (int k = 0; k < LEVELS; k++) {
            ArrayList<double[]> screen = new ArrayList<>();
            for (int i = 0; i < arr_size; i++) {

                if (i > 0) {
                    boolean b;
                    do {
                        b = true;
                        setRandomParam(divisionParam);

                        for (int j = 0; j < i; j++) {
                            b = b && r[j].isNotOverlay(x, y, x + param, y + param);
                        }
                    } while (!b);

                } else {
                    setRandomParam(divisionParam);
                }
//                r[i] = new RectZone(x, y, x + param, y + param, Color.RED);

                screen.add(new double[]{x, y, param});
            }

            double g = 0;
            for (double[] s: screen) { //TODO придумать адекватное деление поуровням

            }
            if () { //TODO написать заполнение ArrayList<ArrayList<double[]>>

            }
        }
        //TODO выести всё по файлам
    }

    private void setRandomParam(double param) {

        this.param = random.nextInt((int) (gameView.getWidth() / param), (int) (gameView.getWidth() / 1.2));
        this.x = random.nextInt(0, gameView.getWidth() - this.param - 1);
        this.y = random.nextInt(0, gameView.getHeight() - this.param - 1);
    }

    public void onDraw(Canvas c) {
        /*for (int i = 0; i < arr_size; i++) {
            r[i].rectDraw(c);
        }
*/
    }

}
