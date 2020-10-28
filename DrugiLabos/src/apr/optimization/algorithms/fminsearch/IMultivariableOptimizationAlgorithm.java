package apr.optimization.algorithms.fminsearch;

import apr.linear.vector.IVector;

/**
 * Interface defining a multivariable function minimization algorithm
 */
public interface IMultivariableOptimizationAlgorithm {

    /**
     * Computes the minimum of function from a starting point
     *
     * @param x0 starting point
     * @return argmin
     */
    IVector search(IVector x0);

    /**
     * Gets the name of this algorithm
     *
     * @return name of algorithm
     */
    String getName();
}
