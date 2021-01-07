package apr.integration.algorithms;

import apr.functions.UnivariateVectorFunction;
import apr.linear.decompose.LUPDecomposer;
import apr.linear.matrix.Matrices;
import apr.linear.matrix.Matrix;
import apr.linear.vector.Vector;

public final class BackwardEuler extends AbstractLinearSystemIntegrator {

    private Matrix P;
    private Matrix Q;
    private double T;

    @Override
    protected void init(Matrix A, Matrix B, double T) {
        P = new LUPDecomposer(Matrices.identity(A.getRowDimension()).subtract(A.multiply(T))).solver().invert();
        Q = P.multiply(T).multiply(B);
        this.T = T;
    }

    @Override
    protected Vector doStep(Vector xk, UnivariateVectorFunction r, double t) {
        return P.multiply(xk).add(Q.multiply(r.valueAt(t + T)));
    }

    @Override
    public String getName() {
        return "Backward euler method";
    }
}
