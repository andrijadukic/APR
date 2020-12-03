package apr.linear.util.operators;

/**
 * Represents a unary operator that accepts a real number (double precision) and returns a real number (double precision)
 */
@FunctionalInterface
public interface IDoubleUnaryOperator {

    /**
     * Applies this operator to operand
     *
     * @param x operand
     * @return result of applying this operator to operand
     */
    double apply(double x);
}
