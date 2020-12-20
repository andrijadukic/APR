package apr.integration;

import apr.linear.vector.Vector;

public class ReverseEulerMethod extends AbstractLinearSystemIntegrator {

    public ReverseEulerMethod(double period, double maximum) {
        super(period, maximum);
    }

    @Override
    protected Vector doStep(Vector prev, double t) {
        return null;
    }
}
