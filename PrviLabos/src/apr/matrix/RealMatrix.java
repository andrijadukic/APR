package apr.matrix;


public interface RealMatrix extends Matrix {

    RealMatrix copy();

    RealMatrix newInstance(int rows, int columns);

    double get(int i, int j);

    RealVector getRow(int index);

    RealVector getColumn(int index);

    RealMatrix set(int i, int j, double value);

    RealMatrix add(RealMatrix other);

    RealMatrix subtract(RealMatrix other);

    RealMatrix multiply(RealMatrix other);

    Vector multiply(RealVector vector);

    RealMatrix multiply(double scalar);

    RealMatrix transpose();

    RealVector[] toColumnRealVectors();

    RealVector[] toRowRealVectors();

    double[][] toArray();
}
