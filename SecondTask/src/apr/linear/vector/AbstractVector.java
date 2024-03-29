package apr.linear.vector;

import apr.linear.matrix.IMatrix;
import apr.linear.util.linalg.LinearAlgebra;
import apr.linear.util.linalg.OperationMutability;
import apr.linear.util.functions.IDoubleUnaryFunction;

import java.util.Iterator;
import java.util.function.DoublePredicate;

/**
 * Abstract Vector class which implements vector operations, independent of the underlying data structure
 */
public abstract class AbstractVector implements IVector {

    @Override
    public IVector add(IVector other) {
        return LinearAlgebra.add(this, other, OperationMutability.IMMUTABLE);
    }

    @Override
    public IVector add(double value) {
        return LinearAlgebra.add(this, value, OperationMutability.IMMUTABLE);
    }

    @Override
    public IVector subtract(IVector other) {
        return LinearAlgebra.subtract(this, other, OperationMutability.IMMUTABLE);
    }

    @Override
    public IVector subtract(double value) {
        return LinearAlgebra.subtract(this, value, OperationMutability.IMMUTABLE);
    }

    @Override
    public IVector multiply(IMatrix matrix) {
        return LinearAlgebra.multiply(this, matrix);
    }

    @Override
    public double multiply(IVector other) {
        return LinearAlgebra.multiply(this, other);
    }

    @Override
    public IVector multiply(double scalar) {
        return LinearAlgebra.multiply(this, scalar, OperationMutability.IMMUTABLE);
    }

    @Override
    public IVector apply(IDoubleUnaryFunction function) {
        return LinearAlgebra.apply(this, function, OperationMutability.IMMUTABLE);
    }

    @Override
    public boolean anyMatch(DoublePredicate predicate) {
        return LinearAlgebra.anyMatch(this, predicate);
    }

    @Override
    public boolean allMatch(DoublePredicate predicate) {
        return LinearAlgebra.allMatch(this, predicate);
    }

    @Override
    public Iterator<Double> iterator() {
        return new AbstractVectorIterator(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof IVector other)) return false;

        int dimension = getDimension();

        if (dimension != other.getDimension()) return false;

        for (int i = 0; i < dimension; i++) {
            if (get(i) != other.get(i)) return false;
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder vector = new StringBuilder();

        int n = getDimension() - 1;
        for (int i = 0; i < n; i++) {
            vector.append(get(i)).append(" ");
        }
        vector.append(get(n));

        return vector.toString();
    }

    private static class AbstractVectorIterator implements Iterator<Double> {

        private final IVector vector;
        private final int dimension;
        private int count;

        public AbstractVectorIterator(IVector vector) {
            this.vector = vector;
            dimension = vector.getDimension();
        }

        @Override
        public boolean hasNext() {
            return count < dimension;
        }

        @Override
        public Double next() {
            return vector.get(count++);
        }
    }
}
