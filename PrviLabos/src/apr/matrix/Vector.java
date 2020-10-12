package apr.matrix;

public interface Vector {

    RealMatrix toMatrix();

    int getDimension();

    void swap(int i, int j);
}
