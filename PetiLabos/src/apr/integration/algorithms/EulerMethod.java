package apr.integration.algorithms;

import apr.functions.UnivariateVectorFunction;
import apr.integration.exceptions.IntegratorNotInitializedException;
import apr.linear.matrix.Matrices;
import apr.linear.matrix.Matrix;
import apr.linear.vector.Vector;

public final class EulerMethod extends AbstractExplicitLinearSystemIntegrator {

    private Matrix M;
    private Matrix N;

    @Override
    protected void initialize(Matrix A, Matrix B, double T) {
        M = Matrices.identity(A.getRowDimension()).add(A.multiply(T));
        N = B.multiply(T);
    }

    @Override
    protected Vector next(Vector xk, UnivariateVectorFunction r, double t) {
        return M.multiply(xk).add(N.multiply(r.valueAt(t)));
    }

    @Override
    public String getName() {
        return "Euler method";
    }
}
