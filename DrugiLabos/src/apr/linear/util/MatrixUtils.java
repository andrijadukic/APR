package apr.linear.util;

import apr.linear.matrix.IMatrix;
import apr.linear.vector.IVector;
import apr.linear.exceptions.DimensionMismatchException;
import apr.linear.exceptions.MatrixDimensionMismatchException;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Utility class with static methods useful for testing varius linear algebra properties
 */
public class MatrixUtils {

    public static final NumberFormat FORMATTER = new DecimalFormat("#.###");

    /**
     * Checks if dimensions of given matrices are the same
     *
     * @param m1 first matrix
     * @param m2 second matrix
     */
    public static void checkDimensionsSame(IMatrix m1, IMatrix m2) {
        int r1 = m1.getRowDimension();
        int c1 = m1.getColumnDimension();
        int r2 = m2.getRowDimension();
        int c2 = m2.getColumnDimension();

        if (!(r1 == r2 && c1 == c2)) throw new MatrixDimensionMismatchException(r1, c1, r2, c2);
    }


    /**
     * Checks if dimensions of given vertices are the same
     *
     * @param v1 first vector
     * @param v2 second vector
     */
    public static void checkDimensionsSame(IVector v1, IVector v2) {
        int d1 = v1.getDimension();
        int d2 = v2.getDimension();

        if (d1 != d2) throw new DimensionMismatchException(d1, d2);
    }

    /**
     * Checks if matrix-matrix addition of given matrices is applicable
     *
     * @param m1 first matrix
     * @param m2 second matrix
     */
    public static void checkAdditionApplicable(IMatrix m1, IMatrix m2) {
        checkDimensionsSame(m1, m2);
    }

    /**
     * Checks if vector-vector addition of given matrices is applicable
     *
     * @param v1 first vector
     * @param v2 second vector
     */
    public static void checkAdditionApplicable(IVector v1, IVector v2) {
        checkDimensionsSame(v1, v2);
    }

    /**
     * Checks if matrix-matrix multiplication of given matrices is applicable
     *
     * @param m1 first matrix
     * @param m2 second matrix
     */
    public static void checkMultiplicationApplicable(IMatrix m1, IMatrix m2) {
        int columnDimension = m1.getColumnDimension();
        int rowDimension = m2.getRowDimension();

        if (columnDimension != rowDimension) throw new DimensionMismatchException(columnDimension, rowDimension);
    }

    /**
     * Checks if matrix-vector addition of given matrices is applicable
     *
     * @param matrix matrix operand
     * @param vector vector operand
     */
    public static void checkMultiplicationApplicable(IMatrix matrix, IVector vector) {
        int columnDimension = matrix.getColumnDimension();
        int vectorDimension = vector.getDimension();

        if (columnDimension != vectorDimension) throw new DimensionMismatchException(columnDimension, vectorDimension);
    }

    /**
     * Checks if vector-vector addition of given matrices is applicable
     *
     * @param v1 first vector
     * @param v2 second vector
     */
    public static void checkMultiplicationApplicable(IVector v1, IVector v2) {
        checkDimensionsSame(v1, v2);
    }
}
