package apr.integration;

import apr.linear.matrix.Matrix;
import apr.linear.vector.Vector;

public interface LinearSystemIntegrator {

    void initialize(Matrix A, Vector B, double T);

    Vector[] solve(Vector x0, Matrix A, Vector B, double T, double tMax);

    Vector doStep(Vector xk);
}
