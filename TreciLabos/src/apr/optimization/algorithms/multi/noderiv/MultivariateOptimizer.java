package apr.optimization.algorithms.multi.noderiv;

import apr.linear.vector.Vector;
import apr.optimization.algorithms.util.NamedAlgorithm;

/**
 * Represents a multivariable function minimization algorithm
 */
public interface MultivariateOptimizer extends NamedAlgorithm {

    /**
     * Computes the minimum of a function from a starting point
     *
     * @param x0 starting point
     * @return argmin
     */
    Vector search(Vector x0);
}
