package apr.linear.util.operators;

/**
 * Represents a binary operator that accepts two real numbers (double precision) and returns a real number (double precision)
 */
@FunctionalInterface
public interface IDoubleBinaryOperator {

    /**
     * Applies this operator to operands
     *
     * @param x first operand
     * @param y second operand
     * @return result of applying this operator to operands
     */
    double apply(double x, double y);
}
