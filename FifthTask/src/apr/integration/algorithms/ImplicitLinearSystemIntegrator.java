package apr.integration.algorithms;

import apr.functions.UnivariateVectorFunction;
import apr.linear.vector.Vector;

public interface ImplicitLinearSystemIntegrator extends LinearSystemIntegrator {

    Vector correct(Vector xk, Vector prediction, UnivariateVectorFunction r, double t);
}
