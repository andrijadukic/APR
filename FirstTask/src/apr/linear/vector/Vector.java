package apr.linear.vector;

import apr.linear.matrix.IMatrix;
import apr.linear.matrix.Matrix;
import apr.linear.util.Matrices;
import apr.linear.util.MatrixUtils;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Concrete Vector class which used an array to store elements
 */
public class Vector extends AbstractVector {

    private final double[] array;

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
    public IMatrix toMatrix() {
        return new Matrix(1, getDimension(), new double[][]{array});
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
    public void swap(int i, int j) {
        double temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    @Override
    public String toString() {
        return Arrays.stream(array)
                .mapToObj(MatrixUtils.FORMATTER::format)
                .collect(Collectors.joining(" "));
    }
}
