package apr.functions;

import apr.linear.matrix.IMatrix;
import apr.linear.vector.AbstractVector;
import apr.linear.vector.IVector;
import apr.linear.vector.ListViewVector;
import apr.linear.vector.Vector;

import java.util.List;

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
     * Calculates value at given point by wrapping given array into vector and calling the standard method
     *
     * @param x point
     * @return real number in double precision
     */
    default double valueAt(double... x) {
        return valueAt(new Vector(x));
    }

    /**
     * Calculates value at given point by wrapping given list into vector and calling the standard method
     *
     * @param x point
     * @return real number in double precision
     */
    default double valueAt(List<Double> x) {
        return valueAt(new ListViewVector(x));
    }

    /**
     * Creates new function which returns negative value of the original (f2(x) = -f1(x))
     *
     * @return new function
     */
    default IMultivariateFunction negate() {
        return x -> -valueAt(x);
    }
}
