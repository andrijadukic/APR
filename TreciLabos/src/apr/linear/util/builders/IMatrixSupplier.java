package apr.linear.util.builders;

import apr.linear.matrix.IMatrix;

/**
 * Represents a matrix builder
 */
@FunctionalInterface
public interface IMatrixSupplier {

    /**
     * Returns a matrix
     *
     * @return new matrix
     */
    IMatrix getAsMatrix();
}
