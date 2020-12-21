package apr.integration;

import apr.linear.decompose.LUPDecomposer;
import apr.linear.matrix.Matrices;
import apr.linear.matrix.Matrix;
import apr.linear.vector.Vector;

public final class Trapezoidal extends AbstractLinearSystemIntegrator {

    private Matrix R;
    private Vector S;

    @Override
    protected void init(Matrix A, Vector B, double T) {
        int n = A.getRowDimension();
        Matrix identity = Matrices.identity(n);
        Matrix inverse = new LUPDecomposer(identity.subtract(A.multiply(T / 2))).solver().invert();
        R = inverse.multiply(identity.add(A.multiply(T / 2)));
        S = B.multiply(inverse.multiply(T));
    }

    @Override
    protected Vector next(Vector xk) {
        return xk.multiply(R).add(S);
    }
}
