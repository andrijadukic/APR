package apr.linear.matrix;

import apr.linear.vector.IVector;
import apr.linear.vector.Vector;

/**
 * Matrix class which uses a two dimensional array to store elements
 */
public class Matrix extends AbstractMatrix {

    private final double[][] array;
    private final int rows;
    private final int columns;

    public Matrix(int rows, int columns) {
        array = new double[rows][columns];
        this.rows = rows;
        this.columns = columns;
    }

    private Matrix(int rows, int columns, double[][] array) {
        this.rows = rows;
        this.columns = columns;
        this.array = array;
    }

    public Matrix(double[]... array) {
        rows = array.length;
        columns = array[0].length;

        for (int i = 1; i < rows; i++) {
            if (array[i].length != columns) throw new IllegalArgumentException();
        }

        this.array = array;
    }

    @Override
    public Matrix copy() {
        double[][] copiedArray = new double[rows][columns];

        for (int i = 0; i < rows; i++) {
            if (columns >= 0) {
                System.arraycopy(array[i], 0, copiedArray[i], 0, columns);
            }
        }
        return new Matrix(rows, columns, copiedArray);
    }

    @Override
    public Matrix newInstance(int rows, int columns) {
        return new Matrix(rows, columns, new double[rows][columns]);
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
    public Matrix set(int i, int j, double value) {
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
    public IVector[] asColumnVectors() {
        IVector[] columnRealVectors = new IVector[columns];

        for (int i = 0; i < columns; i++) {
            columnRealVectors[i] = getColumn(i);
        }

        return columnRealVectors;
    }

    @Override
    public IVector[] asRowVectors() {
        IVector[] rowRealVectors = new IVector[rows];

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
