package apr.linear.decompose;

import apr.linear.matrix.IMatrix;

public interface MatrixDecomposer {

    boolean isApplicable(IMatrix matrix);

    double getDeterminant();

    LinearEquationSolver solver();
}
