package apr.linear.vector;

/**
 * Interface used to create a matrix builder
 */
public interface IVectorBuilder {

    /**
     * Builds a vector
     *
     * @param dimension dimension of new vector
     * @return new vector
     */
    IVector build(int dimension);
}
