package apr.optimization.function;

import apr.linear.vector.IVector;

/**
 * Interface defining a cost function with a counter feature
 */
public interface ICostFunction extends IFunction {

    /**
     * Increments the counter
     */
    void increment();

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

    /**
     * Sets starting point
     *
     * @param x0 starting point
     */
    void setX0(IVector x0);

    /**
     * Sets argmin of function
     *
     * @param xMin argmin of function
     */
    void setXMin(IVector xMin);
}
