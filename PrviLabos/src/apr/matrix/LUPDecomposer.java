package apr.matrix;

import java.security.InvalidParameterException;

public class LUPDecomposer extends AbstractMatrixDecomposer {

    public LUPDecomposer(IMatrix matrix) {
        super(matrix);
    }

    @Override
    public boolean isApplicable(IMatrix matrix) {
        return Matrices.isSquareMatrix(matrix);
    }

    @Override
    public IMatrix[] decompose() {
        IVector P = new Vector(0, matrix.rows());

        for (int i = 0, n = matrix.rows() - 1; i < n; i++) {
            int pivot = i;
            for (int j = i + 1, m = n + 1; j < m; j++) {
                if (Math.abs(matrix.get((int) P.get(j), i)) > Math.abs(matrix.get((int) P.get(j), i))) {
                    pivot = j;
                }
            }

            if (pivot != i) {
                P.swap(i, pivot);
                matrix.swapRows(i, pivot);
            }

            for (int j = i + 1, m = n + 1; j < m; j++) {
                if (Math.abs(matrix.get(i, i)) < Matrices.EPSILON) throw new InvalidParameterException();

                matrix.set((int) P.get(j), i, matrix.get(j, i) / matrix.get(i, i));
                for (int k = i + 1; k < m; k++) {
                    matrix.set(j, k, matrix.get(j, k) - matrix.get(j, i) * matrix.get(i, k));
                }
            }
        }

        return new IMatrix[]{matrix, P.toMatrix()};
    }
}
