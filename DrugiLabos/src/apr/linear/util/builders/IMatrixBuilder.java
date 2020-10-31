package apr.linear.util.builders;

import apr.linear.matrix.IMatrix;

/**
 * Represents a matrix builder
 */
@FunctionalInterface
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
