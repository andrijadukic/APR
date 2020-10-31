package apr.linear.util.functions;

/**
 * Represents a unary function that a real number (double precision) and returns a real number (double precision)
 */
@FunctionalInterface
public interface IDoubleUnaryFunction {

    /**
     * Applies this function to operand
     *
     * @param x operand
     * @return result of applying this function to operand
     */
    double apply(double x);
}
