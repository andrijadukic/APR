package apr.integration.algorithms;

import apr.functions.UnivariateVectorFunction;
import apr.linear.vector.Vector;

public abstract class AbstractExplicitLinearSystemIntegrator extends AbstractLinearSystemIntegrator implements ExplicitLinearSystemIntegrator {

    @Override
    public Vector predict(Vector xk, UnivariateVectorFunction r, double t) {
        return next(xk, r, t);
    }
}
