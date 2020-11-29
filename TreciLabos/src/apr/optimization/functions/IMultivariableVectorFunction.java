package apr.optimization.functions;

import apr.linear.vector.IVector;

/**
 * Represents a multivariate function which takes an n-dimensional vector as argument and returns an m-dimensional vector
 */
@FunctionalInterface
public interface IMultivariableVectorFunction {

    /**
     * Calculates vector at given point
     *
     * @param x point
     * @return vector
     */
    IVector valueAt(IVector x);
}
