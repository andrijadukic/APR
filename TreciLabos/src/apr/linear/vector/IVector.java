package apr.linear.vector;

import apr.linear.matrix.IMatrix;
import apr.linear.util.ICopyable;
import apr.linear.util.IMatchable;
import apr.linear.util.functions.IDoubleUnaryFunction;
import apr.linear.util.linalg.LinearAlgebra;
import apr.linear.util.linalg.OperationMutability;


/**
 * Interface defining a vector that holds real numbers (double precision)
 */
public interface IVector extends Iterable<Double>, ICopyable<IVector>, IMatchable {

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
    default IVector add(IVector other) {
        return LinearAlgebra.add(this, other, OperationMutability.IMMUTABLE);
    }

    /**
     * Performs scalar addition
     *
     * @param value value to be added to this vector
     * @return new vector
     */
    default IVector add(double value) {
        return LinearAlgebra.add(this, value, OperationMutability.IMMUTABLE);
    }

    /**
     * Performs vector subtraction
     *
     * @param other vector to be subtracted from this vector
     * @return new vector
     */
    default IVector subtract(IVector other) {
        return LinearAlgebra.subtract(this, other, OperationMutability.IMMUTABLE);
    }

    /**
     * Performs scalar subtraction
     *
     * @param value value to be subtracted from this vector
     * @return new vector
     */
    default IVector subtract(double value) {
        return LinearAlgebra.subtract(this, value, OperationMutability.IMMUTABLE);
    }

    /**
     * Performs vector-matrix multiplication
     *
     * @param matrix second operand in vector-matrix multiplication
     * @return new vector
     */
    default IVector multiply(IMatrix matrix) {
        return LinearAlgebra.multiply(this, matrix);
    }

    /**
     * Performs inner vector multiplication
     *
     * @param other second operand in vector-vector multiplication
     * @return new vector
     */
    default double inner(IVector other) {
        return LinearAlgebra.inner(this, other);
    }

    /**
     * Performs outer vector multiplication
     *
     * @param other second operand in vector-vector multiplication
     * @return new vector
     */
    default IMatrix outer(IVector other) {
        return LinearAlgebra.outer(this, other);
    }

    /**
     * Performs vector-scalar multiplication
     *
     * @param scalar second operand in matrix-scalar multiplication
     * @return new vector
     */
    default IVector multiply(double scalar) {
        return LinearAlgebra.multiply(this, scalar, OperationMutability.IMMUTABLE);
    }

    /**
     * Applies function to all elements of vector
     *
     * @param function function to be applied
     * @return new vector
     */
    default IVector apply(IDoubleUnaryFunction function) {
        return LinearAlgebra.apply(this, function, OperationMutability.IMMUTABLE);
    }

    /**
     * Transforms this vector to matrix equivalent
     *
     * @return equivalent matrix
     */
    IMatrix toMatrix();
}
