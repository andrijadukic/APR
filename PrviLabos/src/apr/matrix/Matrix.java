package apr.matrix;

public interface Matrix {

    int getRowDimension();

    int getColumnDimension();

    void swapRows(int i, int j);

    void swapColumns(int i, int j);

    void print();

    void save(String fileName);
}
