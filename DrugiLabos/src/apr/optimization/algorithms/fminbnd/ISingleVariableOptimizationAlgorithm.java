package apr.optimization.algorithms.fminbnd;

import apr.optimization.util.INamedAlgorithm;

/**
 * Interface defining a single variable function minimization algorithm
 */
public interface ISingleVariableOptimizationAlgorithm extends INamedAlgorithm {

    /**
     * Computes the minimum of function from a starting point
     *
     * @param x0 starting point
     * @return argmin
     */
    double search(double x0);
}
