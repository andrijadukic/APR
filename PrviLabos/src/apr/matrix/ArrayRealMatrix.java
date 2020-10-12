package apr.matrix;

import apr.matrix.util.Matrices;

public class ArrayRealMatrix extends AbstractRealMatrix {

    private final double[][] array;
    private final int rows;
    private final int columns;

    public ArrayRealMatrix(int rows, int columns, double[][] array) {
        this.rows = rows;
        this.columns = columns;
        this.array = array;
    }

    public ArrayRealMatrix(double[]... array) {
        if (!Matrices.isMatrixArray(array)) throw new IllegalArgumentException();

        rows = array.length;
        columns = array[0].length;
        this.array = array;
    }

    @Override
    public ArrayRealMatrix copy() {
        double[][] copiedArray = new double[rows][columns];

        for (int i = 0; i < rows; i++) {
            if (columns >= 0) {
                System.arraycopy(array[i], 0, copiedArray[i], 0, columns);
            }
        }
        return new ArrayRealMatrix(rows, columns, copiedArray);
    }

    @Override
    public ArrayRealMatrix newInstance(int rows, int columns) {
        return new ArrayRealMatrix(rows, columns, new double[rows][columns]);
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
    public RealVector getRow(int index) {
        return new ArrayRealVector(array[index]);
    }

    @Override
    public RealVector getColumn(int index) {
        double[] column = new double[rows];

        for (int i = 0; i < rows; i++) {
            column[i] = array[i][index];
        }

        return new ArrayRealVector(column);
    }

    @Override
    public ArrayRealMatrix set(int i, int j, double value) {
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
    public RealVector[] toColumnRealVectors() {
        RealVector[] columnRealVectors = new RealVector[columns];

        for (int i = 0; i < columns; i++) {
            columnRealVectors[i] = getColumn(i);
        }

        return columnRealVectors;
    }

    @Override
    public RealVector[] toRowRealVectors() {
        RealVector[] rowRealVectors = new RealVector[rows];

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
