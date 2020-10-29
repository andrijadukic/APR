package apr.optimization.util;

/**
 * Interface defining an object which keeps track of number of times a certain method was called
 */
public interface ICallCounter {

    /**
     * Resets the memory
     */
    void reset();

    /**
     * Gets the current value of the counter
     *
     * @return current value of the counter
     */
    int getCounter();
}
