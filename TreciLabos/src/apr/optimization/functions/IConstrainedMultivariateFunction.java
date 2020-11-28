package apr.optimization.functions;

public interface IConstrainedMultivariateFunction extends IMultivariateFunction {

    double getCoefficient();

    void setCoefficient(double coefficient);
}
