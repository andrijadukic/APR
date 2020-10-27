package apr.linear.matrix;

import apr.linear.matrix.IMatrix;

/**
 * Interface used to create a matrix builder
 */
public interface IMatrixBuilder {

    /**
     * Builds a matrix
     *
     * @param rowDimension    row dimension of new matrix
     * @param columnDimension column dimension of new matrix
     * @return new matrix
     */
    IMatrix build(int rowDimension, int columnDimension);
}
