package apr.linear.decompose;

import apr.linear.matrix.IMatrix;
import apr.linear.vector.IVector;

/**
 * Interface defining an object used for solving linear equations
 */
public interface LinearEquationSolver {

    /**
     * Solves equation Ax = y
     * @param b vector on the right side of the equation
     * @return solution vector
     */
    IVector solve(IVector b);

    /**
     * Solves equation AX = Y
     * @param b matrix on the right side of the equation
     * @return solution matrix
     */
    IMatrix solve(IMatrix b);

    /**
     * Inverts matrix
     * @return inverted matrix
     */
    IMatrix invert();
}
