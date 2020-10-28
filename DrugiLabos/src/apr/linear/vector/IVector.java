package apr.linear.vector;

import apr.linear.matrix.IMatrix;
import apr.linear.util.functions.IDoubleUnaryFunction;

import java.util.function.DoublePredicate;

/**
 * Interface defining a vector that holds real numbers (double precision)
 */
public interface IVector {

    /**
     * Copies this vector
     *
     * @return deep copy of this vector
     */
    IVector copy();

    /**
     * Creates a new vector of this type
     *
     * @param dimension dimension of new vector
     * @return new vector
     */
    IVector newInstance(int dimension);

    /**
     * Gets the dimension of this vector
     *
     * @return dimension of this vector
     */
    int getDimension();

    /**
     * Gets the element at index i
     *
     * @param i index of the target element
     * @return element at index i
     */
    double get(int i);

    /**
     * Sets the element at index i
     *
     * @param i     index of the target element
     * @param value value to store
     * @return this vector
     */
    IVector set(int i, double value);

    /**
     * Swaps the elements at indices i and j
     *
     * @param i first index
     * @param j second index
     */
    void swap(int i, int j);

    /**
     * Performs vector addition
     *
     * @param other vector to be added to this vector
     * @return new vector
     */
    IVector add(IVector other);

    /**
     * Performs scalar addition
     *
     * @param value value to be added to this vector
     * @return new vector
     */
    IVector add(double value);

    /**
     * Performs vector subtraction
     *
     * @param other vector to be subtracted from this vector
     * @return new vector
     */
    IVector subtract(IVector other);

    /**
     * Performs scalar subtraction
     *
     * @param value value to be subtracted from this vector
     * @return new vector
     */
    IVector subtract(double value);

    /**
     * Performs vector-matrix multiplication
     *
     * @param matrix second operand in vector-matrix multiplication
     * @return new vector
     */
    IVector multiply(IMatrix matrix);

    /**
     * Performs vector-vector multiplication
     *
     * @param other second operand in vector-vector multiplication
     * @return new vector
     */
    double multiply(IVector other);

    /**
     * Performs vector-scalar multiplication
     *
     * @param scalar second operand in matrix-scalar multiplication
     * @return new vector
     */
    IVector multiply(double scalar);

    /**
     * Applies function to all elements of vector
     *
     * @param function function to be applied
     * @return new vector
     */
    IVector apply(IDoubleUnaryFunction function);

    /**
     * Tests if any elements of IVector match predicate
     *
     * @param predicate predicate to be tested
     * @return true if any element matches, false otherwise
     */
    boolean anyMatch(DoublePredicate predicate);


    /**
     * Tests if all elements of IVector match predicate
     *
     * @param predicate predicate to be tested
     * @return true if all elements match, false otherwise
     */
    boolean allMatch(DoublePredicate predicate);

    /**
     * Transforms this vector to matrix equivalent
     *
     * @return equivalent matrix
     */
    IMatrix toMatrix();
}
