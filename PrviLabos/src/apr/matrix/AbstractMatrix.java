package apr.matrix;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class AbstractMatrix implements IMatrix {

    public IMatrix add(IMatrix other) {
        MatrixUtils.checkAdditionApplicable(this, other);

        for (int i = 0; i < getRowDimension(); i++) {
            for (int j = 0; j < getColumnDimension(); j++) {
                set(i, j, get(i, j) + other.get(i, j));
            }
        }
        return this;
    }

    public IMatrix subtract(IMatrix other) {
        MatrixUtils.checkAdditionApplicable(this, other);

        for (int i = 0; i < getRowDimension(); i++) {
            for (int j = 0; j < getColumnDimension(); j++) {
                set(i, j, get(i, j) - other.get(i, j));
            }
        }
        return this;
    }

    public IMatrix multiply(IMatrix other) {
        MatrixUtils.checkMultiplicationApplicable(this, other);

        IMatrix matrix = Matrices.blank(getRowDimension(), other.getColumnDimension());

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

    public IMatrix multiply(double scalar) {
        for (int i = 0; i < getRowDimension(); i++) {
            for (int j = 0; j < getColumnDimension(); j++) {
                set(i, j, get(i, j) * scalar);
            }
        }
        return this;
    }

    @Override
    public IMatrix transpose() {
        return new TransposedMatrixView(this);
    }

    @Override
    public IMatrix invert() {
        return new LUPDecomposer(this).solver().solve(Matrices.identity(getRowDimension()));
    }

    @Override
    public void print() {
        System.out.println(toString());
    }

    @Override
    public void save(String fileName) {
        try (var writer = Files.newBufferedWriter(Paths.get(fileName))) {
            writer.write(toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
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