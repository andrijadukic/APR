package apr.matrix;

public interface IMatrixDecomposer {

    boolean isApplicable(IMatrix matrix);

    IMatrix[] decompose();
}
