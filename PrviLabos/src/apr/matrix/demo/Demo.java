package apr.matrix.demo;

import apr.matrix.*;
import apr.matrix.decompose.LUPDecomposer;
import apr.matrix.util.Matrices;

public class Demo {

    public static void main(String[] args) {
        RealMatrix matrix = new ArrayRealMatrix(
                new double[]{1, 2, 0},
                new double[]{3, 5, 4},
                new double[]{5, 6, 3});

        System.out.println("A matrix:");
        System.out.println(matrix);
        System.out.println();

        System.out.println("b vector:");
        RealVector vector = new ArrayRealVector(0.1, 12.5, 10.3);
        System.out.println(vector);
        System.out.println();

        LUPDecomposer decomposer = new LUPDecomposer(matrix);

        System.out.println("L matrix:");
        System.out.println(decomposer.getL());
        System.out.println();
        System.out.println("U matrix:");
        System.out.println(decomposer.getU());
        System.out.println();
        System.out.println("P matrix:");
        System.out.println(decomposer.getPivot());
        System.out.println();

        System.out.println("Equation solution:");
        System.out.println(decomposer.solver().solve(vector));
        System.out.println();

        System.out.println("Determinant of matrix:");
        RealMatrix det = new ArrayRealMatrix(
                new double[]{2, -3, 1},
                new double[]{2, 0, -1},
                new double[]{1, 4, 5});
        System.out.println(det);
        System.out.println("is:");
        System.out.println(Matrices.FORMATTER.format(new LUPDecomposer(det).getDeterminant()));
        System.out.println();

        System.out.println("Inverse of matrix:");
        RealMatrix inv = new ArrayRealMatrix(
                new double[]{4, 7},
                new double[]{2, 6});
        System.out.println(inv);
        System.out.println("is:");
        System.out.println(new LUPDecomposer(inv).solver().invert());
    }
}
