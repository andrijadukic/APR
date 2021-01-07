package apr.integration.algorithms;

import apr.functions.UnivariateVectorFunction;
import apr.linear.vector.Vector;

public interface ExplicitLinearSystemIntegrator extends LinearSystemIntegrator {

    Vector predict(Vector xk, UnivariateVectorFunction r, double t);
}
