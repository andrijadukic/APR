package apr.matrix;

import apr.matrix.util.Matrices;
import apr.matrix.util.MatrixUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class AbstractRealMatrix implements RealMatrix {

    public RealMatrix add(RealMatrix other) {
        MatrixUtils.checkAdditionApplicable(this, other);

        for (int i = 0; i < getRowDimension(); i++) {
            for (int j = 0; j < getColumnDimension(); j++) {
                set(i, j, get(i, j) + other.get(i, j));
            }
        }
        return this;
    }

    public RealMatrix subtract(RealMatrix other) {
        MatrixUtils.checkAdditionApplicable(this, other);

        for (int i = 0; i < getRowDimension(); i++) {
            for (int j = 0; j < getColumnDimension(); j++) {
                set(i, j, get(i, j) - other.get(i, j));
            }
        }
        return this;
    }

    public RealMatrix multiply(RealMatrix other) {
        MatrixUtils.checkMultiplicationApplicable(this, other);

        RealMatrix matrix = Matrices.blank(getRowDimension(), other.getColumnDimension());

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

    public Vector multiply(RealVector vector) {
        MatrixUtils.checkMultiplicationApplicable(this, vector);

        RealVector result = vector.newInstance(getRowDimension());
        for (int i = 0, n = getRowDimension(); i < n; i++) {
            double sum = 0.;
            for (int j = 0, m = vector.getDimension(); j < m; j++) {
                sum += get(i, j) * vector.get(j);
            }
            result.set(i, sum);
        }
        return result;
    }

    public RealMatrix multiply(double scalar) {
        for (int i = 0; i < getRowDimension(); i++) {
            for (int j = 0; j < getColumnDimension(); j++) {
                set(i, j, get(i, j) * scalar);
            }
        }
        return this;
    }

    @Override
    public RealMatrix transpose() {
        return new TransposedRealMatrixView(this);
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