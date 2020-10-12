package apr.matrix;

import java.security.InvalidParameterException;

public class Operations {

    public static IVector forwardSubstitution(IMatrix matrix, IVector vector) {
        if (!isForwardSubstitutionApplicable(matrix, vector)) throw new InvalidParameterException();

        IVector result = vector.copy();
        for (int i = 0, n = matrix.getRowDimension() - 1; i < n; i++) {
            for (int j = i + 1, m = n + 1; j < m; j++) {
                result.set(j, result.get(j) - matrix.get(j, i) * result.get(i));
            }
        }
        return result;
    }

    public static IVector backwardSubstitution(IMatrix matrix, IVector vector) {
        if (!isBackwardSubstitutionApplicable(matrix, vector)) throw new InvalidParameterException();

        IVector result = vector.copy();
        for (int i = matrix.getRowDimension() - 1; i >= 0; i--) {
            if (Math.abs(matrix.get(i, i)) < MatrixUtils.EPSILON) throw new InvalidParameterException();

            result.set(i, result.get(i) / matrix.get(i, i));
            for (int j = 0; j < i; j++) {
                result.set(j, result.get(j) - matrix.get(j, i) * result.get(i));
            }
        }
        return result;
    }

    public static boolean isForwardSubstitutionApplicable(IMatrix matrix, IVector vector) {
        return vector.getDimension() == matrix.getRowDimension() && Matrices.isLowerTriangleMatrix(matrix);
    }

    public static boolean isBackwardSubstitutionApplicable(IMatrix matrix, IVector vector) {
        return vector.getDimension() == matrix.getRowDimension() && Matrices.isUpperTriangleMatrix(matrix);
    }

    public static IMatrix permute(IMatrix matrix, IVector permutationVector) {
        if (matrix.getRowDimension() != permutationVector.getDimension()) throw new InvalidParameterException();

        IMatrix result = matrix.copy();

        for (int i = 0, n = permutationVector.getDimension(); i < n; i++) {
            if (permutationVector.get(i) == i) continue;

            result.swapRows(i, (int) permutationVector.get(i));
        }
        return result;
    }

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
