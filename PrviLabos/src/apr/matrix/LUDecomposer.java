package apr.matrix;

import java.security.InvalidParameterException;

public class LUDecomposer extends AbstractMatrixDecomposer {

    public LUDecomposer(IMatrix matrix) {
        super(matrix);
    }

    @Override
    public boolean isApplicable(IMatrix matrix) {
        return Matrices.isSquareMatrix(matrix);
    }

    @Override
    public IMatrix[] decompose() {
        for (int i = 0, n = matrix.rows() - 1; i < n; i++) {
            for (int j = i + 1, m = n + 1; j < m; j++) {
                if (Math.abs(matrix.get(i, i)) < Matrices.EPSILON) throw new InvalidParameterException();

                matrix.set(j, i, matrix.get(j, i) / matrix.get(i, i));
                for (int k = i + 1; k < m; k++) {
                    matrix.set(j, k, matrix.get(j, k) - matrix.get(j, i) * matrix.get(i, k));
                }
            }
        }

        return new IMatrix[]{matrix};
    }
}
