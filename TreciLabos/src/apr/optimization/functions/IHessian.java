package apr.optimization.functions;

import apr.linear.matrix.IMatrix;
import apr.linear.vector.IVector;

@FunctionalInterface
public interface IHessian {

    IMatrix valueAt(IVector x);
}
