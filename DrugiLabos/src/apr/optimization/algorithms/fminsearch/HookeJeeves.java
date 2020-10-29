package apr.optimization.algorithms.fminsearch;

import apr.linear.vector.IVector;
import apr.optimization.function.IMultivariableFunction;

/**
 * Class implementing the Hooke-Jeeves method
 */
public class HookeJeeves extends AbstractMultivariableOptimizationAlgorithm {

    private double delta = 0.5;

    public HookeJeeves(IMultivariableFunction f) {
        super(f);
    }

    public HookeJeeves(IMultivariableFunction f, double epsilon, double dx) {
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
                xp = xn.multiply(2).subtract(xb);
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
        for (int i = 0, n = x.getDimension(); i < n; i++) {
            double initialFx = f.valueAt(x);
            x.set(i, x.get(i) + dx);
            if (f.valueAt(x) > initialFx) {
                x.set(i, x.get(i) - 2 * dx);
                if (f.valueAt(x) > initialFx) {
                    x.set(i, x.get(i) + dx);
                }
            }
        }
        return x;
    }

    @Override
    public String getName() {
        return "Hooke Jeeves";
    }
}
