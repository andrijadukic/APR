package apr.linear.util.linalg;

import apr.linear.exceptions.DimensionMismatchException;
import apr.linear.exceptions.MatrixDimensionMismatchException;
import apr.linear.exceptions.SingularMatrixException;
import apr.linear.matrix.IMatrix;
import apr.linear.util.Matrices;
import apr.linear.util.functions.IDoubleBinaryFunction;
import apr.linear.util.functions.IDoubleUnaryFunction;
import apr.linear.vector.IVector;

import java.security.InvalidParameterException;
import java.util.function.DoublePredicate;

/**
 * Class implementing common linear algebra operations as static methods
 */
public class LinearAlgebra {

    public static final double EPSILON = 1e-6;

    /**
     * Performs matrix-matrix addition
     *
     * @param m1         first matrix
     * @param m2         second matrix
     * @param mutability if set to MUTABLE then result overwrites first operand
     * @return result matrix
     */
    public static IMatrix add(IMatrix m1, IMatrix m2, OperationMutability mutability) {
        return apply(m1, m2, Double::sum, mutability);
    }

    /**
     * Performs matrix-scalar addition
     *
     * @param matrix     matrix
     * @param value      scalar value
     * @param mutability if set to MUTABLE then result overwrites first operand
     * @return result matrix
     */
    public static IMatrix add(IMatrix matrix, double value, OperationMutability mutability) {
        if (value == 0) return (mutability == OperationMutability.MUTABLE) ? matrix : matrix.copy();
        return apply(matrix, x -> x + value, mutability);
    }

    /**
     * Performs vector-vector addition
     *
     * @param v1         first matrix
     * @param v2         second matrix
     * @param mutability if set to MUTABLE then result overwrites first operand
     * @return result vector
     */
    public static IVector add(IVector v1, IVector v2, OperationMutability mutability) {
        return apply(v1, v2, Double::sum, mutability);
    }

    /**
     * Performs vector-scalar addition
     *
     * @param vector     matrix
     * @param value      scalar value
     * @param mutability if set to MUTABLE then result overwrites first operand
     * @return result vector
     */
    public static IVector add(IVector vector, double value, OperationMutability mutability) {
        if (value == 0) return (mutability == OperationMutability.MUTABLE) ? vector : vector.copy();
        return apply(vector, x -> x + value, mutability);
    }

    /**
     * Performs matrix-matrix subtraction
     *
     * @param m1         first matrix
     * @param m2         second matrix
     * @param mutability if set to MUTABLE then result overwrites first operand
     * @return result matrix
     */
    public static IMatrix subtract(IMatrix m1, IMatrix m2, OperationMutability mutability) {
        return apply(m1, m2, (x, y) -> x - y, mutability);
    }

    /**
     * Performs matrix-scalar subtraction
     *
     * @param matrix     matrix
     * @param value      scalar value
     * @param mutability if set to MUTABLE then result overwrites first operand
     * @return result matrix
     */
    public static IMatrix subtract(IMatrix matrix, double value, OperationMutability mutability) {
        if (value == 0) return (mutability == OperationMutability.MUTABLE) ? matrix : matrix.copy();
        return apply(matrix, x -> x - value, mutability);

    }

    /**
     * Performs vector-vector subtraction
     *
     * @param v1         first matrix
     * @param v2         second matrix
     * @param mutability if set to MUTABLE then result overwrites first operand
     * @return result vector
     */
    public static IVector subtract(IVector v1, IVector v2, OperationMutability mutability) {
        return apply(v1, v2, (x, y) -> x - y, mutability);
    }

    /**
     * Performs vector-scalar subtraction
     *
     * @param vector     vector
     * @param value      scalar value
     * @param mutability if set to MUTABLE then result overwrites first operand
     * @return result vector
     */
    public static IVector subtract(IVector vector, double value, OperationMutability mutability) {
        if (value == 0) return (mutability == OperationMutability.MUTABLE) ? vector : vector.copy();
        return apply(vector, x -> x - value, mutability);

    }

