package apr.integration.algorithms;

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
        Matrix halvedA = A.multiply(T / 2);
        Matrix inverse = new LUPDecomposer(identity.subtract(halvedA)).solver().invert();
        R = inverse.multiply(identity.add(halvedA));
        S = B.multiply(inverse.multiply(T));
    }

    @Override
    protected Vector doStep(Vector xk) {
        return xk.multiply(R).add(S);
    }

    @Override
    public String getName() {
        return "Trapezoidal";
    }
}
