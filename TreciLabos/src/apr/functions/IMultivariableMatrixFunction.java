package apr.functions;

import apr.linear.matrix.IMatrix;
import apr.linear.vector.IVector;

/**
 * Represents a multivariate function which takes an n-dimensional vector as argument and returns a KxL dimensional matrix
 */
@FunctionalInterface
public interface IMultivariableMatrixFunction {

    /**
     * Calculates matrix at given point
     *
     * @param x point
     * @return matrix
     */
    IMatrix valueAt(IVector x);
}
