package apr.linear.vector;

import apr.linear.matrix.IMatrix;
import apr.linear.util.MatrixUtils;

/**
 * Abstract Vector class which implements vector operations, independent of the underlying data structure
 */
public abstract class AbstractVector implements IVector {

    @Override
    public IVector add(IVector other) {
        MatrixUtils.checkAdditionApplicable(this, other);

        int n = getDimension();
        IVector result = newInstance(n);
        for (int i = 0; i < n; i++) {
            result.set(i, get(i) + other.get(i));
        }

        return result;
    }

    @Override
    public IVector subtract(IVector other) {
        MatrixUtils.checkAdditionApplicable(this, other);

        int n = getDimension();
        IVector result = newInstance(n);
        for (int i = 0; i < n; i++) {
            result.set(i, get(i) + other.get(i));
        }

        return result;
    }

    @Override
    public IVector multiply(IMatrix matrix) {
        MatrixUtils.checkMultiplicationApplicable(matrix, this);

        int n = getDimension();
        IVector result = newInstance(n);
        for (int i = 0; i < n; i++) {
            double sum = 0.;
            for (int j = 0; j < n; j++) {
                sum += get(i) * matrix.get(j, i);
            }
            result.set(i, sum);
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
        int n = getDimension();
        IVector result = newInstance(n);
        for (int i = 0; i < n; i++) {
            result.set(i, get(i) * scalar);
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof IVector other)) return false;

        int dimension = getDimension();

        if (dimension != other.getDimension()) return false;

        for (int i = 0; i < dimension; i++) {
            if (get(i) != other.get(i)) return false;
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder vector = new StringBuilder();

        int n = getDimension() - 1;
        for (int i = 0; i < n; i++) {
            vector.append(get(i)).append(" ");
        }
        vector.append(get(n));

        return vector.toString();
    }
}
