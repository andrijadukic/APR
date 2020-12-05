package apr.linear.vector;

import apr.linear.matrix.Matrix;
import apr.linear.matrix.ArrayMatrix;
import apr.util.Copyable;
import apr.util.Matchable;

import java.util.function.DoubleUnaryOperator;

import apr.linear.util.linalg.LinearAlgebra;
import apr.linear.util.linalg.OperationMutability;


/**
 * Interface defining a vector that holds real numbers (double precision)
 */
public interface Vector extends Iterable<Double>, Copyable<Vector>, Matchable {

    /**
     * Creates a new vector of this type
     *
     * @param dimension dimension of new vector
     * @return new vector
     */
    Vector newInstance(int dimension);

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
    Vector set(int i, double value);

    /**
     * Swaps the elements at indices i and j
     *
     * @param i first index
     * @param j second index
     */
    default void swap(int i, int j) {
        var temp = get(i);
        set(i, get(j));
        set(j, temp);
    }

    /**
     * Performs vector addition
     *
     * @param other vector to be added to this vector
     * @return new vector
     */
    default Vector add(Vector other) {
        return LinearAlgebra.add(this, other, OperationMutability.IMMUTABLE);
    }

    /**
     * Performs scalar addition
     *
     * @param value value to be added to this vector
     * @return new vector
     */
    default Vector add(double value) {
        return LinearAlgebra.add(this, value, OperationMutability.IMMUTABLE);
    }

    /**
     * Performs vector subtraction
     *
     * @param other vector to be subtracted from this vector
     * @return new vector
     */
    default Vector subtract(Vector other) {
        return LinearAlgebra.subtract(this, other, OperationMutability.IMMUTABLE);
    }

    /**
     * Performs scalar subtraction
     *
     * @param value value to be subtracted from this vector
     * @return new vector
     */
    default Vector subtract(double value) {
        return LinearAlgebra.subtract(this, value, OperationMutability.IMMUTABLE);
    }

    /**
     * Performs vector-matrix multiplication
     *
     * @param matrix second operand in vector-matrix multiplication
     * @return new vector
     */
    default Vector multiply(Matrix matrix) {
        return LinearAlgebra.multiply(this, matrix);
    }

    /**
     * Performs vector-scalar multiplication
     *
     * @param scalar second operand in matrix-scalar multiplication
     * @return new vector
     */
    default Vector multiply(double scalar) {
        return LinearAlgebra.multiply(this, scalar, OperationMutability.IMMUTABLE);
    }

    /**
     * Performs inner vector multiplication
     *
     * @param other second operand in vector-vector multiplication
     * @return new vector
     */
    default double inner(Vector other) {
        return LinearAlgebra.inner(this, other);
    }

    /**
     * Performs outer vector multiplication
     *
     * @param other second operand in vector-vector multiplication
     * @return new vector
     */
    default Matrix outer(Vector other) {
        return LinearAlgebra.outer(this, other);
    }

    /**
     * Applies function to all elements of vector
     *
     * @param function function to be applied
     * @return new vector
     */
    default Vector apply(DoubleUnaryOperator function) {
        return LinearAlgebra.apply(this, function, OperationMutability.IMMUTABLE);
    }

    /**
     * Transforms this vector to matrix equivalent
     *
     * @return equivalent matrix
     */
    default Matrix asMatrix() {
        int dimension = getDimension();
        Matrix result = new ArrayMatrix(1, dimension);
        for (int i = 0; i < dimension; i++) {
            result.set(0, i, get(i));
        }
        return result;
    }
}
