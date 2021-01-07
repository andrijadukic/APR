package apr.integration.algorithms;

import apr.functions.UnivariateVectorFunction;
import apr.linear.matrix.Matrix;
import apr.linear.vector.Vector;

public final class RungeKutta extends AbstractLinearSystemIntegrator {

    private Matrix A;
    private Matrix B;
    private double T;

    @Override
    protected void init(Matrix A, Matrix B, double T) {
        this.A = A;
        this.B = B;
        this.T = T;
    }

    @Override
    protected Vector doStep(Vector xk, UnivariateVectorFunction r, double t) {
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
    public String getName() {
        return "Runge-Kutta";
    }
}
