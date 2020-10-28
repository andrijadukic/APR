package apr.optimization.function;

/**
 * Interface defining a cost function with a counter feature
 */
public interface ICostFunction extends IFunction {

    /**
     * Resets the memory
     */
    void reset();

    /**
     * Gets the current value of the counter
     *
     * @return current value of the counter
     */
    long getCounter();
}
