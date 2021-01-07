package apr.integration.algorithms;

import apr.functions.UnivariateVectorFunction;
import apr.integration.exceptions.IntegratorNotInitializedException;
import apr.linear.matrix.Matrix;
import apr.linear.vector.Vector;

public final class RungeKutta extends AbstractExplicitLinearSystemIntegrator {

    private Matrix A;
    private Matrix B;
    private double T;

    private boolean isInitialized;

    @Override
    protected void initialize(Matrix A, Matrix B, double T) {
        if (isInitialized) return;

        this.A = A;
        this.B = B;
        this.T = T;

        isInitialized = true;
    }

    @Override
    protected Vector doStep(Vector xk, UnivariateVectorFunction r, double t) {
        if (!isInitialized) throw new IntegratorNotInitializedException(getClass());

        Vector m1 = f(xk, r.valueAt(t));
        Vector m2 = f(xk.add(m1.multiply(T / 2.)), r.valueAt(t + (T / 2.)));
        Vector m3 = f(xk.add(m2.multiply(T / 2.)), r.valueAt(t + (T / 2.)));
        Vector m4 = f(xk.add(m3.multiply(T)), r.valueAt(t + T));

        return xk.add((m1.add(m2.multiply(2)).add(m3.multiply(2)).add(m4)).multiply(T / 6.));
    }

    private Vector f(Vector xk, Vector rt) {
        return A.multiply(xk).add(B.multiply(rt));
    }

    @Override
    public Vector predict(Vector xk, UnivariateVectorFunction r, double t) {
        return doStep(xk, r, t);
    }

    @Override
    public String getName() {
        return "Runge-Kutta";
    }
}
