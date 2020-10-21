package apr.linear.util;

import apr.linear.exceptions.DimensionMismatchException;
import apr.linear.exceptions.SingularMatrixException;
import apr.linear.matrix.IMatrix;
import apr.linear.vector.IVector;

import java.security.InvalidParameterException;

/**
 * Utility class with static methods for performing various linear algebra operations
 */
public class Operations {

    /**
     * Performs forward substitution Ly = b
     *
     * @param matrix L matrix
     * @param vector b vector
     * @return result vector y
     */
    public static IVector forwardSubstitution(IMatrix matrix, IVector vector) {
        if (!isForwardSubstitutionApplicable(matrix, vector))
            throw new InvalidParameterException("Forward substitution is not applicable with these parameters");

        IVector result = vector.copy();
        for (int i = 0, n = matrix.getRowDimension() - 1; i < n; i++) {
            for (int j = i + 1, m = n + 1; j < m; j++) {
                result.set(j, result.get(j) - matrix.get(j, i) * result.get(i));
            }
        }
        return result;
    }

    /**
     * Performs backward substitution Ux = y
     *
     * @param matrix U matrix
     * @param vector y vector
     * @return result vector x
     */
    public static IVector backwardSubstitution(IMatrix matrix, IVector vector) {
        if (!isBackwardSubstitutionApplicable(matrix, vector))
            throw new InvalidParameterException("Backward substitution is not applicable with these parameters");

        IVector result = vector.copy();
        for (int i = matrix.getRowDimension() - 1; i >= 0; i--) {
            if (Math.abs(matrix.get(i, i)) < Matrices.EPSILON) throw new SingularMatrixException();

            result.set(i, result.get(i) / matrix.get(i, i));
            for (int j = 0; j < i; j++) {
                result.set(j, result.get(j) - matrix.get(j, i) * result.get(i));
            }
        }
        return result;
    }

    /**
     * Tests if forward substitution is applicable with these parameters
     * (vector b is of the same dimensions as the row count of matrix L and matrix L is lower triangle matrix)
     *
     * @param matrix L matrix
     * @param vector b vector
     * @return true if forward substitution is applicable, false otherwise
     */
    public static boolean isForwardSubstitutionApplicable(IMatrix matrix, IVector vector) {
        return vector.getDimension() == matrix.getRowDimension() && Matrices.isLowerTriangleMatrix(matrix);
    }

    /**
     * Tests if backward substitution is applicable with these parameters
     * (vector y is of the same dimensions as the row count of matrix U and matrix U is upper triangle matrix)
     *
     * @param matrix U matrix
     * @param vector y vector
     * @return true if backward substitution is applicable, false otherwise
     */
    public static boolean isBackwardSubstitutionApplicable(IMatrix matrix, IVector vector) {
        return vector.getDimension() == matrix.getRowDimension() && Matrices.isUpperTriangleMatrix(matrix);
    }

    /**
     * Performs row permutation of a matrix with the given permutation vector
     *
     * @param matrix            matrix to be permuted
     * @param permutationVector vector with desired indices order
     * @return permuted matrix
     */
    public static IMatrix permute(IMatrix matrix, IVector permutationVector) {
        if (matrix.getRowDimension() != permutationVector.getDimension())
            throw new DimensionMismatchException(matrix.getRowDimension(), permutationVector.getDimension());

        IMatrix result = matrix.copy();

        for (int i = 0, n = permutationVector.getDimension(); i < n; i++) {
            if (permutationVector.get(i) == i) continue;

            result.swapRows(i, (int) permutationVector.get(i));
        }
        return result;
    }

    /**
     * Performs permutation of a vector with the given permutation vector
     *
     * @param vector            vector to be permuted
     * @param permutationVector vector with desired indices order
     * @return permuted vector
     */
    public static IVector permute(IVector vector, IVector permutationVector) {
        int dimension = vector.getDimension();

        if (dimension != permutationVector.getDimension()) throw new InvalidParameterException();

        IVector result = vector.copy();

        for (int i = 0; i < dimension; i++) {
            result.set(i, vector.get((int) permutationVector.get(i)));
        }
        return result;
    }
}
