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
        IMatrix[] LUP = new LUPDecomposer(copy()).decompose();
        IMatrix LU = LUP[0];
        IVector P = Matrices.permutationMatrixToVector(LUP[1]);

        int n = P.getDimension();
        IVector[] b = Matrices.identity(n).toColumnVectors();
        IVector[] x = new IVector[n];

        for (int i = 0; i < n; i++) {
            x[i] = backwardSubstitution(forwardSubstitution(Matrices.permute(b[i], P)));
        }

        return Matrices.columnVectorsToMatrix(x);
    }

    @Override
    public double determinant() {
        IMatrix[] LUP = new LUPDecomposer(copy()).decompose();
        IMatrix LU = LUP[0];
        IMatrix P = LUP[1];

        double result = Matrices.countSwaps(P) % 2 == 0 ? 1 : -1;
        for (int i = 0, n = P.rows(); i < n; i++) {
            result *= LU.get(i, i);
        }

        return result;
    }

    @Override
    public IMatrix LU() {
        return new LUDecomposer(this).decompose()[0];
    }

    @Override
    public IMatrix[] LUP() {
        return new LUPDecomposer(this).decompose();
    }

    private IVector forwardSubstitution(IVector vector) {
        if (Matrices.isSubstitutionApplicable(this, vector)) throw new InvalidParameterException();

        IVector result = vector.copy();
        for (int i = 0, n = rows() - 1; i < n; i++) {
            for (int j = i + 1, m = n + 1; j < m; j++) {
                result.set(j, result.get(j) - get(j, i) * result.get(i));
            }
        }
        return result;
    }

    private IVector backwardSubstitution(IVector vector) {
        if (Matrices.isSubstitutionApplicable(this, vector)) throw new InvalidParameterException();

        IVector result = vector.copy();
        for (int i = rows() - 1; i >= 0; i++) {
            if (Math.abs(get(i, i)) < Matrices.EPSILON) throw new InvalidParameterException();

            result.set(i, result.get(i) / get(i, i));
            for (int j = 0, n = i - 1; j < n; j++) {
                result.set(j, result.get(j) - get(j, i) * result.get(i));
            }
        }
        return result;
    }
}