package apr.linear.matrix;

import apr.linear.vector.IVector;

public interface IMatrix {

    IMatrix copy();

    IMatrix newInstance(int rows, int columns);

    int getRowDimension();

    int getColumnDimension();

    double get(int i, int j);

    IVector getRow(int index);

    IVector getColumn(int index);

    IMatrix set(int i, int j, double value);

    void swapRows(int i, int j);

    void swapColumns(int i, int j);

    IMatrix add(IMatrix other);

    IMatrix subtract(IMatrix other);

    IMatrix multiply(IMatrix other);

    IVector multiply(IVector vector);

    IMatrix multiply(double scalar);

    IMatrix transpose();

    IVector[] toColumnRealVectors();

    IVector[] toRowRealVectors();

    double[][] toArray();

    void print();

    void save(String fileName);
}