    /**
     * Performs matrix-matrix multiplication
     *
     * @param m1 first matrix
     * @param m2 second matrix
     * @return result matrix
     */
    public static IMatrix multiply(IMatrix m1, IMatrix m2) {
        checkMultiplicationApplicable(m1, m2);

        int r1 = m1.getRowDimension();
        int c1 = m1.getColumnDimension();
        int c2 = m2.getColumnDimension();
        IMatrix result = m1.newInstance(r1, c2);

        for (int i = 0; i < r1; i++) {
            for (int j = 0; j < c2; j++) {
                double sum = 0.;
                for (int k = 0; k < c1; k++) {
                    sum += m1.get(i, k) * m2.get(k, j);
                }
                result.set(i, j, sum);
            }
        }
        return result;
    }

    /**
     * Performs vector-matrix multiplication
     *
     * @param vector vector
     * @param matrix matrix
     * @return result vector
     */
    public static IVector multiply(IVector vector, IMatrix matrix) {
        checkMultiplicationApplicable(matrix, vector);

        int n = matrix.getColumnDimension();
        IVector result = vector.newInstance(n);
        for (int i = 0; i < n; i++) {
            double sum = 0.;
            for (int j = 0, m = matrix.getRowDimension(); j < m; j++) {
                sum += matrix.get(j, i) * vector.get(j);
            }
            result.set(i, sum);
        }
        return result;
    }

    /**
     * Performs matrix-scalar multiplication
     *
     * @param matrix     matrix
     * @param scalar     scalar value
     * @param mutability if set to MUTABLE then result overwrites first operand
     * @return result matrix
     */
    public static IMatrix multiply(IMatrix matrix, double scalar, OperationMutability mutability) {
        if (scalar == 1) return (mutability == OperationMutability.MUTABLE) ? matrix : matrix.copy();
        return apply(matrix, x -> x * scalar, mutability);
    }

    /**
     * Performs vector-vector multiplication
     *
     * @param v1 first vector
     * @param v2 second vector
     * @return result scalar value
     */
    public static double multiply(IVector v1, IVector v2) {
        checkMultiplicationApplicable(v1, v2);

        double sum = 0.;
        for (int i = 0, n = v1.getDimension(); i < n; i++) {
            sum += v1.get(i) * v2.get(i);
        }
        return sum;
    }

    /**
     * Performs vector-scalar multiplication
     *
     * @param vector     matrix
     * @param scalar     scalar value
     * @param mutability if set to MUTABLE then result overwrites first operand
     * @return result vector
     */
    public static IVector multiply(IVector vector, double scalar, OperationMutability mutability) {
        if (scalar == 1) return mutability == OperationMutability.MUTABLE ? vector : vector.copy();
        return apply(vector, x -> x * scalar, mutability);
    }


    /**
     * Applies function to all elements of given matrix
     *
     * @param matrix     matrix
     * @param function   function to be applied
     * @param mutability if set to MUTABLE then result overwrites first operand
     * @return result matrix
     */
    public static IMatrix apply(IMatrix matrix, IDoubleUnaryFunction function, OperationMutability mutability) {
        int rowDimension = matrix.getRowDimension();
        int columnDimension = matrix.getColumnDimension();
        IMatrix result = (mutability == OperationMutability.MUTABLE) ? matrix : matrix.newInstance(rowDimension, columnDimension);
        for (int i = 0; i < rowDimension; i++) {
            for (int j = 0; j < columnDimension; j++) {
                result.set(i, j, function.apply(matrix.get(i, j)));
            }
        }
        return result;
    }

    /**
     * Applies function to all elements of given matrices
     *
     * @param m1         first matrix
     * @param m2         second matrix
     * @param function   function to be applied
     * @param mutability if set to MUTABLE then result overwrites first operand
     */
    public static IMatrix apply(IMatrix m1, IMatrix m2, IDoubleBinaryFunction function, OperationMutability mutability) {
        checkDimensionsSame(m1, m2);
        int rowDimension = m1.getRowDimension();
        int columnDimension = m1.getColumnDimension();
        IMatrix result = (mutability == OperationMutability.MUTABLE) ? m1 : m1.newInstance(rowDimension, columnDimension);
        for (int i = 0; i < rowDimension; i++) {
            for (int j = 0; j < columnDimension; j++) {
                result.set(i, j, function.apply(m1.get(i, j), m2.get(i, j)));
            }
        }
        return result;
    }

