package apr.matrix;

public class TransposedRealMatrixView extends AbstractRealMatrix {

    private final RealMatrix view;

    public TransposedRealMatrixView(RealMatrix view) {
        this.view = view;
    }

    @Override
    public RealMatrix copy() {
        return new TransposedRealMatrixView(view.copy());
    }

    @Override
    public RealMatrix newInstance(int rows, int columns) {
        return view.newInstance(columns, rows);
    }

    @Override
    public int getRowDimension() {
        return view.getColumnDimension();
    }

    @Override
    public int getColumnDimension() {
        return view.getRowDimension();
    }

    @Override
    public double get(int i, int j) {
        return view.get(j, i);
    }

    @Override
    public RealVector getRow(int index) {
        return view.getColumn(index);
    }

    @Override
    public RealVector getColumn(int index) {
        return view.getRow(index);
    }

    @Override
    public RealMatrix set(int i, int j, double value) {
        return view.set(j, i, value);
    }

    @Override
    public void swapRows(int i, int j) {
        view.swapColumns(i, j);
    }

    @Override
    public void swapColumns(int i, int j) {
        view.swapRows(i, j);
    }

    @Override
    public RealVector[] toColumnRealVectors() {
        return view.toRowRealVectors();
    }

    @Override
    public RealVector[] toRowRealVectors() {
        return view.toColumnRealVectors();
    }

    @Override
    public double[][] toArray() {
        double[][] array = new double[view.getColumnDimension()][view.getColumnDimension()];

        for (int i = 0, n = getColumnDimension(); i < n; i++) {
            for (int j = 0, m = getRowDimension(); j < m; j++) {
                array[i][j] = get(i, j);
            }
        }

        return array;
    }
}
