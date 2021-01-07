package apr.integration.algorithms;

import apr.linear.matrix.Matrices;
import apr.linear.matrix.Matrix;
import apr.linear.vector.Vector;

public final class EulerMethod extends AbstractLinearSystemIntegrator {

    private Matrix M;
    private Matrix N;

    @Override
    protected void init(Matrix A, Matrix B, double T) {
        M = Matrices.identity(A.getRowDimension()).add(A.multiply(T));
        N = B.multiply(T);
    }

    @Override
    protected Vector doStep(Vector xk, Vector r) {
        return M.multiply(xk).add(N.multiply(r));
    }

    @Override
    public String getName() {
        return "Euler method";
    }
}
