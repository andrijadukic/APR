package apr.optimization.function;

import apr.linear.vector.IVector;

public interface ICostFunction extends IFunction {

    void increment();

    void reset();

    long getCounter();

    void setX0(IVector x0);

    void setXMin(IVector xMin);
}
