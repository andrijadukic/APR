package apr.optimization.functions;

import apr.linear.matrix.IMatrix;
import apr.linear.vector.IVector;

@FunctionalInterface
public interface IMultivariableMatrixFunction {

    IMatrix valueAt(IVector x);
}
