package apr.linear.util.functions;

/**
 * Represents a binary function that takes two real numbers (double precision) and returns a real number (double precision)
 */
@FunctionalInterface
public interface IDoubleBinaryFunction {

    /**
     * Applies this function to operands
     *
     * @param x first operand
     * @param y second operand
     * @return result of applying this function to operands
     */
    double apply(double x, double y);
}
