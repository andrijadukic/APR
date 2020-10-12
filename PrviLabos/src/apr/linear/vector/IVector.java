package apr.linear.vector;

import apr.linear.matrix.IMatrix;

public interface IVector {

    IVector copy();

    IVector newInstance(int dimension);

    int getDimension();

    double get(int i);

    IVector set(int i, double value);

    void swap(int i, int j);

    IVector add(IVector other);

    IVector subtract(IVector other);

    IVector multiply(IMatrix matrix);

    double multiply(IVector other);

    IVector multiply(double scalar);

    IMatrix toMatrix();
}
