package apr.integration.algorithms;

import apr.functions.UnivariateVectorFunction;
import apr.integration.util.AbstractLinearSystemIntegrationSubject;
import apr.integration.exceptions.IntegratorNotInitializedException;
import apr.integration.util.StateStatistics;
import apr.linear.matrix.Matrix;
import apr.linear.vector.Vector;
import apr.util.Named;
import apr.util.Sampling;

public abstract class AbstractLinearSystemIntegrator extends AbstractLinearSystemIntegrationSubject implements LinearSystemIntegrator, Named {

    private boolean isInitialized;

    @Override
    public final void initialize(Matrix A, Matrix B, double T) {
        init(A, B, T);
        isInitialized = true;
    }

    protected abstract void init(Matrix A, Matrix B, double T);

    @Override
    public final Vector[] solve(Matrix A, Matrix B, UnivariateVectorFunction r, double T, double max, Vector x0) {
        initialize(A, B, T);

        int n = Math.toIntExact(Math.round(max / T)) + 1;
        Vector[] states = new Vector[n];

        int ind = 0;
        Vector prev = x0.copy();
        for (double t : Sampling.linspace(0., max, n)) {
            notifyObservers(new StateStatistics(ind, t, prev));
            states[ind++] = prev;
            prev = doStep(prev, r.valueAt(t));
        }

        return states;
    }

    @Override
    public final Vector next(Vector xk, Vector r) {
        if (!isInitialized) throw new IntegratorNotInitializedException(getClass());
        return doStep(xk, r);
    }

    protected abstract Vector doStep(Vector xk, Vector r);
}
