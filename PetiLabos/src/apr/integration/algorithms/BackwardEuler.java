package apr.integration.algorithms;

import apr.functions.UnivariateVectorFunction;
import apr.integration.exceptions.IntegratorNotInitializedException;
import apr.linear.decompose.LUPDecomposer;
import apr.linear.matrix.Matrices;
import apr.linear.matrix.Matrix;
import apr.linear.vector.Vector;

public final class BackwardEuler extends AbstractImplicitLinearSystemIntegrator {

    private Matrix A;
    private Matrix B;
    private double T;

    private Matrix P;
    private Matrix Q;

    private boolean isInitialized;

    @Override
    protected void initialize(Matrix A, Matrix B, double T) {
        if (isInitialized) return;

        this.A = A;
        this.B = B;
        this.T = T;

        P = new LUPDecomposer(Matrices.identity(A.getRowDimension()).subtract(A.multiply(T))).solver().invert();
        Q = P.multiply(T).multiply(B);

        isInitialized = true;
    }

    @Override
    protected Vector next(Vector xk, UnivariateVectorFunction r, double t) {
        if (!isInitialized) throw new IntegratorNotInitializedException(getClass());
        return P.multiply(xk).add(Q.multiply(r.valueAt(t + T)));
    }

    @Override
    public Vector correct(Vector xk, Vector prediction, UnivariateVectorFunction r, double t) {
        if (!isInitialized) throw new IntegratorNotInitializedException(getClass());
        return xk.add(A.multiply(prediction).add(B.multiply(r.valueAt(t + T))).multiply(T));
    }

    @Override
    public String getName() {
        return "Backward euler method";
    }
}
