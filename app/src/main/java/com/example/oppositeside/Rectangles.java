package com.example.oppositeside;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Rectangles {

    GameView gameView;
    double scaleFactorX;
    double scaleFactorY;
    RectZone startZone;
    boolean start;
    ArrayList<ArrayList<RectZone>> arrayLists;
    String path = "1";
    int sizeLvl = 5;
    int i = 0;
    Activity activity;
    ThreadLocalRandom random;
    long startPoint;
//    int[] b = new int[3];


    public Rectangles(GameView gameView, Activity activity) {
        this.gameView = gameView;
        this.activity = activity;
        random = ThreadLocalRandom.current();

        scaleFactorX = gameView.getWidth() / 1080.0;
        scaleFactorY = gameView.getHeight() / 1920.0;

        startZone = new RectZone(0, 0, gameView.getRight(), gameView.getBottom() / 2, Color.RED);
        try {
            arrayLists = setLevel(path, sizeLvl, 1);
        } catch (IOException exception) {
            exception.printStackTrace();
        }


    }

    public void onDraw(Canvas c) {

        if (this.start) {

            startZone.rectDraw(c);
        } else {
            if (i < arrayLists.size()) {
                for (int j = 0; j < arrayLists.get(i).size(); j++) {
                    arrayLists.get(i).get(j).rectDraw(c);
                }
            }
        }
    }

    public void onTouch(MotionEvent event) {

try {
    if (event.getPointerCount() == arrayLists.get(i).size()) {
        boolean b = true;
        for (int o = 0; o < event.getPointerCount(); o++) {
            b = b && arrayLists.get(i).get(o).isTouch(event.getX(o), event.getY(o));

        }
        if (b) {
            i++; //TODO написать функцию смены уровня
        }

    }
} catch (Exception ignored){}

    }

    public void setStart(boolean start) {
        this.start = start;
    }

    ArrayList<ArrayList<RectZone>>  setLevel(String path, int size, int lvl) throws IOException {
        // генерим отрезок экранов в нужном файле
        //File file = new File(path);
        ArrayList<ArrayList<RectZone>> list = new ArrayList<>();

//        AssetManager assetManager = activity.getAssets();
//        InputStream inputStream = assetManager.open("1");
        AssetFileDescriptor assetFileDescriptor = activity.getAssets().openFd(path);
        InputStream inputStream = assetFileDescriptor.createInputStream();

        try (DataInputStream dataInputStream = new DataInputStream(inputStream)) {

            //файл имеет следующую структуру: одна запись - три инта, на одном "экране" lvl записей. Всего может быть очень много экранов.

            //считаем рандомную точку начала отрезка.
            startPoint = random.nextLong(0L, (assetFileDescriptor.getLength() - size * 4L * 3 * lvl));
            dataInputStream.skipBytes((int)startPoint);


            for (int p = 0; p < size; p++) {

                ArrayList<RectZone> doubles = new ArrayList<>();

                for (int t = 0; t < lvl; t++) {

                    int[] b = new int[3];

                    for (int j = 0; j < 3; j++) {
                        b[j] = (dataInputStream.readInt());
                    }
                    /*doubles.add(new RectZone((int) (b[0] ), (int) (b[1] ),
                            (int) ((b[0] + b[2]) ), (int) ((b[1] + b[2]) ), Color.RED));*/
                    doubles.add(new RectZone((int) (b[0] ), (int) (b[1] ), //TODO происходит что-то очень плохое, неправильно считаются числа 
                            (int) ((b[0] + b[2]) ), (int) ((b[1] + b[2]) ), Color.RED));
                }
                list.add(doubles);
            }
            return list;
        } catch (FileNotFoundException exception) {
            Log.println(Log.ERROR, "", "FileNotFoundException");
            return null;
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }

    }

    public void isStart(boolean s) {

        start = s;
    }

    /*public static int countLines(String filename) throws IOException { // штука для подсчёта количества строк в файле. Нашёл в интернете
        try (InputStream is = new BufferedInputStream(new FileInputStream(filename))) {//https://coderoad.ru/453018/%D0%9A%D0%BE%D0%BB%D0%B8%D1%87%D0%B5%D1%81%D1%82%D0%B2%D0%BE-%D1%81%D1%82%D1%80%D0%BE%D0%BA-%D0%B2-%D1%84%D0%B0%D0%B9%D0%BB%D0%B5-%D0%B2-Java
            byte[] c = new byte[1024];

            int readChars = is.read(c);
            if (readChars == -1) {
                // bail out if nothing to read
                return 0;
            }

            // make it easy for the optimizer to tune this loop
            int count = 0;
            while (readChars == 1024) {
                for (int i = 0; i < 1024; ) {
                    if (c[i++] == '\n') {
                        ++count;
                    }
                }
                readChars = is.read(c);
            }

            // count remaining characters
            while (readChars != -1) {
//                System.out.println(readChars);
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
                readChars = is.read(c);
            }

            return count == 0 ? 1 : count;
        }
    }
*/
}
