package apr.linear.vector;

import apr.linear.linalg.LinearAlgebra;

import java.util.Iterator;
import java.util.function.DoublePredicate;

/**
 * Abstract Vector class which all IVector implementations should inherit
 */
public abstract class AbstractVector implements Vector {

    @Override
    public boolean anyMatch(DoublePredicate predicate) {
        return LinearAlgebra.anyMatch(iterator(), predicate);
    }

    @Override
    public boolean allMatch(DoublePredicate predicate) {
        return LinearAlgebra.allMatch(iterator(), predicate);
    }

    @Override
    public Iterator<Double> iterator() {
        return new AbstractVectorIterator(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vector other)) return false;

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

    private static class AbstractVectorIterator implements Iterator<Double> {

        private final Vector vector;
        private final int dimension;
        private int count;

        public AbstractVectorIterator(Vector vector) {
            this.vector = vector;
            dimension = vector.getDimension();
        }

        @Override
        public boolean hasNext() {
            return count < dimension;
        }

        @Override
        public Double next() {
            return vector.get(count++);
        }
    }
}
