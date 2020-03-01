package ru.nstu.matrix;

import java.io.*;

public class MatrixRandomGen {
    public static void main(String[] args) {
        int n = 4000;
        int m = 4000;

        int[][] Matrix = new int[n][m];
        int[][] MatrixB = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                Matrix[i][j] = (int)(Math.random()*100);
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                MatrixB[i][j] = (int)(Math.random()*100);
            }
        }


        //try (BufferedWriter fos = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/home/aleksandr/Documents/testKach/data5x5"), "UTF-8")) )
        try (PrintWriter fos = new PrintWriter("/home/aleksandr/Documents/testKach/data4000x4000", "UTF-8"))
        {
            fos.print(n);
            fos.print(' ');
            fos.print(m);
            fos.print('\n');
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    fos.print(Matrix[i][j]);
                    fos.print(' ');
                }
                fos.print('\n');
            }
            fos.print(n);
            fos.print(' ');
            fos.print(m);
            fos.print('\n');
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < m; ++j) {
                    fos.print(MatrixB[i][j]);
                    fos.print(' ');
                }
                fos.print('\n');
            }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
