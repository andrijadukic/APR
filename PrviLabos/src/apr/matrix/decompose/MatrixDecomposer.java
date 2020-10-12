package apr.matrix.decompose;

import apr.matrix.RealMatrix;

public interface MatrixDecomposer {

    boolean isApplicable(RealMatrix matrix);

    double getDeterminant();

    LinearEquationSolver solver();
}
