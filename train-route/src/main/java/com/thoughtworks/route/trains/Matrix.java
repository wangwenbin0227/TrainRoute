package com.thoughtworks.route.trains;

/**
 * matrix
 * Created by wangWenBin on 2018/12/08.
 */
public class Matrix {
    private int row;
    private int column;
    private int[][] array;

    private Matrix(int row, int column, int[][] array) {
        this.row = row;
        this.column = column;
        this.array = array;
    }

    public static Matrix of(int[][] array) {
        if (array == null) {
            throw new IllegalArgumentException();
        }

        if (array.length == 0) {
            return new Matrix(0, 0, new int[0][0]);
        }

        int r = array.length;
        int c = array[0].length;
        int[][] a = new int[r][c];

        for (int i = 0; i<r; i++) {
            for (int j = 0; j<c; j++) {
                a[i][j] = array[i][j];
            }
        }

        return new Matrix(r, c, a);
    }

    /**
     * matrix multiplication
     * @param matrix
     * @return
     */
    public Matrix multiply(Matrix matrix) {
        if (matrix == null || this.row != matrix.column || this.column != matrix.row) {
            throw new IllegalArgumentException();
        }

        int n = this.row;
        int[][] array = new int[n][n];
        for (int i = 0; i<n; i++) {
            for (int j = 0; j<n; j++) {
                int sum = 0;
                for (int k = 0; k<n; k++) {
                    sum += this.array[i][k] * matrix.array[k][j];
                }
                array[i][j] = sum;
            }
        }
        return new Matrix(n, n, array);
    }

    /**
     * The n power of a matrix
     * @param n
     * @return
     */
    public Matrix power(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        if (n == 1) {
            return new Matrix(this.row, this.column, this.array);
        }
        return power(n - 1).multiply(this);
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int[][] getArray() {
        return array;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(row).append("*").append(column).append("matrix：\n");
        if (array != null) {
            for (int i = 0; i<row; i++) {
                for (int j = 0; j<column; j++) {
                    sb.append(array[i][j]).append(" ");
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
