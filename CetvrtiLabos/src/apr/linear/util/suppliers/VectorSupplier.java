package apr.linear.util.suppliers;

import apr.linear.vector.Vector;

/**
 * Represents a vector builder
 */
@FunctionalInterface
public interface VectorSupplier {

    /**
     * Builds a vector
     *
     * @return new vector
     */
    Vector getAsVector();
}
