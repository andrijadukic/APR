package apr.optimization.functions;

/**
 * Represents a single variable function
 */
@FunctionalInterface
public interface IUnivariateFunction {

    /**
     * Calculates value at given point
     *
     * @param x point
     * @return real number in double precision
     */
    double valueAt(double x);
}
