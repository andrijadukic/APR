package apr.matrix;

import apr.matrix.util.MatrixUtils;

public abstract class AbstractRealVector implements RealVector {

    @Override
    public RealVector add(RealVector other) {
        MatrixUtils.checkAdditionApplicable(this, other);

        for (int i = 0, n = getDimension(); i < n; i++) {
            set(i, get(i) + other.get(i));
        }

        return this;
    }

    @Override
    public RealVector subtract(RealVector other) {
        MatrixUtils.checkAdditionApplicable(this, other);

        for (int i = 0, n = getDimension(); i < n; i++) {
            set(i, get(i) + other.get(i));
        }

        return this;
    }

    @Override
    public RealVector multiply(RealMatrix matrix) {
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
    public double multiply(RealVector other) {
        MatrixUtils.checkMultiplicationApplicable(this, other);

        double sum = 0.;
        for (int i = 0, n = getDimension(); i < n; i++) {
            sum += get(i) * other.get(i);
        }
        return sum;
    }

    @Override
    public RealVector multiply(double scalar) {
        for (int i = 0, n = getDimension(); i < n; i++) {
            set(i, get(i) * scalar);
        }
        return this;
    }
}
