package apr.optimization.algorithms.fminsearch;

import apr.linear.vector.IVector;
import apr.optimization.util.INamedAlgorithm;

/**
 * Represents a multivariable function minimization algorithm
 */
public interface IMultivariableOptimizationAlgorithm extends INamedAlgorithm {

    /**
     * Computes the minimum of a function from a starting point
     *
     * @param x0 starting point
     * @return argmin
     */
    IVector search(IVector x0);
}
