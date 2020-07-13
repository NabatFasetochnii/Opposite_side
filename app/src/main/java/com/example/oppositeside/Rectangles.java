package com.example.oppositeside;

import android.graphics.Canvas;

import java.io.*;
import java.util.concurrent.ThreadLocalRandom;

public class Rectangles {

    GameView gameView;

    int x; //координаты прямоугольничков
    int y;
    int param; //размер прямоугольничков (пока будут квадратики)
    RectZone[] r;
    int arr_size;


    public Rectangles(GameView gameView, int arr_size) {
        this.gameView = gameView;
        this.arr_size = arr_size;

        r = new RectZone[arr_size];


    }

    public void onDraw(Canvas c) {
        /*for (int i = 0; i < arr_size; i++) {
            r[i].rectDraw(c);
        }
*/
    }

    boolean setLevel(String path, int s){ // генерим отрезок экранов в нужном файле

        try(FileReader fileReader = new FileReader(path)){ //TODO дописать функцию

            int stringCount = countLines(path) - 1;

            ThreadLocalRandom random = ThreadLocalRandom.current();
            int startPoint = random.nextInt(0, stringCount - s);
//            fileReader.read();

            return true;
        }catch (IOException exception){return false;}


    }

    public static int countLines(String filename) throws IOException { // штука для подсчёта количества строк в файле. Нашёл в интернете
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

}
