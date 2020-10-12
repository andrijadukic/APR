package apr.matrix;

public abstract class AbstractMatrixDecomposer implements IMatrixDecomposer {

    protected final IMatrix matrix;
    protected final int rowDimension;
    protected final int columnDimension;

    public AbstractMatrixDecomposer(IMatrix matrix) {
        if (!isApplicable(matrix)) throw new IllegalArgumentException("Given matrix is not applicable for this decomposition");

        this.matrix = matrix.copy();
        rowDimension = matrix.getRowDimension();
        columnDimension = matrix.getColumnDimension();
    }
}
