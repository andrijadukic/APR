package apr.integration.algorithms;

import apr.functions.UnivariateVectorFunction;
import apr.linear.decompose.LUPDecomposer;
import apr.linear.matrix.Matrices;
import apr.linear.matrix.Matrix;
import apr.linear.vector.Vector;

public final class Trapezoidal extends AbstractImplicitLinearSystemIntegrator {

    private Matrix A;
    private Matrix B;
    private Matrix R;
    private Matrix S;
    private double T;

    @Override
    protected void init(Matrix A, Matrix B, double T) {
        this.A = A;
        this.B = B;

        Matrix identity = Matrices.identity(A.getRowDimension());
        Matrix halvedA = A.multiply(T / 2.);
        Matrix inverse = new LUPDecomposer(identity.subtract(halvedA)).solver().invert();
        R = inverse.multiply(identity.add(halvedA));
        S = inverse.multiply(T / 2.).multiply(B);

        this.T = T;
    }

    @Override
    protected Vector doStep(Vector xk, UnivariateVectorFunction r, double t) {
        return R.multiply(xk).add(S.multiply(r.valueAt(t).add(r.valueAt(t + T))));
    }

    @Override
    public Vector correct(Vector xk, Vector prediction, UnivariateVectorFunction r, double t) {
        return xk.add(A.multiply(xk.add(prediction).add(B.multiply(r.valueAt(t).add(r.valueAt(t + T))))).multiply(T / 2.));
    }

    @Override
    public String getName() {
        return "Trapezoidal";
    }
}
