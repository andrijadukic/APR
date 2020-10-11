package apr.matrix;

public class SeparativeLUDecomposer extends LUDecomposer{

    public SeparativeLUDecomposer(IMatrix matrix) {
        super(matrix);
    }

    @Override
    public IMatrix[] decompose() {
        int n = matrix.rows();

        IMatrix LU = super.decompose()[0];

        IMatrix L = Matrices.blankSquare(n);
        for (int i = 0; i < n; i++) {
            L.set(i, i, 1);
            for (int j = 0; j < i; j++) {
                L.set(i, j, LU.get(i, j));
            }
        }

        IMatrix U = Matrices.blankSquare(n);
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                U.set(i, j, LU.get(i, j));
            }
        }

        return new IMatrix[]{L, U};
    }
}