    /**
     * Applies function to all elements of given vector
     *
     * @param vector     vector
     * @param function   function to be applied
     * @param mutability if set to MUTABLE then result overwrites first operand
     * @return new vector
     */
    public static IVector apply(IVector vector, IDoubleUnaryFunction function, OperationMutability mutability) {
        int n = vector.getDimension();
        IVector result = (mutability == OperationMutability.MUTABLE) ? vector : vector.newInstance(n);
        for (int i = 0; i < n; i++) {
            result.set(i, function.apply(vector.get(i)));
        }
        return result;
    }

    /**
     * Applies function to all elements of given vectors
     *
     * @param v1         first vector
     * @param v2         second vector
     * @param function   function to be applied
     * @param mutability if set to MUTABLE then result overwrites first operand
     * @return new vector
     */
    public static IVector apply(IVector v1, IVector v2, IDoubleBinaryFunction function, OperationMutability mutability) {
        checkDimensionsSame(v1, v2);

        int n = v1.getDimension();
        IVector result = (mutability == OperationMutability.MUTABLE) ? v1 : v1.newInstance(n);
        for (int i = 0; i < n; i++) {
            result.set(i, function.apply(v1.get(i), v2.get(i)));
        }
        return result;
    }

    /**
     * Tests if any element matches predicate
     *
     * @param iterable  iterable object
     * @param predicate predicate to be tested
     * @return true if any element matches, false otherwise
     */
    public static boolean anyMatch(Iterable<Double> iterable, DoublePredicate predicate) {
        for (Double element : iterable) {
            if (predicate.test(element)) return true;
        }
        return false;
    }

    /**
     * Tests if all elements match predicate
     *
     * @param iterable  iterable object
     * @param predicate predicate to be tested
     * @return true if any element matches, false otherwise
     */
    public static boolean allMatch(Iterable<Double> iterable, DoublePredicate predicate) {
        return !anyMatch(iterable, x -> !predicate.test(x));
    }

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
            if (Math.abs(matrix.get(i, i)) < LinearAlgebra.EPSILON) throw new SingularMatrixException();

            result.set(i, result.get(i) / matrix.get(i, i));
            for (int j = 0; j < i; j++) {
                result.set(j, result.get(j) - matrix.get(j, i) * result.get(i));
            }
        }
        return result;
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

    /**
     * Checks if dimensions of given matrices are the same
     *
     * @param m1 first matrix
     * @param m2 second matrix
     */
    private static void checkDimensionsSame(IMatrix m1, IMatrix m2) {
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
    private static void checkDimensionsSame(IVector v1, IVector v2) {
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
    private static void checkAdditionApplicable(IMatrix m1, IMatrix m2) {
        checkDimensionsSame(m1, m2);
    }

    /**
     * Checks if vector-vector addition of given matrices is applicable
     *
     * @param v1 first vector
     * @param v2 second vector
     */
    private static void checkAdditionApplicable(IVector v1, IVector v2) {
        checkDimensionsSame(v1, v2);
    }

    /**
     * Checks if matrix-matrix multiplication of given matrices is applicable
     *
     * @param m1 first matrix
     * @param m2 second matrix
     */
    private static void checkMultiplicationApplicable(IMatrix m1, IMatrix m2) {
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
    private static void checkMultiplicationApplicable(IMatrix matrix, IVector vector) {
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
    private static void checkMultiplicationApplicable(IVector v1, IVector v2) {
        checkDimensionsSame(v1, v2);
    }

    /**
     * Tests if forward substitution is applicable with these parameters
     * (vector b is of the same dimensions as the row count of matrix L and matrix L is lower triangle matrix)
     *
     * @param matrix L matrix
     * @param vector b vector
     * @return true if forward substitution is applicable, false otherwise
     */
    private static boolean isForwardSubstitutionApplicable(IMatrix matrix, IVector vector) {
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
    private static boolean isBackwardSubstitutionApplicable(IMatrix matrix, IVector vector) {
        return vector.getDimension() == matrix.getRowDimension() && Matrices.isUpperTriangleMatrix(matrix);
    }
}
