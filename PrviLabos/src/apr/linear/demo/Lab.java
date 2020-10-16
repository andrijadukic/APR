package apr.linear.demo;

import apr.linear.decompose.LUDecomposer;
import apr.linear.decompose.LUPDecomposer;
import apr.linear.decompose.MatrixDecomposer;
import apr.linear.io.IMatrixLoader;
import apr.linear.io.IMatrixWriter;
import apr.linear.io.MatrixFileLoader;
import apr.linear.io.MatrixFileWriter;
import apr.linear.matrix.IMatrix;
import apr.linear.matrix.Matrix;
import apr.linear.vector.IVector;
import apr.linear.vector.Vector;

import java.io.IOException;
import java.io.PrintWriter;

public class Lab {

    public static final IMatrixLoader loader = new MatrixFileLoader(Matrix::new);
    public static final IMatrixWriter writer = new MatrixFileWriter();

    public static void main(String[] args) throws IOException {
        System.out.println("Zadatak 2:");
        IMatrix m1 = loader.load("data/m1.txt");
        IVector b1 = loader.load("data/b1.txt").toColumnVectors()[0];
        trySolveWithLUAndLUP(m1, b1, "r1");

        System.out.println();

        System.out.println("Zadatak 3:");
        IMatrix m2 = loader.load("data/m2.txt");
        IVector b2 = new Vector(1, 1, 1);
        trySolveWithLUAndLUP(m2, b2, "r2");

        System.out.println();

        System.out.println("Zadatak 4:");
        IMatrix m3 = loader.load("data/m3.txt");
        IVector b3 = loader.load("data/b2.txt").toColumnVectors()[0];
        trySolveWithLUAndLUP(m3, b3, "r3");

        System.out.println();

        System.out.println("Zadatak 5:");
        IMatrix m4 = loader.load("data/m4.txt");
        IVector b4 = loader.load("data/b3.txt").toColumnVectors()[0];
        trySolveWithLUP(m4, b4, "r4");

        System.out.println();

        System.out.println("Zadatak 6:");
        IMatrix m5 = loader.load("data/m5.txt");
        IVector b5 = loader.load("data/b4.txt").toColumnVectors()[0];
        trySolveWithLUP(m5, b5, "r5");

        System.out.println();

        System.out.println("Zadatak 7:");
        IMatrix m6 = loader.load("data/m6.txt");
        try {
            IMatrix r6 = new LUPDecomposer(m6).solver().invert();
            r6.print();
            writer.write(r6, "data/r6.txt");
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println("Ne postoji inverz dane matrice");
        }

        System.out.println();

        System.out.println("Zadatak 8:");
        IMatrix m7 = loader.load("data/m7.txt");
        try {
            double determinant = new LUPDecomposer(m7).getDeterminant();
            System.out.println("Determinanta: " + determinant);
            new PrintWriter("data/r7.txt").print(determinant);
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        System.out.println();

        System.out.println("Zadatak 9:");
        IMatrix m9 = loader.load("data/m9.txt");
        try {
            double determinant = new LUPDecomposer(m9).getDeterminant();
            System.out.println("Determinanta: " + determinant);
            new PrintWriter("data/r8.txt").print(determinant);
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void trySolveWithLUAndLUP(IMatrix A, IVector b, String resultName) {
        trySolveWithLU(A, b, resultName);
        trySolveWithLUP(A, b, resultName);
    }

    private static void trySolveWithLU(IMatrix A, IVector b, String resultName) {
        try {
            System.out.print("LU dekompozicija: ");
            IMatrix r2 = new LUDecomposer(A).solver().solve(b).toMatrix();
            System.out.println("Sustav je rijesiv LU dekompozicijom");
            System.out.println("x = " + r2);
            writer.write(r2, "data/" + resultName + "LU.txt");
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
        };
    }

    private static void trySolveWithLUP(IMatrix A, IVector b, String resultName) {
        try {
            System.out.print("LUP dekompozicija: ");
            IMatrix r2 = new LUPDecomposer(A).solver().solve(b).toMatrix();
            System.out.println("Sustav je rijesiv LUP dekompozicijom");
            System.out.println("x = " + r2);
            writer.write(r2, "data/" + resultName + "LUP.txt");
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}