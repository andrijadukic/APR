package apr.linear.matrix;

import apr.linear.util.linalg.LinearAlgebra;
import apr.linear.util.linalg.OperationMutability;
import apr.linear.util.functions.IDoubleUnaryFunction;

import java.util.Iterator;
import java.util.function.DoublePredicate;

/**
 * Abstract Matrix class which implements matrix operations, independent of the underlying data structure
 */
public abstract class AbstractMatrix implements IMatrix {

    @Override
    public IMatrix add(IMatrix other) {
        return LinearAlgebra.add(this, other, OperationMutability.IMMUTABLE);
    }

    @Override
    public IMatrix add(double value) {
        return LinearAlgebra.add(this, value, OperationMutability.IMMUTABLE);
    }

    @Override
    public IMatrix subtract(IMatrix other) {
        return LinearAlgebra.subtract(this, other, OperationMutability.IMMUTABLE);
    }

    @Override
    public IMatrix subtract(double value) {
        return LinearAlgebra.subtract(this, value, OperationMutability.IMMUTABLE);
    }

    @Override
    public IMatrix multiply(IMatrix other) {
        return LinearAlgebra.multiply(this, other);
    }

    @Override
    public IMatrix multiply(double scalar) {
        return LinearAlgebra.multiply(this, scalar, OperationMutability.IMMUTABLE);
    }

    @Override
    public IMatrix transpose() {
        return new TransposedMatrixView(this);
    }

    @Override
    public Iterator<Double> iterator() {
        return new AbstractMatrixIterator(this);
    }

    @Override
    public IMatrix apply(IDoubleUnaryFunction function) {
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
    public boolean equals(Object obj) {
        if (!(obj instanceof IMatrix other)) return false;

        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();

        if (rowDimension != other.getRowDimension() || columnDimension != other.getColumnDimension())
            return false;

        for (int i = 0; i < rowDimension; i++) {
            for (int j = 0; j < columnDimension; j++) {
                if (get(i, j) != other.get(i, j)) return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder matrix = new StringBuilder();

        int m = getColumnDimension() - 1;
        for (int i = 0, n = getRowDimension(); i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix.append(get(i, j)).append(" ");
            }
            matrix.append(get(i, m));
            matrix.append(System.lineSeparator());
        }

        return matrix.toString();
    }

    private static class AbstractMatrixIterator implements Iterator<Double> {

        private final IMatrix matrix;
        private final int rowDimension;
        private final int columnDimension;

        private int rowCount;
        private int columnCount;

        public AbstractMatrixIterator(IMatrix matrix) {
            this.matrix = matrix;
            rowDimension = matrix.getRowDimension();
            columnDimension = matrix.getColumnDimension();
        }

        @Override
        public boolean hasNext() {
            return rowCount < rowDimension || columnCount < columnDimension;
        }

        @Override
        public Double next() {
            double value = matrix.get(rowCount, columnCount++);

            if (columnCount == columnDimension - 1) {
                rowCount++;
            }

            return value;
        }
    }
}