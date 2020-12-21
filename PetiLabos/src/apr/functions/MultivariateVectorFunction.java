package apr.functions;

import apr.linear.vector.Vector;

/**
 * Represents a multivariate function which takes an n-dimensional vector as argument and returns an m-dimensional vector
 */
@FunctionalInterface
public interface MultivariateVectorFunction {

    /**
     * Calculates vector at given point
     *
     * @param x point
     * @return vector
     */
    Vector valueAt(Vector x);
}
