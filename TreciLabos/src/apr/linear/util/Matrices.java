package apr.linear.util;

import apr.linear.matrix.IMatrix;
import apr.linear.util.builders.IMatrixBuilder;
import apr.linear.util.linalg.LinearAlgebra;
import apr.linear.util.linalg.OperationMutability;
import apr.linear.vector.IVector;
import apr.linear.util.builders.IVectorBuilder;

import java.util.Random;
import java.util.function.DoubleSupplier;

/**
 * Utility class with factory methods for building matrices
 */
public class Matrices {

    /**
     * Fills matrix with given value
     *
     * @param matrix matrix to be filled
     * @param value  value
     * @return filled matrix
     */
    public static IMatrix fill(IMatrix matrix, double value) {
        return LinearAlgebra.apply(matrix, x -> value, OperationMutability.MUTABLE);
    }

    /**
     * Fills matrix using given supplier
     *
     * @param matrix   matrix to be filled
     * @param supplier supplier
     * @return filled matrix
     */
    public static IMatrix fill(IMatrix matrix, DoubleSupplier supplier) {
        return LinearAlgebra.apply(matrix, x -> supplier.getAsDouble(), OperationMutability.MUTABLE);
    }

    /**
     * Builds a new blank matrix
     *
     * @param rows    row dimension of new matrix
     * @param columns column dimension of new matrix
     * @param builder builder object used to dynamically create an instance of an IMatrix
     * @return new blank matrix
     */
    public static IMatrix zeroes(int rows, int columns, IMatrixBuilder builder) {
        return fill(builder.build(rows, columns), 0);
    }

    /**
     * Builds a new blank square matrix
     *
     * @param dimension dimension of new matrix
     * @param builder   builder object used to dynamically create an instance of an IMatrix
     * @return new blank matrix
     */
    public static IMatrix zeroes(int dimension, IMatrixBuilder builder) {
        return zeroes(dimension, dimension, builder);
    }

    /**
     * Builds a new diagonal matrix
     *
     * @param dimension dimension of new matrix
     * @param builder   builder object used to dynamically create an instance of an IMatrix
     * @return new identity matrix
     */
    public static IMatrix diagonal(int dimension, double value, IMatrixBuilder builder) {
        IMatrix matrix = zeroes(dimension, builder);

        for (int i = 0; i < dimension; i++) {
            matrix.set(i, i, value);
        }

        return matrix;
    }

    /**
     * Builds a new identity matrix
     *
     * @param dimension dimension of new matrix
     * @param builder   builder object used to dynamically create an instance of an IMatrix
     * @return new identity matrix
     */
    public static IMatrix ones(int dimension, IMatrixBuilder builder) {
        return diagonal(dimension, 1., builder);
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
        return LinearAlgebra.apply(builder.build(rows, columns), x -> random.nextDouble(), OperationMutability.MUTABLE);
    }

    /**
     * Builds a new matrix filled with random values
     *
     * @param dimension dimension of new matrix
     * @param builder   builder object used to dynamically create an instance of IMatrix
     * @param random    random object used to call nextDouble() method
     * @return new random matrix
     */
    public static IMatrix random(int dimension, IMatrixBuilder builder, Random random) {
        return random(dimension, dimension, builder, random);
    }

    /**
     * Fills vector with given value
     *
     * @param vector vector to be filled
     * @param value  value
     * @return filled vector
     */
    public static IVector fill(IVector vector, double value) {
        return LinearAlgebra.apply(vector, x -> value, OperationMutability.MUTABLE);
    }

    /**
     * Fills vector using given supplier
     *
     * @param vector   vector to be filled
     * @param supplier supplier
     * @return filled vector
     */
    public static IVector fill(IVector vector, DoubleSupplier supplier) {
        return LinearAlgebra.apply(vector, x -> supplier.getAsDouble(), OperationMutability.MUTABLE);
    }

    /**
     * Builds a new null vector
     *
     * @param dimension dimension of null vector
     * @param builder   builder object used to dynamically create an instance of IVector
     * @return new null vector
     */
    public static IVector zeroes(int dimension, IVectorBuilder builder) {
        return fill(builder.build(dimension), 0);
    }

    public static IVector ones(int dimension, IVectorBuilder builder) {
        return fill(builder.build(dimension), 1);

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
                if (matrix.get(i, j) > LinearAlgebra.EPSILON) return false;
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
                if (matrix.get(i, j) > LinearAlgebra.EPSILON) return false;
            }
        }

        return true;
    }
}
