package ru.nstu.matrix;

import org.springframework.stereotype.Component;

@Component
public class MatrixCalculate {

    public synchronized Matrix calculate(int i, int j, Matrix A, Matrix B) {
        Matrix C = new Matrix(A.getN(), B.getM());
        // Код
        for (int k = i; k < j; k++) {
            for (int l = 0; l < B.getM(); l++) {
                int sum = 0;
                for (int n = 0; n < B.getM(); n++) {
                    sum += A.get(k, n) * B.get(n, l);
                }
                C.set(k, l, sum);
            }
            //System.out.println("k:" + k);
        }
        return C;
    }
}
