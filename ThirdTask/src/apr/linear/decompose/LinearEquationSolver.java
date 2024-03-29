package apr.linear.decompose;

import apr.linear.matrix.Matrix;
import apr.linear.vector.Vector;

/**
 * Represents an object used for solving linear equations
 */
public interface LinearEquationSolver {

    /**
     * Solves equation Ax = y
     *
     * @param b vector on the right side of the equation
     * @return solution vector
     */
    Vector solve(Vector b);

    /**
     * Inverts matrix
     *
     * @return inverted matrix
     */
    Matrix invert();
}
