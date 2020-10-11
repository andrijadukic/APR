package apr.matrix;

public class Matrix extends AbstractMatrix {

    private final double[][] array;
    private final int rows;
    private final int columns;

    public Matrix(int rows, int columns, double[][] array) {
        this.rows = rows;
        this.columns = columns;
        this.array = array;
    }

    public Matrix(double[]... array) {
        if (!Matrices.isMatrixArray(array)) throw new IllegalArgumentException();

        rows = array.length;
        columns = array[0].length;
        this.array = array;
    }

    @Override
    public IMatrix copy() {
        double[][] copiedArray = new double[rows][columns];

        for (int i = 0; i < rows; i++) {
            if (columns >= 0) {
                System.arraycopy(array[i], 0, copiedArray[i], 0, columns);
            }
        }
        return new Matrix(rows, columns, copiedArray);
    }

    @Override
    public IMatrix newInstance(int rows, int columns) {
        return new Matrix(rows, columns, new double[rows][columns]);
    }

    @Override
    public int rows() {
        return rows;
    }

    @Override
    public int columns() {
        return columns;
    }

    @Override
    public double get(int i, int j) {
        return array[i][j];
    }

    @Override
    public IVector getRow(int index) {
        return new Vector(array[index]);
    }

    @Override
    public IVector getColumn(int index) {
        double[] column = new double[rows];

        for (int i = 0; i < rows; i++) {
            column[i] = array[i][index];
        }

        return new Vector(column);
    }

    @Override
    public IMatrix set(int i, int j, double value) {
        array[i][j] = value;
        return this;
    }

    @Override
    public void swapRows(int i, int j) {
        double[] tempRow = array[i];
        array[i] = array[j];
        array[j] = tempRow;
    }

    @Override
    public void swapColumns(int i, int j) {
        for (int k = 0; k < rows; k++) {
            double temp = array[k][i];
            array[k][i] = array[k][j];
            array[k][j] = temp;
        }
    }

    @Override
    public IVector[] toColumnVectors() {
        IVector[] columnVectors = new IVector[columns];

        for (int i = 0; i < columns; i++) {
            columnVectors[i] = getColumn(i);
        }

        return columnVectors;
    }

    @Override
    public IVector[] toRowVectors() {
        IVector[] rowVectors = new IVector[rows];

        for (int i = 0; i < rows; i++) {
            rowVectors[i] = getRow(i);
        }

        return rowVectors;
    }

    @Override
    public double[][] toArray() {
        return array;
    }
}
