package apr.linear.matrix;

import apr.linear.vector.IVector;
import apr.linear.util.Matrices;
import apr.linear.util.MatrixUtils;

/**
 * Abstract Matrix class which implements matrix operations, independent of the underlying data structure
 */
public abstract class AbstractMatrix implements IMatrix {

    @Override
    public IMatrix add(IMatrix other) {
        MatrixUtils.checkAdditionApplicable(this, other);

        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        IMatrix result = newInstance(rowDimension, columnDimension);
        for (int i = 0; i < rowDimension; i++) {
            for (int j = 0; j < columnDimension; j++) {
                result.set(i, j, get(i, j) + other.get(i, j));
            }
        }
        return this;
    }

    @Override
    public IMatrix subtract(IMatrix other) {
        MatrixUtils.checkAdditionApplicable(this, other);

        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        IMatrix result = newInstance(rowDimension, columnDimension);
        for (int i = 0; i < rowDimension; i++) {
            for (int j = 0; j < columnDimension; j++) {
                result.set(i, j, get(i, j) - other.get(i, j));
            }
        }
        return this;
    }

    @Override
    public IMatrix multiply(IMatrix other) {
        MatrixUtils.checkMultiplicationApplicable(this, other);

        IMatrix matrix = Matrices.blank(getRowDimension(), other.getColumnDimension(), this::newInstance);

        for (int i = 0; i < getRowDimension(); i++) {
            for (int j = 0; i < other.getColumnDimension(); j++) {
                double sum = 0.;
                for (int k = 0; k < getColumnDimension(); k++) {
                    sum += get(i, k) * other.get(k, j);
                }
                matrix.set(i, j, sum);
            }
        }
        return matrix;
    }

    @Override
    public IVector multiply(IVector vector) {
        MatrixUtils.checkMultiplicationApplicable(this, vector);

        IVector result = vector.newInstance(getRowDimension());
        for (int i = 0, n = getRowDimension(); i < n; i++) {
            double sum = 0.;
            for (int j = 0, m = vector.getDimension(); j < m; j++) {
                sum += get(i, j) * vector.get(j);
            }
            result.set(i, sum);
        }
        return result;
    }

    @Override
    public IMatrix multiply(double scalar) {
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        IMatrix result = newInstance(rowDimension, columnDimension);
        for (int i = 0; i < rowDimension; i++) {
            for (int j = 0; j < columnDimension; j++) {
                result.set(i, j, get(i, j) * scalar);
            }
        }
        return result;
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
        int n = getRowDimension();

        StringBuilder matrix = new StringBuilder();
        for (int i = 0, m = n - 1; i < m; i++) {
            matrix.append(getRow(i));
            matrix.append(System.lineSeparator());
        }
        matrix.append(getRow(n - 1));

        return matrix.toString();
    }
}