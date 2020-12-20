package apr.integration;

import apr.linear.vector.Vector;

public class Trapezoidal extends AbstractLinearSystemIntegrator {

    public Trapezoidal(double period, double maximum) {
        super(period, maximum);
    }

    @Override
    protected Vector doStep(Vector prev, double t) {
        return null;
    }
}
