package ru.nstu.matrix;

public class Matrix {
    private int n, m;
    int[][] M;

    public Matrix(int n, int m) {
        this.n = n;
        this.m = m;
        M = new int[n][m];
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    int get(int i, int j) {
        return M[i][j];
    }

    void set(int i, int j, int x) {
        M[i][j] = x;
    }
}
