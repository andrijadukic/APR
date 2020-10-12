package apr.matrix;

import apr.matrix.util.Matrices;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ArrayRealVector extends AbstractRealVector {

    private final double[] array;

    public ArrayRealVector(double... array) {
        this.array = array;
    }

    public ArrayRealVector(int start, int end) {
        array = IntStream.range(start, end).asDoubleStream().toArray();
    }

    @Override
    public ArrayRealVector copy() {
        int dimension = getDimension();
        double[] copiedArray = new double[dimension];
        System.arraycopy(array, 0, copiedArray, 0, dimension);
        return new ArrayRealVector(copiedArray);
    }

    @Override
    public ArrayRealVector newInstance(int dimension) {
        return new ArrayRealVector(new double[dimension]);
    }

    @Override
    public RealMatrix toMatrix() {
        return new ArrayRealMatrix(1, getDimension(), new double[][]{array});
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
    public ArrayRealVector set(int i, double value) {
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
                .mapToObj(Matrices.FORMATTER::format)
                .collect(Collectors.joining(" "));
    }
}
