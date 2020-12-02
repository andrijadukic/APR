package apr.linear.matrix;

import apr.linear.util.ICopyable;
import apr.linear.util.IMatchable;
import apr.linear.util.functions.IDoubleUnaryFunction;
import apr.linear.util.linalg.LinearAlgebra;
import apr.linear.util.linalg.OperationMutability;
import apr.linear.vector.IVector;

import java.util.function.DoublePredicate;

/**
 * Represents a matrix that holds real numbers (double precision)
 */
public interface IMatrix extends Iterable<Double>, ICopyable<IMatrix>, IMatchable {

    /**
     * Creates a new matrix of this type
     *
     * @param rows    row dimension of new matrix
     * @param columns column dimension of new matrix
     * @return new matrix of this type
     */
    IMatrix newInstance(int rows, int columns);

    /**
     * Gets row dimension
     *
     * @return row dimension
     */
    int getRowDimension();

    /**
     * Gets row dimension
     *
     * @return row dimension
     */
    int getColumnDimension();

    /**
     * Gets the element at index [i, j]
     *
     * @param i row index
     * @param j column index
     * @return element at index [i, j]
     */
    double get(int i, int j);

    /**
     * Gets the row at index
     *
     * @param index row index
     * @return row at index
     */
    IVector getRow(int index);

    /**
     * Gets the column at index
     *
     * @param index column index
     * @return column at index
     */
    IVector getColumn(int index);

    /**
     * Sets the element at index [i, j]
     *
     * @param i     row index
     * @param j     column index
     * @param value value to store
     * @return this matrix
     */
    IMatrix set(int i, int j, double value);

    /**
     * Swaps rows at index i and j
     *
     * @param i first row
     * @param j second row
     */
    void swapRows(int i, int j);

    /**
     * Swaps columns at index i and j
     *
     * @param i first column
     * @param j second column
     */
    void swapColumns(int i, int j);

    /**
     * Performs matrix addition
     *
     * @param other matrix to be added to this matrix
     * @return new matrix
     */
    default IMatrix add(IMatrix other) {
        return LinearAlgebra.add(this, other, OperationMutability.IMMUTABLE);
    }

    /**
     * Performs scalar addition
     *
     * @param value value to be added to this matrix
     * @return new matrix
     */
    default IMatrix add(double value) {
        return LinearAlgebra.add(this, value, OperationMutability.IMMUTABLE);
    }

    /**
     * Performs matrix subtraction
     *
     * @param other matrix to be subtracted from this matrix
     * @return new matrix
     */
    default IMatrix subtract(IMatrix other) {
        return LinearAlgebra.subtract(this, other, OperationMutability.IMMUTABLE);
    }

    /**
     * Performs scalar subtraction
     *
     * @param value value to be subtracted from this matrix
     * @return new matrix
     */
    default IMatrix subtract(double value) {
        return LinearAlgebra.subtract(this, value, OperationMutability.IMMUTABLE);
    }

    /**
     * Performs matrix-matrix multiplication
     *
     * @param other second operand in matrix multiplication
     * @return new matrix
     */
    default IMatrix multiply(IMatrix other) {
        return LinearAlgebra.multiply(this, other);
    }

    /**
     * Performs matrix-scalar multiplication
     *
     * @param scalar second operand in matrix-scalar multiplication
     * @return new matrix
     */
    default IMatrix multiply(double scalar) {
        return LinearAlgebra.multiply(this, scalar, OperationMutability.IMMUTABLE);
    }

    /**
     * Transposes this matrix
     *
     * @return transposed matrix
     */
    IMatrix transpose();

    /**
     * Applies function to all elements of matrix
     *
     * @param function function to be applied
     * @return new matrix
     */
    default IMatrix apply(IDoubleUnaryFunction function) {
        return LinearAlgebra.apply(this, function, OperationMutability.IMMUTABLE);
    }

    /**
     * Turns this matrix into vector array by columns
     *
     * @return column vector array
     */
    IVector[] toColumnVectors();

    /**
     * Turns this matrix into vector array by rows
     *
     * @return row vector array
     */
    IVector[] toRowVectors();

    /**
     * Gets this matrix in array form
     *
     * @return array representation of this matrix
     */
    double[][] toArray();
}
