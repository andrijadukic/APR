package apr.optimization.function;

/**
 * Interface defining a single variable function
 */
public interface ISingleVariableFunction {

    /**
     * Calculates value at given point of this function
     *
     * @param x point
     * @return real number in double precision
     */
    double valueAt(double x);
}
