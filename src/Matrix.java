public class Matrix {
    private final int rows, cols;
    private final Complex[][] data;

    public Matrix(int rows, int cols) {

        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Размеры матрицы должны быть положительными");
        }

        this.rows = rows;
        this.cols = cols;
        this.data = new Complex[rows][cols];

        for (int i = 0;i < rows;i++) {
            for (int j = 0;j < cols;j++) {
                data[i][j] = new Complex(0, 0);
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Complex get(int i, int j) {
        return data[i][j];
    }

    public void set(int i, int j, Complex value) {
        data[i][j] = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sb.append(data[i][j]);
                if (j < cols - 1) {
                    sb.append("  ");
                }
            }
            if (i < rows - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public boolean isSquare() {
        return rows == cols;
    }

    public Matrix copy() {
        Matrix copiedMatrix = new Matrix(rows, cols);
        for (int i = 0;i < rows;i++) {
            for (int j = 0;j < cols;j++) {
                copiedMatrix.data[i][j] = this.data[i][j];
            }
        }
        return copiedMatrix;
    }

    private void swapRows(int row1, int row2) {
        Complex[] temp = data[row1];
        data[row1] = data[row2];
        data[row2] = temp;
    }

    public static Matrix identity(int n) {
        Matrix identityMatrix = new Matrix(n, n);
        for (int i = 0; i < n; i++) {
            identityMatrix.data[i][i] = new Complex(1, 0);
        }

        return identityMatrix;
    }

    public Matrix add(Matrix other) {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new IllegalArgumentException("Размеры матриц не совпадают");
        }

        Matrix resultMatrix = new Matrix(this.rows, this.cols);
        for (int i = 0;i < rows; i++) {
            for (int j = 0;j < cols; j++) {
                resultMatrix.data[i][j] = this.data[i][j].add(other.data[i][j]);
            }
        }

        return resultMatrix;
    }

    public Matrix subtract(Matrix other) {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new IllegalArgumentException("Размеры матриц не совпадают");
        }

        Matrix resultMatrix = new Matrix(this.rows, this.cols);
        for (int i = 0;i < rows; i++) {
            for (int j = 0;j < cols; j++) {
                resultMatrix.data[i][j] = this.data[i][j].subtract(other.data[i][j]);
            }
        }

        return resultMatrix;
    }

    public Matrix transpose() {
        Matrix transposedMatrix = new Matrix(cols, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposedMatrix.data[j][i] = this.data[i][j];
            }
        }

        return transposedMatrix;
    }

    public Matrix multiply(Matrix other) {
        if (this.cols != other.rows) {
            throw new IllegalArgumentException("Размеры матриц не подходят для умножения");
        }

        Matrix resultMatrix = new Matrix(this.rows, other.cols);
        for (int i = 0;i < this.rows; i++) {
            for (int j = 0;j < other.cols; j++) {
                Complex tempSum = new Complex(0, 0);
                for (int k = 0;k < this.cols; k++) {
                    tempSum = tempSum.add(this.data[i][k].multiply(other.data[k][j]));
                }
                resultMatrix.data[i][j] = tempSum;
            }
        }

        return resultMatrix;
    }

    public Matrix multiply(Complex k) {
        Matrix resultMatrix = new Matrix(rows, cols);
        for (int i = 0;i < rows; i++) {
            for (int j = 0;j < cols; j++) {
                resultMatrix.data[i][j] = this.data[i][j].multiply(k);
            }
        }

        return resultMatrix;
    }

    public Complex determinant() {
        if (!isSquare()) {
            throw new IllegalArgumentException("Матрица должна быть квадратной");
        }

        Matrix copiedMatrix = copy();
        Complex det = new Complex(1, 0);

        for (int i = 0; i < rows; i++) {
            int pivotRow = i;
            for (int j = i + 1; j < cols; j++) {
                if (copiedMatrix.data[j][i].abs() > copiedMatrix.data[pivotRow][i].abs()) {
                    pivotRow = j;
                }
            }

            if (copiedMatrix.data[pivotRow][i].isZero()) {
                return new Complex(0, 0);
            }

            if (pivotRow != i) {
                copiedMatrix.swapRows(pivotRow, i);
                det = det.multiply(new Complex(-1, 0));
            }

            for (int j = i + 1; j < rows; j++) {
                Complex factor = copiedMatrix.data[j][i].divide(copiedMatrix.data[i][i]);
                for (int k = i; k < rows; k++) {
                    copiedMatrix.data[j][k] = copiedMatrix.data[j][k].subtract(factor.multiply(copiedMatrix.data[i][k]));
                }
            }
            det = det.multiply(copiedMatrix.data[i][i]);
        }
        return det;
    }

    public Matrix inverse() {
        if (!isSquare()) {
            throw new IllegalArgumentException("Матрица должна быть квадратной");
        }

        Matrix copiedMatrix = copy();
        Matrix inversedMatrix = identity(rows);

        for (int i = 0; i < rows; i++) {
            int pivotRow = i;
            for (int j = i + 1; j < rows; j++) {
                if (copiedMatrix.data[j][i].abs() > copiedMatrix.data[pivotRow][i].abs()) {
                    pivotRow = j;
                }
            }

            if (copiedMatrix.data[pivotRow][i].isZero()) {
                throw new ArithmeticException("Матрица вырожденная (det = 0), обратной не существует");
            }

            if (pivotRow != i) {
                copiedMatrix.swapRows(pivotRow, i);
                inversedMatrix.swapRows(pivotRow, i);
            }

            Complex pivot = copiedMatrix.data[i][i];
            for (int k = 0; k < rows; k++) {
                copiedMatrix.data[i][k] = copiedMatrix.data[i][k].divide(pivot);
                inversedMatrix.data[i][k] = inversedMatrix.data[i][k].divide(pivot);
            }

            for (int j = 0; j < rows; j++) {
                if (j == i) {
                    continue;
                }
                Complex factor = copiedMatrix.data[j][i];
                for (int k = 0; k < rows; k++) {
                    copiedMatrix.data[j][k] = copiedMatrix.data[j][k].subtract(factor.multiply(copiedMatrix.data[i][k]));
                    inversedMatrix.data[j][k] = inversedMatrix.data[j][k].subtract(factor.multiply(inversedMatrix.data[i][k]));
                }
            }
        }

        return inversedMatrix;
    }

    public Matrix divide(Matrix other) {
        return this.multiply(other.inverse());
    }
}
