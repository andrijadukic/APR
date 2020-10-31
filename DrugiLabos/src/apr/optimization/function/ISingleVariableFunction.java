package apr.optimization.function;

/**
 * Represents a single variable function
 */
@FunctionalInterface
public interface ISingleVariableFunction {

    /**
     * Calculates value at given point of this function
     *
     * @param x point
     * @return real number in double precision
     */
    double valueAt(double x);
}
