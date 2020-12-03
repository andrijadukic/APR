package apr.linear.util;

import apr.linear.util.suppliers.IVectorSupplier;
import apr.linear.util.linalg.LinearAlgebra;
import apr.linear.util.linalg.OperationMutability;
import apr.linear.vector.IVector;
import apr.linear.vector.ListViewVector;
import apr.linear.vector.Vector;

import java.util.List;
import java.util.Objects;
import java.util.function.DoubleSupplier;

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
    public static IVector zeroes(int dimension) {
        return zeroes(() -> new Vector(dimension));
    }

    /**
     * Builds a new null vector
     *
     * @param supplier supplier object used to dynamically create an instance of IVector
     * @return new null vector
     */
    public static IVector zeroes(IVectorSupplier supplier) {
        return fill(supplier.getAsVector(), 0);
    }

    /**
     * Builds a new vector and fills it with ones
     *
     * @param dimension dimension of null vector
     * @return new vector
     */
    public static IVector ones(int dimension) {
        return ones(() -> new Vector(dimension));
    }

    /**
     * Builds a new vector and fills it with ones
     *
     * @param supplier supplier object used to dynamically create an instance of IVector
     * @return new vector
     */
    public static IVector ones(IVectorSupplier supplier) {
        return fill(supplier.getAsVector(), 1);
    }

    /**
     * Fills vector with given value
     *
     * @param vector vector to be filled
     * @param value  value
     * @return filled vector
     */
    public static IVector fill(IVector vector, double value) {
        return LinearAlgebra.apply(vector, x -> value, OperationMutability.MUTABLE);
    }

    /**
     * Fills vector using given supplier
     *
     * @param vector   vector to be filled
     * @param supplier supplier
     * @return filled vector
     */
    public static IVector fill(IVector vector, DoubleSupplier supplier) {
        return LinearAlgebra.apply(vector, x -> supplier.getAsDouble(), OperationMutability.MUTABLE);
    }

    /**
     * Creates a vector that serves as view of the given array
     *
     * @param array underlying array
     * @return new vector
     */
    public static IVector asVector(double... array) {
        return new Vector(Objects.requireNonNull(array));
    }

    /**
     * Creates a vector that serves as view of the given list
     *
     * @param list underlying list
     * @return new vector
     */
    public static IVector asVector(List<Double> list) {
        return new ListViewVector(Objects.requireNonNull(list));
    }
}
