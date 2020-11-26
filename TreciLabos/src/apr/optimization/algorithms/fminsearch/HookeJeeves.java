package apr.optimization.algorithms.fminsearch;

import apr.linear.vector.IVector;
import apr.optimization.functions.IMultivariateCostFunction;

import static apr.linear.util.linalg.LinearAlgebra.*;
import static apr.linear.util.linalg.OperationMutability.*;

/**
 * Implementation of the Hooke-Jeeves algorithm
 */
public class HookeJeeves extends AbstractMultivariateOptimizer {

    private double delta = 0.5;

    public HookeJeeves(IMultivariateCostFunction f) {
        super(f);
    }

    public HookeJeeves(IMultivariateCostFunction f, double epsilon, double dx) {
        super(f, epsilon);
        this.delta = dx;
    }

    public double getEpsilon() {
        return epsilon;
    }

    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
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

        double fxb = f.valueAt(xb);
        double dx = delta;
        while (dx >= epsilon) {
            IVector xn = explore(xp, dx);
            double fxn = f.valueAt(xn);
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
        double fxInitial = f.valueAt(x);
        for (int i = 0, n = x.getDimension(); i < n; i++) {
            double xi = x.get(i);
            x.set(i, xi + dx);
            double fxNew = f.valueAt(x);
            if (fxNew > fxInitial) {
                x.set(i, xi - dx);
                fxNew = f.valueAt(x);
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
