package apr.linear.decompose;

import apr.linear.exceptions.SingularMatrixException;
import apr.linear.matrix.IMatrix;
import apr.linear.util.Matrices;
import apr.linear.util.MatrixUtils;
import apr.linear.util.Operations;
import apr.linear.vector.IVector;
import apr.linear.vector.Vector;

/**
 * Class defining an object used for LUP decomposition of a matrix and storing the resulting L and U matrices and the permutation vector
 */
public class LUPDecomposer extends AbstractMatrixDecomposer {

    private IMatrix L;
    private IMatrix U;
    private IVector P;
    private boolean isSwapCountEven;

    public LUPDecomposer(IMatrix matrix) {
        super(matrix);
        decompose();
    }

    @Override
    public boolean isApplicable(IMatrix matrix) {
        return Matrices.isSquareMatrix(matrix);
    }

    /**
     * Performs LUP decomposition
     */
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

    /**
     * Gets the cached L matrix
     * @return L matrix
     */
    public IMatrix getL() {
        if (L != null) return L;

        L = Matrices.blankSquare(rowDimension, matrix::newInstance);
        for (int i = 0; i < rowDimension; i++) {
            L.set(i, i, 1);
            for (int j = 0; j < i; j++) {
                L.set(i, j, matrix.get(i, j));
            }
        }
        return L;
    }

    /**
     * Gets the cached U matrix
     * @return U matrix
     */
    public IMatrix getU() {
        if (U != null) return L;

        IMatrix U = Matrices.blankSquare(rowDimension, matrix::newInstance);
        for (int i = 0; i < rowDimension; i++) {
            for (int j = i; j < rowDimension; j++) {
                U.set(i, j, matrix.get(i, j));
            }
        }
        return U;
    }

    /**
     * Gets the permution vector
     * @return permutation vector
     */
    public IVector getPivot() {
        return P;
    }

    @Override
    public double getDeterminant() {
        double det = (isSwapCountEven ? 1 : -1);
        for (int i = 0; i < rowDimension; i++) {
            det *= matrix.get(i, i);
        }
        return det;
    }

    @Override
    public LinearEquationSolver solver() {
        return new LUPSolver(this);
    }

    /**
     * Private static class implementing the LinearEquationSolver interface by using LU decomposition
     */
    private static class LUPSolver implements LinearEquationSolver {

        private final IMatrix L;
        private final IMatrix U;
        private final IVector P;
        private final int n;

        public LUPSolver(LUPDecomposer decomposer) {
            L = decomposer.getL();
            U = decomposer.getU();
            P = decomposer.getPivot();
            n = P.getDimension();
        }

        @Override
        public IVector solve(IVector b) {
            return Operations.backwardSubstitution(U, Operations.forwardSubstitution(L, Operations.permute(b, P)));
        }

        @Override
        public IMatrix solve(IMatrix b) {
            IVector[] x = new Vector[n];

            for (int i = 0; i < n; i++) {
                x[i] = solve(b.getColumn(i));
            }

            IMatrix result = Matrices.blankSquare(n, b::newInstance);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    result.set(i, j, x[j].get(i));
                }
            }
            return result;
        }

        @Override
        public IMatrix invert() {
            return solve(Matrices.identity(n, L::newInstance));
        }
    }
}
