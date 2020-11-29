package apr.optimization.algorithms.multi.noderiv;

import apr.linear.vector.IVector;
import apr.optimization.algorithms.multi.IMultivariateCostFunction;

import static apr.linear.util.linalg.LinearAlgebra.*;
import static apr.linear.util.linalg.OperationMutability.*;

/**
 * Implementation of the Hooke-Jeeves algorithm
 */
public class HookeJeeves extends AbstractMultivariateOptimizer {

    private double delta = DEFAULT_DELTA;

    private static final double DEFAULT_DELTA = 0.5;

    public HookeJeeves(IMultivariateCostFunction f) {
        super(f);
    }

    public HookeJeeves(IMultivariateCostFunction f, double epsilon, double delta) {
        super(f, epsilon);
        this.delta = delta;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    @Override
    public IVector search(IVector x0) {
        IVector xp = x0;
        IVector xb = x0;

        double fxb = function.valueAt(xb);
        double dx = delta;
        while (dx >= epsilon) {
            IVector xn = explore(xp, dx);
            double fxn = function.valueAt(xn);
            if (fxn < fxb) {
                xp = subtract(multiply(xn, 2, IMMUTABLE), xb, MUTABLE);
                xb = xn;
                fxb = fxn;
            } else {
                dx /= 2;
                xp = xb;
            }
        }

        return xb;
    }

    private IVector explore(IVector xp, double dx) {
        IVector x = xp.copy();
        double fxInitial = function.valueAt(x);
        for (int i = 0, n = x.getDimension(); i < n; i++) {
            double xi = x.get(i);
            x.set(i, xi + dx);
            double fxNew = function.valueAt(x);
            if (fxNew > fxInitial) {
                x.set(i, xi - dx);
                fxNew = function.valueAt(x);
                if (fxNew > fxInitial) {
                    x.set(i, xi);
                } else {
                    fxInitial = fxNew;
                }
            } else {
                fxInitial = fxNew;
            }
        }
        return x;
    }

    @Override
    public String getName() {
        return "Hooke Jeeves";
    }
}
