package ru.nstu.matrix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nstu.FileStorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

@Component
public class MatrixServices {

    private Matrix matrixA, matrixB, matrixC;
    @Autowired
    MatrixCalculate matrixCalculate;
    @Autowired
    FileStorage fileStorage;

    public int getCount() {
        return matrixA.getN();
    }

    // Вычисление задачи и подготовка отчета
    public long calculate() {
        //readData();

        long m = System.currentTimeMillis();
        matrixC = matrixCalculate.calculate(0, matrixA.getN(), matrixA, matrixB);
        //System.out.println((double) (System.currentTimeMillis() - m));
        return System.currentTimeMillis() - m;
    }


    public void readData() {
        try {
            File data = fileStorage.getData();
            //InputStream fileIn = new FileInputStream(fileStorage.getData());

            //try (InputStreamReader in = new InputStreamReader(fileIn, "UTF-8") ) {
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

//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}
