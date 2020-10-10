package apr.matrix;

import java.security.InvalidParameterException;

public abstract class AbstractVector implements IVector {

    @Override
    public IVector add(IVector other) {
        if (!Matrices.areDimensionsSame(this, other)) throw new InvalidParameterException();

        for (int i = 0, n = getDimension(); i < n; i++) {
            set(i, get(i) + other.get(i));
        }

        return this;
    }

    @Override
    public IVector sub(IVector other) {
        if (!Matrices.areDimensionsSame(this, other)) throw new InvalidParameterException();

        for (int i = 0, n = getDimension(); i < n; i++) {
            set(i, get(i) + other.get(i));
        }

        return this;
    }

    @Override
    public IVector multiply(IMatrix matrix) {
        if (!Matrices.isMultiplicationApplicable(matrix, this)) throw new InvalidParameterException();

        for (int i = 0, n = getDimension(); i < n; i++) {
            double sum = 0.;
            for (int j = 0; j < n; j++) {
                sum += get(i) * matrix.get(j, i);
            }
            set(i, sum);
        }

        return this;
    }

    @Override
    public double multiply(IVector other) {
        if (!Matrices.isMultiplicationApplicable(this, other)) throw new InvalidParameterException();

        double sum = 0.;
        for (int i = 0, n = getDimension(); i < n; i++) {
            sum += get(i) * other.get(i);
        }
        return sum;
    }

    @Override
    public IVector multiply(double scalar) {
        for (int i = 0, n = getDimension(); i < n; i++) {
            set(i, get(i) * scalar);
        }
        return this;
    }
}
