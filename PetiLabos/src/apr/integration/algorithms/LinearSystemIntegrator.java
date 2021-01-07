package apr.integration.algorithms;

import apr.functions.UnivariateVectorFunction;
import apr.linear.matrix.Matrix;
import apr.linear.vector.Vector;

public interface LinearSystemIntegrator {

    void initialize(Matrix A, Matrix B, double T);

    Vector[] solve(Matrix A, Matrix B, UnivariateVectorFunction r, double T, double max, Vector x0);

    Vector next(Vector xk, Vector r);
}
