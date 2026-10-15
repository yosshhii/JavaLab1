import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            printMenu();
            int choice = readInt("Выберите операцию: ");

            try {
                switch (choice) {
                    case 1 -> {
                        Matrix a = readMatrix("A");
                        Matrix b = readMatrix("B");
                        printResult("A + B", a.add(b));
                    }
                    case 2 -> {
                        Matrix a = readMatrix("A");
                        Matrix b = readMatrix("B");
                        printResult("A - B", a.subtract(b));
                    }
                    case 3 -> {
                        Matrix a = readMatrix("A");
                        Matrix b = readMatrix("B");
                        printResult("A * B", a.multiply(b));
                    }
                    case 4 -> {
                        Matrix a = readMatrix("A");
                        Matrix b = readMatrix("B");
                        printResult("A / B", a.divide(b));
                    }
                    case 5 -> {
                        Matrix a = readMatrix("A");
                        printResult("A^T", a.transpose());
                    }
                    case 6 -> {
                        Matrix a = readMatrix("A");
                        System.out.println("det(A) = " + a.determinant());
                    }
                    case 7 -> {
                        Matrix a = readMatrix("A");
                        printResult("A^(-1)", a.inverse());
                    }
                    case 0 -> {
                        System.out.println("Выход");
                        return;
                    }
                    default -> System.out.println("Неизвестная команда");
                }
            } catch (IllegalArgumentException | ArithmeticException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }

            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("====== Матрицы комплексных чисел ======");
        System.out.println("1. Сложить");
        System.out.println("2. Вычесть");
        System.out.println("3. Умножить");
        System.out.println("4. Разделить");
        System.out.println("5. Транспонировать");
        System.out.println("6. Определитель");
        System.out.println("7. Обратная матрица");
        System.out.println("0. Выход");
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return in.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Нужно ввести целое число");
                in.next();
            }
        }
    }

    private static int readPositiveInt(String prompt) {
        while (true) {
            int value = readInt(prompt);
            if (value > 0) {
                return value;
            }
            System.out.println("Число должно быть больше нуля");
        }
    }

    private static double readDouble() {
        while (true) {
            try {
                return in.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Нужно ввести число, попробуйте ещё раз");
                in.next();
            }
        }
    }

    private static Complex readComplex(String prompt) {
        System.out.print(prompt);
        double re = readDouble();
        double im = readDouble();
        return new Complex(re, im);
    }

    private static Matrix readMatrix(String name) {
        System.out.println("Матрица " + name);
        int rows = readPositiveInt("Число строк: ");
        int cols = readPositiveInt("Число столбцов: ");

        Matrix matrix = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                String prompt = name + "[" + (i + 1) + "][" + (j + 1) + "] (re im): ";
                matrix.set(i, j, readComplex(prompt));
            }
        }

        System.out.println("Введена матрица " + name + ":");
        System.out.println(matrix);
        return matrix;
    }

    private static void printResult(String title, Matrix result) {
        System.out.println(title + " =");
        System.out.println(result);
    }
}