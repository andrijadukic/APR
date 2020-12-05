package apr.linear.util.suppliers;

import apr.linear.matrix.Matrix;

/**
 * Represents a matrix builder
 */
@FunctionalInterface
public interface MatrixSupplier {

    /**
     * Returns a matrix
     *
     * @return new matrix
     */
    Matrix getAsMatrix();
}
