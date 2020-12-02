package apr.linear.vector;

import apr.linear.matrix.IMatrix;
import apr.linear.matrix.Matrix;

import java.util.stream.IntStream;

/**
 * Vector class which uses an array to store elements
 */
public class Vector extends AbstractVector {

    private final double[] array;

    public Vector(int dimension) {
        this(new double[dimension]);
    }

    public Vector(double... array) {
        this.array = array;
    }

    public Vector(int start, int end) {
        array = IntStream.range(start, end).asDoubleStream().toArray();
    }

    @Override
    public Vector copy() {
        int dimension = getDimension();
        double[] copiedArray = new double[dimension];
        System.arraycopy(array, 0, copiedArray, 0, dimension);
        return new Vector(copiedArray);
    }

    @Override
    public Vector newInstance(int dimension) {
        return new Vector(new double[dimension]);
    }

    @Override
    public int getDimension() {
        return array.length;
    }

    @Override
    public double get(int i) {
        return array[i];
    }

    @Override
    public Vector set(int i, double value) {
        array[i] = value;
        return this;
    }

    @Override
    public IMatrix asMatrix() {
        return new Matrix(new double[][]{array});
    }
}
