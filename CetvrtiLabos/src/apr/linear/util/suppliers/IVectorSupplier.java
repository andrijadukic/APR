package apr.linear.util.suppliers;

import apr.linear.vector.IVector;

/**
 * Represents a vector builder
 */
@FunctionalInterface
public interface IVectorSupplier {

    /**
     * Builds a vector
     *
     * @return new vector
     */
    IVector getAsVector();
}
