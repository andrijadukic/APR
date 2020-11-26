package apr.optimization.functions;

import apr.linear.vector.IVector;

@FunctionalInterface
public interface IMultivariableVectorFunction {

    IVector valueAt(IVector x);
}
