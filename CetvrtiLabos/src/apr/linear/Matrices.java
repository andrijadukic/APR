package apr.linear;

import apr.linear.exceptions.NonSquareMatrixException;
import apr.linear.matrix.Matrix;
import apr.linear.matrix.ArrayMatrix;
import apr.linear.linalg.LinearAlgebra;
import apr.linear.linalg.Mutability;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

/**
 * Utility class with factory methods for building matrices and checking properties
 */
public class Matrices {

    /**
     * Builds a new blank square matrix
     *
     * @param dimension dimension of new matrix
     * @return new blank matrix
     */
    public static Matrix zeroes(int dimension) {
        return zeroes(dimension, dimension);
    }

    /**
     * Builds a new blank matrix
     *
     * @param rows    row dimension of new matrix
     * @param columns column dimension of new matrix
     * @return new blank matrix
     */
    public static Matrix zeroes(int rows, int columns) {
        return zeroes(() -> new ArrayMatrix(rows, columns));
    }

    /**
     * Builds a new blank matrix
     *
     * @param supplier supplier object used to dynamically create an instance of an IMatrix
     * @return new blank matrix
     */
    public static Matrix zeroes(Supplier<Matrix> supplier) {
        return fill(supplier.get(), 0);
    }

    /**
     * Builds a new identity matrix
     *
     * @param dimension dimension of new matrix
     * @return new identity matrix
     */
    public static Matrix identity(int dimension) {
        return diagonal(dimension, 1.);
    }

    /**
     * Builds a new identity matrix
     *
     * @param supplier matrix supplier
     * @return new identity matrix
     */
    public static Matrix identity(Supplier<Matrix> supplier) {
        return diagonal(supplier, 1.);
    }

    /**
     * Builds a new diagonal matrix
     *
     * @param supplier matrix supplier
     * @param value    value to be put on diagonal
     * @return new identity matrix
     */
    public static Matrix diagonal(Supplier<Matrix> supplier, double value) {
        Matrix matrix = zeroes(supplier);

        int rowDimension = matrix.getRowDimension();

        if (!isSquareMatrix(matrix))
            throw new NonSquareMatrixException(rowDimension, matrix.getColumnDimension());

        for (int i = 0; i < rowDimension; i++) {
            matrix.set(i, i, value);
        }
        return matrix;
    }

    /**
     * Builds a new diagonal matrix
     *
     * @param dimension dimension of new matrix
     * @return new identity matrix
     */
    public static Matrix diagonal(int dimension, double value) {
        Matrix matrix = zeroes(dimension);
        for (int i = 0; i < dimension; i++) {
            matrix.set(i, i, value);
        }
        return matrix;
    }

    /**
     * Builds a new matrix filled with random values
     *
     * @param rows           row dimension of new matrix
     * @param columns        column dimension of new matrix
     * @param doubleSupplier supplier object
     * @return new random matrix
     */
    public static Matrix random(int rows, int columns, DoubleSupplier doubleSupplier) {
        return LinearAlgebra.apply(zeroes(rows, columns), x -> doubleSupplier.getAsDouble(), Mutability.MUTABLE);
    }

    /**
     * Builds a new matrix filled with random values
     *
     * @param dimension      dimension of new matrix
     * @param doubleSupplier supplier object
     * @return new random matrix
     */
    public static Matrix random(int dimension, DoubleSupplier doubleSupplier) {
        return random(dimension, dimension, doubleSupplier);
    }

    /**
     * Builds a new matrix filled with random values
     *
     * @param matrixSupplier matrix supplier object used to dynamically create an instance of an IMatrix
     * @param doubleSupplier double supplier
     * @return new random matrix
     */
    public static Matrix random(Supplier<Matrix> matrixSupplier, DoubleSupplier doubleSupplier) {
        return LinearAlgebra.apply(matrixSupplier.get(), x -> doubleSupplier.getAsDouble(), Mutability.MUTABLE);
    }

    /**
     * Fills matrix with given value
     *
     * @param matrix matrix to be filled
     * @param value  value
     * @return filled matrix
     */
    public static Matrix fill(Matrix matrix, double value) {
        return LinearAlgebra.apply(matrix, x -> value, Mutability.MUTABLE);
    }

    /**
     * Fills matrix using given supplier
     *
     * @param matrix   matrix to be filled
     * @param supplier supplier
     * @return filled matrix
     */
    public static Matrix fill(Matrix matrix, DoubleSupplier supplier) {
        return LinearAlgebra.apply(matrix, x -> supplier.getAsDouble(), Mutability.MUTABLE);
    }

    /**
     * Tests if given matrix is square
     *
     * @param matrix matrix to be tested
     * @return true if matrix is square, else otherwise
     */
    public static boolean isSquareMatrix(Matrix matrix) {
        return matrix.getRowDimension() == matrix.getColumnDimension();
    }

    /**
     * Tests if given matrix is lower triangle matrix
     *
     * @param matrix matrix to be tested
     * @return true if matrix is lower triangle matrix, else otherwise
     */
    public static boolean isLowerTriangleMatrix(Matrix matrix) {
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
    public static boolean isUpperTriangleMatrix(Matrix matrix) {
        if (!isSquareMatrix(matrix)) return false;

        for (int i = 0, n = matrix.getRowDimension(); i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (matrix.get(i, j) > LinearAlgebra.EPSILON) return false;
            }
        }

        return true;
    }
}