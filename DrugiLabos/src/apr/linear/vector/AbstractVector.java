package apr.linear.vector;

import apr.linear.matrix.IMatrix;
import apr.linear.util.LinearAlgebra;

/**
 * Abstract Vector class which implements vector operations, independent of the underlying data structure
 */
public abstract class AbstractVector implements IVector {

    @Override
    public IVector add(IVector other) {
        return LinearAlgebra.add(this, other);
    }

    @Override
    public IVector add(double value) {
        return LinearAlgebra.add(this, value);
    }

    @Override
    public IVector subtract(IVector other) {
        return LinearAlgebra.subtract(this, other);
    }

    @Override
    public IVector subtract(double value) {
        return LinearAlgebra.subtract(this, value);
    }

    @Override
    public IVector multiply(IMatrix matrix) {
        return LinearAlgebra.multiply(this, matrix);
    }

    @Override
    public double multiply(IVector other) {
        return LinearAlgebra.multiply(this, other);
    }

    @Override
    public IVector multiply(double scalar) {
        return LinearAlgebra.multiply(this, scalar);
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
