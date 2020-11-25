package apr.optimization.functions;

import apr.linear.vector.IVector;

@FunctionalInterface
public interface IMultivariableFunctionGradient {

    IVector valueAt(IVector x);
}
