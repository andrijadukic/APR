package apr.integration.algorithms;

import apr.linear.matrix.Matrices;
import apr.linear.matrix.Matrix;
import apr.linear.vector.Vector;

public final class EulerMethod extends AbstractLinearSystemIntegrator {

    private Matrix M;
    private Vector N;

    @Override
    protected void init(Matrix A, Vector B, double T) {
        int n = A.getRowDimension();
        Matrix identity = Matrices.identity(n);
        M = identity.add(A.multiply(T));
        N = B.multiply(T);
    }

    @Override
    protected Vector doStep(Vector xk) {
        return xk.multiply(M).add(N);
    }

    @Override
    public String getName() {
        return "Euler method";
    }
}
