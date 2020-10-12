package apr.linear.vector;

import apr.linear.matrix.IMatrix;
import apr.linear.util.MatrixUtils;

public abstract class AbstractVector implements IVector {

    @Override
    public IVector add(IVector other) {
        MatrixUtils.checkAdditionApplicable(this, other);

        for (int i = 0, n = getDimension(); i < n; i++) {
            set(i, get(i) + other.get(i));
        }

        return this;
    }

    @Override
    public IVector subtract(IVector other) {
        MatrixUtils.checkAdditionApplicable(this, other);

        for (int i = 0, n = getDimension(); i < n; i++) {
            set(i, get(i) + other.get(i));
        }

        return this;
    }

    @Override
    public IVector multiply(IMatrix matrix) {
        MatrixUtils.checkMultiplicationApplicable(matrix, this);

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
        MatrixUtils.checkMultiplicationApplicable(this, other);

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
