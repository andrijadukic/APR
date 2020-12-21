package apr.integration.algorithms;

import apr.linear.decompose.LUPDecomposer;
import apr.linear.matrix.Matrices;
import apr.linear.matrix.Matrix;
import apr.linear.vector.Vector;

public final class ReverseEulerMethod extends AbstractLinearSystemIntegrator {

    private Matrix P;
    private Vector Q;

    @Override
    protected void init(Matrix A, Vector B, double T) {
        int n = A.getRowDimension();
        Matrix identity = Matrices.identity(n);
        P = new LUPDecomposer(identity.subtract(A.multiply(T))).solver().invert();
        Q = P.multiply(T).multiply(B);
    }

    @Override
    protected Vector doStep(Vector xk) {
        return P.multiply(xk).add(Q);
    }

    @Override
    public String getName() {
        return "Reverse euler method";
    }
}
