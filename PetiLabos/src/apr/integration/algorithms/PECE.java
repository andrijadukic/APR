package apr.integration.algorithms;

import apr.integration.util.LinearSystemIntegrator;
import apr.linear.matrix.Matrix;
import apr.linear.vector.Vector;

public final class PECE extends AbstractLinearSystemIntegrator {

    private final LinearSystemIntegrator predictor;
    private final LinearSystemIntegrator corrector;

    public PECE(LinearSystemIntegrator predictor, LinearSystemIntegrator corrector) {
        this.predictor = predictor;
        this.corrector = corrector;
    }

    @Override
    protected void init(Matrix A, Vector B, double T) {
        predictor.initialize(A, B, T);
        corrector.initialize(A, B, T);
    }

    @Override
    protected Vector doStep(Vector xk) {
        return corrector.next(predictor.next(xk));
    }

    @Override
    public String getName() {
        return "PECE";
    }
}
