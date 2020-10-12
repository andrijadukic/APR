package apr.matrix;

public interface IMatrixDecomposer {

    boolean isApplicable(IMatrix matrix);

    double getDeterminant();

    ILinearEquationSolver solver();
}
