package apr.integration.algorithms;

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
    protected Vector doStep(Vector xk, Vector r) {
        Vector m1 = f(xk, r);
        Vector m2 = f(xk.add(m1.multiply(T / 2)), r.add(T / 2));
        Vector m3 = f(xk.add(m2.multiply(T / 2)), r.add(T / 2));
        Vector m4 = f(xk.add(m3.multiply(T)), r.add(T));

        return xk.add(m1.add(m2.multiply(2)).add(m3.multiply(2).add(m4)).multiply(T / 6));
    }

    private Vector f(Vector xk, Vector r) {
        return A.multiply(xk).add(B.multiply(r));
    }

    @Override
    public String getName() {
        return "Runge-Kutta";
    }
}
