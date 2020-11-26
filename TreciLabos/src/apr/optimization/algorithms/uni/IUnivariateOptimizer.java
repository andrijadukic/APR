package apr.optimization.algorithms.uni;

import apr.optimization.algorithms.util.INamedAlgorithm;

/**
 * Represents a single variable function minimization algorithm
 */
public interface IUnivariateOptimizer extends INamedAlgorithm {

    /**
     * Computes the minimum of a function from a starting point
     *
     * @param x0 starting point
     * @return argmin
     */
    double search(double x0);
}
