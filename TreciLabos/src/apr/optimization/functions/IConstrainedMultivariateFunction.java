package apr.optimization.functions;

/**
 * Represents a constrained multivariate function
 */
public interface IConstrainedMultivariateFunction extends IMultivariateFunction {

    /**
     * Returns coefficient used for scaling constraint penalties
     *
     * @return coefficient
     */
    double getCoefficient();

    /**
     * Sets coefficient used for scaling constraint penalties
     *
     * @param coefficient coefficient
     */
    void setCoefficient(double coefficient);
}
