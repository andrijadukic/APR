package apr.optimization.function;

import apr.linear.vector.IVector;

public interface IFunction {

    double valueAt(IVector vector);
}
