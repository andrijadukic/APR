package apr.integration;

import apr.linear.matrix.Matrix;
import apr.linear.vector.Vector;

public interface LinearSystemIntegrator {

    Vector[] solve(Vector x0, Matrix A, Matrix B);
}
