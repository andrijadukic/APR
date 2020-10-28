package apr.optimization.function;

public interface ISingleVariableCostFunction extends ISingleVariableFunction {

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
