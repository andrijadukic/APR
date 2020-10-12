package apr.matrix;

public interface IVector {

    IVector copy();

    IVector newInstance(int dimension);

    IMatrix toMatrix();

    int getDimension();

    double get(int i);

    IVector set(int i, double value);

    void swap(int i, int j);

    IVector add(IVector other);

    IVector subtract(IVector other);

    IVector multiply(IMatrix other);

    double multiply(IVector vector);

    IVector multiply(double scalar);
}
