package apr.functions;

import apr.linear.vector.Vector;

@FunctionalInterface
public interface UnivariateVectorFunction {

    Vector valueAt(double x);
}
