package apr.linear.util;

import apr.linear.matrix.IMatrix;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

/**
 * Utility class with factory methods for building matrices
 */
public class Matrices {

    public static final NumberFormat FORMATTER = new DecimalFormat("#.###");

    /**
     * Builds a new blank matrix
     *
     * @param rows    row dimension of new matrix
     * @param columns column dimension of new matrix
     * @param builder builder object used to dynamically create an instance of an IMatrix
     * @return new blank matrix
     */
    public static IMatrix blank(int rows, int columns, IMatrixBuilder builder) {
        return builder.build(rows, columns);
    }

    /**
     * Builds a new blank square matrix
     *
     * @param dimension dimension of new matrix
     * @param builder   builder object used to dynamically create an instance of an IMatrix
     * @return new blank matrix
     */
    public static IMatrix blankSquare(int dimension, IMatrixBuilder builder) {
        return blank(dimension, dimension, builder);
    }

    /**
     * Builds a new identity matrix
     *
     * @param dimension dimension of new matrix
     * @param builder   builder object used to dynamically create an instance of an IMatrix
     * @return new identity matrix
     */
    public static IMatrix identity(int dimension, IMatrixBuilder builder) {
        return diagonal(dimension, 1., builder);
    }

    /**
     * Builds a new diagonal matrix
     *
     * @param dimension dimension of new matrix
     * @param builder   builder object used to dynamically create an instance of an IMatrix
     * @return new identity matrix
     */
    public static IMatrix diagonal(int dimension, double value, IMatrixBuilder builder) {
        IMatrix matrix = builder.build(dimension, dimension);

        for (int i = 0; i < dimension; i++) {
            matrix.set(i, i, value);
        }

        return matrix;
    }

    /**
     * Builds a new matrix filled with random values
     *
     * @param rows    row dimension of new matrix
     * @param columns column dimension of new matrix
     * @param builder builder object used to dynamically create an instance of an IMatrix
     * @param random  random object used to call nextDouble() method
     * @return new random matrix
     */
    public static IMatrix random(int rows, int columns, IMatrixBuilder builder, Random random) {
        IMatrix matrix = blank(rows, columns, builder);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix.set(i, j, random.nextDouble());
            }
        }

        return matrix;
    }

    /**
     * Builds a new matrix filled with random values
     *
     * @param dimension dimension of new matrix
     * @param builder   builder object used to dynamically create an instance of an IMatrix
     * @param random    random object used to call nextDouble() method
     * @return new random matrix
     */
    public static IMatrix squareRandom(int dimension, IMatrixBuilder builder, Random random) {
        return random(dimension, dimension, builder, random);
    }

    /**
     * Tests if given matrix is square
     *
     * @param matrix matrix to be tested
     * @return true if matrix is square, else otherwise
     */
    public static boolean isSquareMatrix(IMatrix matrix) {
        return matrix.getRowDimension() == matrix.getColumnDimension();
    }

    /**
     * Tests if given matrix is lower triangle matrix
     *
     * @param matrix matrix to be tested
     * @return true if matrix is lower triangle matrix, else otherwise
     */
    public static boolean isLowerTriangleMatrix(IMatrix matrix) {
        if (!isSquareMatrix(matrix)) return false;

        for (int i = 0, n = matrix.getRowDimension(); i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (matrix.get(i, j) > MatrixUtils.EPSILON) return false;
            }
        }

        return true;
    }

    /**
     * Tests if given matrix is upper triangle matrix
     *
     * @param matrix matrix to be tested
     * @return true if matrix is upper triangle matrix, else otherwise
     */
    public static boolean isUpperTriangleMatrix(IMatrix matrix) {
        if (!isSquareMatrix(matrix)) return false;

        for (int i = 0, n = matrix.getRowDimension(); i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (matrix.get(i, j) > MatrixUtils.EPSILON) return false;
            }
        }

        return true;
    }

    /**
     * Tests if given array is transformable to a IMatrix object
     *
     * @param array array to be tested
     * @return true if array is transformable to a IMatrix object, else otherwise
     */
    public static boolean isTransformableToMatrix(double[][] array) {
        int columns = array[0].length;

        for (int i = 1, n = array.length; i < n; i++) {
            if (array[i].length != columns) return false;
        }

        return true;
    }
}
