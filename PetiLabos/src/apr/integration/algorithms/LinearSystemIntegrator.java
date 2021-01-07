package apr.integration.algorithms;

import apr.functions.UnivariateVectorFunction;
import apr.linear.matrix.Matrix;
import apr.linear.vector.Vector;

import java.util.List;

public interface LinearSystemIntegrator {

    void initialize(Matrix A, Matrix B, double T);

    List<Vector> solve(Matrix A, Matrix B, UnivariateVectorFunction r, double T, double max, Vector x0);

    Vector next(Vector xk, UnivariateVectorFunction r, double t);
}
