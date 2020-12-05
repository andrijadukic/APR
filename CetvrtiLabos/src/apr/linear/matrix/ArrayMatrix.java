package apr.linear.matrix;

import apr.linear.vector.Vector;
import apr.linear.vector.ArrayVector;

/**
 * Matrix class which uses a two dimensional array to store elements
 */
public class ArrayMatrix extends AbstractMatrix {

    private final double[][] array;
    private final int rows;
    private final int columns;

    public ArrayMatrix(int rows, int columns) {
        array = new double[rows][columns];
        this.rows = rows;
        this.columns = columns;
    }

    private ArrayMatrix(int rows, int columns, double[][] array) {
        this.rows = rows;
        this.columns = columns;
        this.array = array;
    }

    public ArrayMatrix(double[]... array) {
        rows = array.length;
        columns = array[0].length;

        for (int i = 1; i < rows; i++) {
            if (array[i].length != columns) throw new IllegalArgumentException();
        }

        this.array = array;
    }

    @Override
    public ArrayMatrix copy() {
        double[][] copiedArray = new double[rows][columns];

        for (int i = 0; i < rows; i++) {
            if (columns >= 0) {
                System.arraycopy(array[i], 0, copiedArray[i], 0, columns);
            }
        }
        return new ArrayMatrix(rows, columns, copiedArray);
    }

    @Override
    public ArrayMatrix newInstance(int rows, int columns) {
        return new ArrayMatrix(rows, columns, new double[rows][columns]);
    }

    @Override
    public int getRowDimension() {
        return rows;
    }

    @Override
    public int getColumnDimension() {
        return columns;
    }

    @Override
    public double get(int i, int j) {
        return array[i][j];
    }

    @Override
    public Vector getRow(int index) {
        return new ArrayVector(array[index]);
    }

    @Override
    public Vector getColumn(int index) {
        double[] column = new double[rows];

        for (int i = 0; i < rows; i++) {
            column[i] = array[i][index];
        }

        return new ArrayVector(column);
    }

    @Override
    public ArrayMatrix set(int i, int j, double value) {
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
    public Vector[] asColumnVectors() {
        Vector[] columnRealVectors = new Vector[columns];

        for (int i = 0; i < columns; i++) {
            columnRealVectors[i] = getColumn(i);
        }

        return columnRealVectors;
    }

    @Override
    public Vector[] asRowVectors() {
        Vector[] rowRealVectors = new Vector[rows];

        for (int i = 0; i < rows; i++) {
            rowRealVectors[i] = getRow(i);
        }

        return rowRealVectors;
    }

    @Override
    public double[][] toArray() {
        return array;
    }
}
