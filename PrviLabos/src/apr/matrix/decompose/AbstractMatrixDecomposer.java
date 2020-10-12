package apr.matrix.decompose;

import apr.matrix.RealMatrix;

public abstract class AbstractMatrixDecomposer implements MatrixDecomposer {

    protected final RealMatrix matrix;
    protected final int rowDimension;
    protected final int columnDimension;

    public AbstractMatrixDecomposer(RealMatrix matrix) {
        if (!isApplicable(matrix)) throw new IllegalArgumentException("Given matrix is not applicable for this decomposition");

        this.matrix = matrix.copy();
        rowDimension = matrix.getRowDimension();
        columnDimension = matrix.getColumnDimension();
    }
}
