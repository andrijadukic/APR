package apr.linear.matrix;

import apr.linear.util.LinearAlgebra;

/**
 * Abstract Matrix class which implements matrix operations, independent of the underlying data structure
 */
public abstract class AbstractMatrix implements IMatrix {

    @Override
    public IMatrix add(IMatrix other) {
        return LinearAlgebra.add(this, other);
    }

    @Override
    public IMatrix add(double value) {
        return LinearAlgebra.add(this, value);
    }

    @Override
    public IMatrix subtract(IMatrix other) {
        return LinearAlgebra.subtract(this, other);
    }

    @Override
    public IMatrix subtract(double value) {
        return LinearAlgebra.subtract(this, value);
    }

    @Override
    public IMatrix multiply(IMatrix other) {
        return LinearAlgebra.multiply(this, other);
    }

    @Override
    public IMatrix multiply(double scalar) {
        return LinearAlgebra.multiply(this, scalar);
    }

    @Override
    public IMatrix transpose() {
        return new TransposedMatrixView(this);
    }

    @Override
    public void print() {
        System.out.println(toString());
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
}