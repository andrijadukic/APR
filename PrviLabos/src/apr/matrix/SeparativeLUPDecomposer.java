package apr.matrix;

public class SeparativeLUPDecomposer extends LUPDecomposer {
    
    public SeparativeLUPDecomposer(IMatrix matrix) {
        super(matrix);
    }

    @Override
    public IMatrix[] decompose() {
        int n = matrix.rows();

        IMatrix[] LUP = super.decompose();
        IMatrix LU = LUP[0];
        IMatrix P = LUP[1];

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

        return new IMatrix[]{L, U, P};
    }
}
