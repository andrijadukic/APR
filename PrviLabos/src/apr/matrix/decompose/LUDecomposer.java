package apr.matrix.decompose;

import apr.matrix.*;
import apr.matrix.exceptions.SingularMatrixException;
import apr.matrix.util.Matrices;
import apr.matrix.util.MatrixUtils;
import apr.matrix.util.Operations;

public class LUDecomposer extends AbstractMatrixDecomposer {

    protected RealMatrix L;
    protected RealMatrix U;

    public LUDecomposer(RealMatrix matrix) {
        super(matrix);
    }

    @Override
    public boolean isApplicable(RealMatrix matrix) {
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

    public RealMatrix getLU() {
        return matrix;
    }

    public RealMatrix getL() {
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

    public RealMatrix getU() {
        if (U != null) return L;

        RealMatrix U = Matrices.blankSquare(rowDimension);
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
    public LinearEquationSolver solver() {
        return new LUSolver(this);
    }

    protected static class LUSolver implements LinearEquationSolver {

        protected final RealMatrix L;
        protected final RealMatrix U;
        protected final int n;

        public LUSolver(LUDecomposer decomposer) {
            L = decomposer.getL();
            U = decomposer.getU();
            n = decomposer.rowDimension;
        }

        @Override
        public RealVector solve(RealVector b) {
            return Operations.backwardSubstitution(U, Operations.forwardSubstitution(L, b));
        }

        @Override
        public RealMatrix solve(RealMatrix b) {
            int n = b.getColumnDimension();
            RealVector[] x = new ArrayRealVector[n];

            for (int i = 0; i < n; i++) {
                x[i] = solve(b.getColumn(i));
            }

            return Matrices.columnRealVectorsToMatrix(x);
        }

        @Override
        public RealMatrix invert() {
            return solve(Matrices.identity(n));
        }
    }
}
