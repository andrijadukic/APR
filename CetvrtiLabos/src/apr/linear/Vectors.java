package apr.linear;

import apr.linear.linalg.LinearAlgebra;
import apr.linear.linalg.Mutability;
import apr.linear.vector.Vector;
import apr.linear.vector.ListVector;
import apr.linear.vector.ArrayVector;

import java.util.List;
import java.util.Objects;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

/**
 * Utility class with factory methods for building vectors and checking properties
 */
public class Vectors {

    /**
     * Builds a new null vector
     *
     * @param dimension dimension of null vector
     * @return new null vector
     */
    public static Vector zeroes(int dimension) {
        return zeroes(() -> new ArrayVector(dimension));
    }

    /**
     * Builds a new null vector
     *
     * @param supplier supplier object used to dynamically create an instance of IVector
     * @return new null vector
     */
    public static Vector zeroes(Supplier<Vector> supplier) {
        return fill(supplier.get(), 0);
    }

    /**
     * Builds a new vector and fills it with ones
     *
     * @param dimension dimension of null vector
     * @return new vector
     */
    public static Vector ones(int dimension) {
        return ones(() -> new ArrayVector(dimension));
    }

    /**
     * Builds a new vector and fills it with ones
     *
     * @param supplier supplier object used to dynamically create an instance of IVector
     * @return new vector
     */
    public static Vector ones(Supplier<Vector> supplier) {
        return fill(supplier.get(), 1);
    }

    /**
     * Fills vector with given value
     *
     * @param vector vector to be filled
     * @param value  value
     * @return filled vector
     */
    public static Vector fill(Vector vector, double value) {
        return LinearAlgebra.apply(vector, x -> value, Mutability.MUTABLE);
    }

    /**
     * Fills vector using given supplier
     *
     * @param vector   vector to be filled
     * @param supplier supplier
     * @return filled vector
     */
    public static Vector fill(Vector vector, DoubleSupplier supplier) {
        return LinearAlgebra.apply(vector, x -> supplier.getAsDouble(), Mutability.MUTABLE);
    }

    /**
     * Creates a vector that serves as view of the given array
     *
     * @param array underlying array
     * @return new vector
     */
    public static Vector asVector(double... array) {
        return new ArrayVector(Objects.requireNonNull(array));
    }

    /**
     * Creates a vector that serves as view of the given list
     *
     * @param list underlying list
     * @return new vector
     */
    public static Vector asVector(List<Double> list) {
        return new ListVector(Objects.requireNonNull(list));
    }
}
