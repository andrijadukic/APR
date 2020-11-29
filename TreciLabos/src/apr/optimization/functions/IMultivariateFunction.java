package apr.optimization.functions;

import apr.linear.vector.IVector;

/**
 * Represents a multivariable function
 */
@FunctionalInterface
public interface IMultivariateFunction {

    /**
     * Calculates value at given point
     *
     * @param x point
     * @return real number in double precision
     */
    double valueAt(IVector x);

    /**
     * Creates new function which returns negative value of the original (f2(x) = -f1(x))
     * @return new function
     */
    default IMultivariateFunction negate() {
        return x -> -valueAt(x);
    }
}
