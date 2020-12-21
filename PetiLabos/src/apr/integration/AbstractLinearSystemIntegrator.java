package apr.integration;

import apr.linear.matrix.Matrix;
import apr.linear.vector.Vector;
import apr.util.Sampling;

public abstract class AbstractLinearSystemIntegrator extends AbstractLinearSystemIntegrationSubject implements LinearSystemIntegrator {

    private boolean isInitialized;

    @Override
    public final void initialize(Matrix A, Vector B, double T) {
        init(A, B, T);
        isInitialized = true;
    }

    protected abstract void init(Matrix A, Vector B, double T);

    @Override
    public final Vector[] solve(Vector x0, Matrix A, Vector B, double T, double tMax) {
        initialize(A, B, T);

        int card = Math.toIntExact(Math.round(tMax / T)) + 1;
        Vector[] states = new Vector[card];

        int ind = 0;
        Vector prev = x0.copy();
        for (double t : Sampling.linspace(0., tMax, card)) {
            notifyObservers(new StateStatistics(ind, t, prev));
            Vector next = doStep(prev);
            states[ind] = next;
            prev = next;
        }

        return states;
    }

    @Override
    public final Vector doStep(Vector xk) {
        if (!isInitialized) throw new IntegratorNotInitializedException(getClass());
        return next(xk);
    }

    protected abstract Vector next(Vector xk);
}
