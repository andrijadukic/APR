package apr.matrix;

import java.security.InvalidParameterException;

public class LUDecomposer extends AbstractMatrixDecomposer {

    protected IMatrix L;
    protected IMatrix U;

    public LUDecomposer(IMatrix matrix) {
        super(matrix);
    }

    @Override
    public boolean isApplicable(IMatrix matrix) {
        return Matrices.isSquareMatrix(matrix);
    }

    private void decompose() {
        for (int i = 0, n = rowDimension - 1; i < n; i++) {
            for (int j = i + 1; j < rowDimension; j++) {
                if (Math.abs(matrix.get(i, i)) < MatrixUtils.EPSILON) throw new SingularMatrixException();

                matrix.set(j, i, matrix.get(j, i) / matrix.get(i, i));
                for (int k = i + 1; k < rowDimension; k++) {
                    matrix.set(j, k, matrix.get(j, k) - matrix.get(j, i) * matrix.get(i, k));
                }
            }
        }
    }

    public IMatrix getLU() {
        return matrix;
    }

    public IMatrix getL() {
        if (L != null) return L;

        L = Matrices.blankSquare(rowDimension);
        for (int i = 0; i < rowDimension; i++) {
            L.set(i, i, 1);
            for (int j = 0; j < i; j++) {
                L.set(i, j, matrix.get(i, j));
            }
        }
        return L;
    }

    public IMatrix getU() {
        if (U != null) return L;

        IMatrix U = Matrices.blankSquare(rowDimension);
        for (int i = 0; i < rowDimension; i++) {
            for (int j = i; j < rowDimension; j++) {
                U.set(i, j, matrix.get(i, j));
            }
        }
        return U;
    }

    @Override
    public double getDeterminant() {
        double det = 1;
        for (int i = 0; i < rowDimension; i++) {
            det *= matrix.get(i, i);
        }
        return det;
    }

    @Override
    public ILinearEquationSolver solver() {
        return new LUSolver(this);
    }

    protected static class LUSolver implements ILinearEquationSolver {

        protected final IMatrix L;
        protected final IMatrix U;
        protected final int n;

        public LUSolver(LUDecomposer decomposer) {
            L = decomposer.getL();
            U = decomposer.getU();
            n = decomposer.rowDimension;
        }

        @Override
        public IVector solve(IVector b) {
            return Operations.backwardSubstitution(U, Operations.forwardSubstitution(L, b));
        }

        @Override
        public IMatrix solve(IMatrix b) {
            int n = b.getColumnDimension();
            IVector[] x = new IVector[n];

            for (int i = 0; i < n; i++) {
                x[i] = solve(b.getColumn(i));
            }

            return Matrices.columnVectorsToMatrix(x);
        }

        @Override
        public IMatrix invert() {
            return solve(Matrices.identity(n));
        }
    }
}
