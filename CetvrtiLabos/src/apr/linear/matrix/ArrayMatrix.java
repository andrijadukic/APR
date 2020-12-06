package apr.linear.matrix;

import apr.linear.vector.Vector;
import apr.linear.vector.ArrayVector;

/**
 * Matrix class which uses a two dimensional array to store elements
 */
public class ArrayMatrix extends AbstractMatrix {

    private final double[][] array;
    private final int rowDimension;
    private final int columnDimension;

    public ArrayMatrix(int rowDimension, int columnDimension) {
        this(rowDimension, columnDimension, new double[rowDimension][columnDimension]);
    }

    private ArrayMatrix(int rowDimension, int columnDimension, double[][] array) {
        this.rowDimension = rowDimension;
        this.columnDimension = columnDimension;
        this.array = array;
    }

    public ArrayMatrix(double[]... array) {
        rowDimension = array.length;
        columnDimension = array[0].length;
        for (int i = 1; i < rowDimension; i++) {
            if (array[i].length != columnDimension) throw new IllegalArgumentException();
        }
        this.array = array;
    }

    @Override
    public ArrayMatrix copy() {
        double[][] copiedArray = new double[rowDimension][columnDimension];
        for (int i = 0; i < rowDimension; i++) {
            if (columnDimension >= 0) {
                System.arraycopy(array[i], 0, copiedArray[i], 0, columnDimension);
            }
        }
        return new ArrayMatrix(rowDimension, columnDimension, copiedArray);
    }

    @Override
    public ArrayMatrix newInstance(int rows, int columns) {
        return new ArrayMatrix(rows, columns);
    }

    @Override
    public int getRowDimension() {
        return rowDimension;
    }

    @Override
    public int getColumnDimension() {
        return columnDimension;
    }

    @Override
    public double get(int i, int j) {
        return array[i][j];
    }

    @Override
    public ArrayMatrix set(int i, int j, double value) {
        array[i][j] = value;
        return this;
    }

    @Override
    public Vector getRow(int index) {
        return new ArrayVector(array[index]);
    }

    @Override
    public Vector getColumn(int index) {
        double[] column = new double[rowDimension];
        for (int i = 0; i < rowDimension; i++) {
            column[i] = array[i][index];
        }
        return new ArrayVector(column);
    }

    @Override
    public void swapRows(int i, int j) {
        var tempRow = array[i];
        array[i] = array[j];
        array[j] = tempRow;
    }

    @Override
    public void swapColumns(int i, int j) {
        for (int row = 0; row < rowDimension; row++) {
            var temp = array[row][i];
            array[row][i] = array[row][j];
            array[row][j] = temp;
        }
    }

    @Override
    public Vector[] columns() {
        Vector[] columns = new Vector[columnDimension];
        for (int i = 0; i < columnDimension; i++) {
            columns[i] = getColumn(i);
        }
        return columns;
    }

    @Override
    public Vector[] rows() {
        Vector[] rows = new Vector[rowDimension];
        for (int i = 0; i < rowDimension; i++) {
            rows[i] = getRow(i);
        }
        return rows;
    }

    @Override
    public double[][] toArray() {
        return array;
    }
}
