package apr.linear.decompose;

import apr.linear.exceptions.SingularMatrixException;
import apr.linear.matrix.IMatrix;
import apr.linear.util.MatrixUtils;
import apr.linear.util.Operations;
import apr.linear.vector.IVector;
import apr.linear.vector.Vector;

public class LUPDecomposer extends LUDecomposer {

    protected IVector P;
    private boolean isSwapCountEven;

    public LUPDecomposer(IMatrix matrix) {
        super(matrix);
        decompose();
    }

    private void decompose() {
        P = new Vector(0, rowDimension);

        for (int i = 0, n = rowDimension - 1; i < n; i++) {
            int pivot = i;
            for (int j = i + 1, m = n + 1; j < m; j++) {
                if (Math.abs(matrix.get(j, i)) > Math.abs(matrix.get(pivot, i))) {
                    pivot = j;
                }
            }

            if (pivot != i) {
                P.swap(i, pivot);
                matrix.swapRows(i, pivot);
                isSwapCountEven = !isSwapCountEven;
            }

            for (int j = i + 1, m = n + 1; j < m; j++) {
                if (Math.abs(matrix.get(i, i)) < MatrixUtils.EPSILON) throw new SingularMatrixException();

                matrix.set(j, i, matrix.get(j, i) / matrix.get(i, i));
                for (int k = i + 1; k < m; k++) {
                    matrix.set(j, k, matrix.get(j, k) - matrix.get(j, i) * matrix.get(i, k));
                }
            }
        }
    }

    public IVector getPivot() {
        return P;
    }

    @Override
    public double getDeterminant() {
        return (isSwapCountEven ? 1 : -1) * super.getDeterminant();
    }

    @Override
    public LinearEquationSolver solver() {
        return new LUPSolver(this);
    }

    protected static class LUPSolver extends LUSolver {

        protected final IVector P;

        public LUPSolver(LUPDecomposer decomposer) {
            super(decomposer);
            P = decomposer.getPivot();
        }

        @Override
        public IVector solve(IVector b) {
            return super.solve(Operations.permute(b, P));
        }

        @Override
        public IMatrix solve(IMatrix b) {
            return super.solve(Operations.permute(b, P));
        }
    }
}
