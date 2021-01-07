package apr.integration.algorithms;

import apr.functions.UnivariateVectorFunction;
import apr.linear.matrix.Matrix;
import apr.linear.vector.Vector;

public final class PECE extends AbstractLinearSystemIntegrator {

    private final LinearSystemIntegrator predictor;
    private final LinearSystemIntegrator corrector;
    private final int n;

    public PECE(LinearSystemIntegrator predictor, LinearSystemIntegrator corrector, int n) {
        this.predictor = predictor;
        this.corrector = corrector;
        this.n = n;
    }

    @Override
    protected void init(Matrix A, Matrix B, double T) {
        predictor.initialize(A, B, T);
        corrector.initialize(A, B, T);
    }

    @Override
    protected Vector doStep(Vector xk, UnivariateVectorFunction r, double t) {
        Vector prediction = predictor.next(xk, r, t);
        int leftover = n;
        while (leftover != 0) {
            prediction = corrector.next(prediction, r, t);
            leftover--;
        }
        return prediction;
    }

    @Override
    public String getName() {
        return "PE(CE)^" + n;
    }
}
