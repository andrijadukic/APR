package apr.matrix;

import java.security.InvalidParameterException;

public abstract class AbstractMatrix implements IMatrix {

    public IMatrix add(IMatrix other) {
        if (!Matrices.areDimensionsSame(this, other)) throw new InvalidParameterException();

        for (int i = 0; i < rows(); i++) {
            for (int j = 0; j < columns(); j++) {
                set(i, j, get(i, j) + other.get(i, j));
            }
        }
        return this;
    }

    public IMatrix sub(IMatrix other) {
        if (!Matrices.areDimensionsSame(this, other)) throw new InvalidParameterException();

        for (int i = 0; i < rows(); i++) {
            for (int j = 0; j < columns(); j++) {
                set(i, j, get(i, j) - other.get(i, j));
            }
        }
        return this;
    }

    public IMatrix multiply(IMatrix other) {
        if (Matrices.isMultiplicationApplicable(this, other)) throw new InvalidParameterException();

        IMatrix matrix = Matrices.blank(rows(), other.columns());

        for (int i = 0; i < rows(); i++) {
            for (int j = 0; i < other.columns(); j++) {
                double sum = 0.;
                for (int k = 0; k < columns(); k++) {
                    sum += get(i, k) * other.get(k, j);
                }
                matrix.set(i, j, sum);
            }
        }
        return matrix;
    }

    public IVector multiply(IVector vector) {
        if (!Matrices.isMultiplicationApplicable(this, vector)) throw new InvalidParameterException();

        IVector result = vector.newInstance(rows());
        for (int i = 0, n = rows(); i < n; i++) {
            double sum = 0.;
            for (int j = 0, m = vector.getDimension(); j < m; j++) {
                sum += get(i, j) * vector.get(j);
            }
            result.set(i, sum);
        }
        return result;
    }

    public IMatrix multiply(double scalar) {
        for (int i = 0; i < rows(); i++) {
            for (int j = 0; j < columns(); j++) {
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
        int n = rows();
        IMatrix b = Matrices.identity(n);
        IVector[] x = new IVector[n];

        ILinearEquationSolver solver = new LUPLinearEquationSolver(this);
        for (int i = 0; i < n; i++) {
            x[i] = solver.solve(b.getColumn(i));
        }

        return Matrices.columnVectorsToMatrix(x);
    }

    @Override
    public double determinant() {
        IMatrix[] LUP = new LUPDecomposer(copy()).decompose();
        IMatrix LU = LUP[0];
        IMatrix P = LUP[1];

        double result = Matrices.countSwaps(P.toRowVectors()[0]) % 2 == 0 ? 1 : -1;
        for (int i = 0, n = LU.rows(); i < n; i++) {
            result *= LU.get(i, i);
        }

        return result;
    }

    @Override
    public String toString() {
        int n = rows();

        StringBuilder matrix = new StringBuilder();
        for (int i = 0, m = n - 1; i < m; i++) {
            matrix.append(getRow(i));
            matrix.append(System.lineSeparator());
        }
        matrix.append(getRow(n - 1));

        return matrix.toString();
    }
}