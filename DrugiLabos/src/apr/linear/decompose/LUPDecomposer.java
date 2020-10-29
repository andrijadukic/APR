package apr.linear.decompose;

import apr.linear.exceptions.SingularMatrixException;
import apr.linear.matrix.IMatrix;
import apr.linear.util.linalg.LinearAlgebra;
import apr.linear.util.Matrices;
import apr.linear.vector.IVector;
import apr.linear.vector.Vector;


/**
 * Class defining an object used for LUP decomposition of a matrix and storing the resulting L and U matrices and the permutation vector
 */
public class LUPDecomposer extends AbstractMatrixDecomposer {

    private IMatrix L;
    private IMatrix U;
    private IVector P;
    private boolean isSwapCountEven = true;

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

            if (Math.abs(matrix.get(i, i)) < LinearAlgebra.EPSILON) throw new SingularMatrixException();

            for (int j = i + 1, m = n + 1; j < m; j++) {
                matrix.set(j, i, matrix.get(j, i) / matrix.get(i, i));
                for (int k = i + 1; k < m; k++) {
                    matrix.set(j, k, matrix.get(j, k) - matrix.get(j, i) * matrix.get(i, k));
                }
            }
        }
    }

    /**
     * Gets the cached L matrix
     *
     * @return L matrix
     */
    public IMatrix getL() {
        if (L != null) return L;

        L = Matrices.zeroes(rowDimension, matrix::newInstance);
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
     *
     * @return U matrix
     */
    public IMatrix getU() {
        if (U != null) return L;

        IMatrix U = Matrices.zeroes(rowDimension, matrix::newInstance);
        for (int i = 0; i < rowDimension; i++) {
            for (int j = i; j < rowDimension; j++) {
                U.set(i, j, matrix.get(i, j));
            }
        }
        return U;
    }

    /**
     * Gets the permutation vector
     *
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
    public ILinearEquationSolver solver() {
        return new LUPSolver(this);
    }

    /**
     * Private static class implementing the LinearEquationSolver interface by using LUP decomposition
     */
    private static class LUPSolver implements ILinearEquationSolver {

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
            return LinearAlgebra.backwardSubstitution(U, LinearAlgebra.forwardSubstitution(L, LinearAlgebra.permute(b, P)));
        }

        @Override
        public IMatrix invert() {
            IMatrix identity = Matrices.ones(n, L::newInstance);

            IVector[] x = new Vector[n];

            for (int i = 0; i < n; i++) {
                x[i] = solve(identity.getColumn(i));
            }

            IMatrix result = Matrices.zeroes(n, identity::newInstance);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    result.set(i, j, x[j].get(i));
                }
            }
            return result;
        }
    }
}
