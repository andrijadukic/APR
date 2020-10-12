package apr.linear.util;

import apr.linear.matrix.IMatrix;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

public class Matrices {

    public static final NumberFormat FORMATTER = new DecimalFormat("#.###");

    public static IMatrix blank(int rows, int columns, IMatrixBuilder builder) {
        return builder.build(rows, columns);
    }

    public static IMatrix blankSquare(int dimension, IMatrixBuilder builder) {
        return blank(dimension, dimension, builder);
    }

    public static IMatrix identity(int dimension, IMatrixBuilder builder) {
        return diagonal(dimension, 1., builder);
    }

    public static IMatrix diagonal(int dimension, double value, IMatrixBuilder builder) {
        IMatrix matrix = builder.build(dimension, dimension);

        for (int i = 0; i < dimension; i++) {
            matrix.set(i, i, value);
        }

        return matrix;
    }

    public static IMatrix random(int rows, int columns, IMatrixBuilder builder, Random random) {
        IMatrix matrix = blank(rows, columns, builder);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix.set(i, j, random.nextDouble());
            }
        }

        return matrix;
    }

    public static IMatrix squareRandom(int dimension, IMatrixBuilder builder, Random random) {
        return random(dimension, dimension, builder, random);
    }

    public static boolean isSquareMatrix(IMatrix matrix) {
        return matrix.getRowDimension() == matrix.getColumnDimension();
    }

    public static boolean isLowerTriangleMatrix(IMatrix matrix) {
        if (!isSquareMatrix(matrix)) return false;

        for (int i = 0, n = matrix.getRowDimension(); i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (matrix.get(i, j) > MatrixUtils.EPSILON) return false;
            }
        }

        return true;
    }

    public static boolean isUpperTriangleMatrix(IMatrix matrix) {
        if (!isSquareMatrix(matrix)) return false;

        for (int i = 0, n = matrix.getRowDimension(); i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (matrix.get(i, j) > MatrixUtils.EPSILON) return false;
            }
        }

        return true;
    }

    public static boolean isMatrixArray(double[][] array) {
        int columns = array[0].length;

        for (int i = 1, n = array.length; i < n; i++) {
            if (array[i].length != columns) return false;
        }

        return true;
    }
}
