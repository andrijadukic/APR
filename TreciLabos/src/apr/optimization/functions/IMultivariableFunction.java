package apr.optimization.functions;

import apr.linear.vector.IVector;

/**
 * Represents a multivariable function
 */
@FunctionalInterface
public interface IMultivariableFunction {

    /**
     * Calculates value at given point of this function
     *
     * @param vector point
     * @return real number in double precision
     */
    double valueAt(IVector vector);
}
