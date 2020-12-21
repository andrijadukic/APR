package apr.integration.algorithms;

import apr.integration.util.AbstractLinearSystemIntegrationSubject;
import apr.integration.exceptions.IntegratorNotInitializedException;
import apr.integration.util.LinearSystemIntegrator;
import apr.integration.util.StateStatistics;
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

        int sampleSize = Math.toIntExact(Math.round(tMax / T)) + 1;
        Vector[] states = new Vector[sampleSize];

        int ind = 0;
        Vector prev = x0.copy();
        for (double t : Sampling.linspace(0., tMax, sampleSize)) {
            notifyObservers(new StateStatistics(ind, t, prev));
            states[ind++] = prev;
            prev = doStep(prev);
        }

        return states;
    }

    @Override
    public final Vector next(Vector xk) {
        if (!isInitialized) throw new IntegratorNotInitializedException(getClass());
        return doStep(xk);
    }

    protected abstract Vector doStep(Vector xk);
}
