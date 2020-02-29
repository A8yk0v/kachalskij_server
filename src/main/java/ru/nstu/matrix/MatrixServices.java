package ru.nstu.matrix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nstu.FileStorage;

import java.io.File;
import java.util.Scanner;

@Component
public class MatrixServices {

    private Matrix matrixA, matrixB, matrixC;
    @Autowired
    MatrixCalculate matrixCalculate;
    @Autowired
    FileStorage fileStorage;

    // Вычисление задачи и подготовка отчета
    public long calculate() {
        readData(fileStorage.getData());

        long m = System.currentTimeMillis();
        matrixC = matrixCalculate.calculate(0, matrixA.getN(), matrixA, matrixB);
        //System.out.println((double) (System.currentTimeMillis() - m));
        return System.currentTimeMillis() - m;
    }


    void readData(File data) {
        try {
            Scanner in = new Scanner(data.toPath(), "UTF-8");
            // FOR matrixA
            int n = in.nextInt();
            int m = in.nextInt();
            matrixA = new Matrix(n, m);
            for (int i = 0; i < matrixA.getN(); i++) {
                for (int j = 0; j < matrixA.getM(); j++) {
                    matrixA.set(i, j, in.nextInt());
                }
            }
            // FOR matrixB
            n = in.nextInt();
            m = in.nextInt();
            matrixB = new Matrix(n, m);
            for (int i = 0; i < matrixB.getN(); i++) {
                for (int j = 0; j < matrixB.getM(); j++) {
                    matrixB.set(i, j, in.nextInt());
                }
            }

            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}
