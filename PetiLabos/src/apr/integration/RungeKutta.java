package apr.integration;

import apr.linear.vector.Vector;

public class RungeKutta extends AbstractLinearSystemIntegrator {

    public RungeKutta(double period, double maximum) {
        super(period, maximum);
    }

    @Override
    protected Vector doStep(Vector prev, double t) {
        return null;
    }
}
