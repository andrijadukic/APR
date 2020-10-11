package apr.matrix;

public abstract class AbstractMatrixDecomposer implements IMatrixDecomposer {

    protected final IMatrix matrix;

    public AbstractMatrixDecomposer(IMatrix matrix) {
        if (!isApplicable(matrix)) throw new IllegalArgumentException("Given matrix is not applicable for this decomposition");

        this.matrix = matrix.copy();
    }
}
