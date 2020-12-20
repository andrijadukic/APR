package apr.integration;

import apr.linear.matrix.Matrix;
import apr.linear.vector.Vector;
import apr.util.Sampling;

public abstract class AbstractLinearSystemIntegrator extends BaseLinearSystemIntegrationSubject implements LinearSystemIntegrator {

    private double period;
    private double maximum;

    protected AbstractLinearSystemIntegrator(double period, double maximum) {
        this.period = period;
        this.maximum = maximum;
    }

    public double getPeriod() {
        return period;
    }

    public void setPeriod(double period) {
        this.period = period;
    }

    public double getMaximum() {
        return maximum;
    }

    public void setMaximum(double maximum) {
        this.maximum = maximum;
    }

    @Override
    public Vector[] solve(Vector x0, Matrix A, Matrix B) {
        int card = Math.toIntExact(Math.round(maximum / period)) + 1;
        Vector[] states = new Vector[card];

        int ind = 0;
        Vector prev = x0.copy();
        for (double t : Sampling.linspace(0, maximum, card)) {
            notifyObservers(new StateStatistics(ind, prev));
            states[ind] = doStep(prev, t);
        }

        return states;
    }

    protected abstract Vector doStep(Vector prev, double t);
}
