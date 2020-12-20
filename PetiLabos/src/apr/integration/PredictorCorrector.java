package apr.integration;

import apr.linear.vector.Vector;

public class PredictorCorrector extends AbstractLinearSystemIntegrator {

    public PredictorCorrector(double period, double maximum) {
        super(period, maximum);
    }

    @Override
    protected Vector doStep(Vector prev, double t) {
        return null;
    }
}
