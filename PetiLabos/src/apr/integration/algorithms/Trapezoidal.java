package apr.integration.algorithms;

import apr.linear.decompose.LUPDecomposer;
import apr.linear.matrix.Matrices;
import apr.linear.matrix.Matrix;
import apr.linear.vector.Vector;

public final class Trapezoidal extends AbstractLinearSystemIntegrator {

    private Matrix R;
    private Matrix S;

    @Override
    protected void init(Matrix A, Matrix B, double T) {
        Matrix identity = Matrices.identity(A.getRowDimension());
        Matrix halvedA = A.multiply(T / 2);
        Matrix inverse = new LUPDecomposer(identity.subtract(halvedA)).solver().invert();
        R = inverse.multiply(identity.add(halvedA));
        S = inverse.multiply(T / 2).multiply(B);
    }

    @Override
    protected Vector doStep(Vector xk, Vector r) {
        return R.multiply(xk).add(S.multiply(r));
    }

    @Override
    public String getName() {
        return "Trapezoidal";
    }
}
