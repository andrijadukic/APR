package apr.optimization.function;

import apr.linear.vector.IVector;

/**
 * Interface defining a multivariable function
 */
public interface IFunction {

    /**
     * Calculates value at given point of this function
     *
     * @param vector point
     * @return real number in double precision
     */
    double valueAt(IVector vector);
}
