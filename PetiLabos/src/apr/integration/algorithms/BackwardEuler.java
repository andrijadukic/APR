package apr.integration.algorithms;

import apr.functions.UnivariateVectorFunction;
import apr.linear.decompose.LUPDecomposer;
import apr.linear.matrix.Matrices;
import apr.linear.matrix.Matrix;
import apr.linear.vector.Vector;

public final class BackwardEuler extends AbstractImplicitLinearSystemIntegrator {

    private Matrix A;
    private Matrix B;
    private Matrix P;
    private Matrix Q;
    private double T;

    @Override
    protected void init(Matrix A, Matrix B, double T) {
        this.A = A;
        this.B = B;
        P = new LUPDecomposer(Matrices.identity(A.getRowDimension()).subtract(A.multiply(T))).solver().invert();
        Q = P.multiply(T).multiply(B);
        this.T = T;
    }

    @Override
    protected Vector doStep(Vector xk, UnivariateVectorFunction r, double t) {
        return P.multiply(xk).add(Q.multiply(r.valueAt(t + T)));
    }

    @Override
    public Vector correct(Vector xk, Vector prediction, UnivariateVectorFunction r, double t) {
        return xk.add(A.multiply(prediction).add(B.multiply(r.valueAt(t + T))).multiply(T));
    }

    @Override
    public String getName() {
        return "Backward euler method";
    }
}
