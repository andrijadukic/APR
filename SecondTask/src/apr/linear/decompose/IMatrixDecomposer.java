package apr.linear.decompose;

import apr.linear.matrix.IMatrix;

/**
 * Represents an object used for matrix decomposition
 */
public interface IMatrixDecomposer {

    /**
     * Tests whether the given matrix is applicable for this decomposition algorithm
     *
     * @param matrix matrix to be tested
     * @return true if matrix is applicable, false otherwise
     */
    boolean isApplicable(IMatrix matrix);

    /**
     * Gets the determinant of the given matrix
     *
     * @return determinant of the given matrix
     */
    double getDeterminant();

    /**
     * Gets a linear equation solver
     *
     * @return solver object
     */
    ILinearEquationSolver solver();
}
