package apr.matrix;

public class TransposedMatrixView extends AbstractMatrix {

    private final IMatrix view;

    public TransposedMatrixView(IMatrix view) {
        this.view = view;
    }

    @Override
    public IMatrix copy() {
        return new TransposedMatrixView(view.copy());
    }

    @Override
    public IMatrix newInstance(int rows, int columns) {
        return view.newInstance(columns, rows);
    }

    @Override
    public int rows() {
        return view.columns();
    }

    @Override
    public int columns() {
        return view.rows();
    }

    @Override
    public double get(int i, int j) {
        return view.get(j, i);
    }

    @Override
    public IVector getRow(int index) {
        return view.getColumn(index);
    }

    @Override
    public IVector getColumn(int index) {
        return view.getRow(index);
    }

    @Override
    public IMatrix set(int i, int j, double value) {
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
    public IVector[] toColumnVectors() {
        return view.toRowVectors();
    }

    @Override
    public IVector[] toRowVectors() {
        return view.toColumnVectors();
    }

    @Override
    public double[][] toArray() {
        double[][] array = new double[view.columns()][view.columns()];

        for (int i = 0, n = columns(); i < n; i++) {
            for (int j = 0, m = rows(); j < m; j++) {
                array[i][j] = get(i, j);
            }
        }

        return array;
    }
}
