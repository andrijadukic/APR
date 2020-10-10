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
}
