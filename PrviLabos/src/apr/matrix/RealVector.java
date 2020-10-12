package apr.matrix;

public interface RealVector extends Vector {

    RealVector copy();

    RealVector newInstance(int dimension);

    double get(int i);

    RealVector set(int i, double value);

    RealVector add(RealVector other);

    RealVector subtract(RealVector other);

    RealVector multiply(RealMatrix matrix);

    double multiply(RealVector other);

    RealVector multiply(double scalar);
}
