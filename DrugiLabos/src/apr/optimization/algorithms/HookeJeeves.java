package apr.optimization.algorithms;

import apr.linear.vector.IVector;
import apr.optimization.function.IFunction;

public class HookeJeeves extends AbstractOptimizationAlgorithm {

    private double delta = 0.5;

    public HookeJeeves(IFunction f) {
        super(f);
    }

    public HookeJeeves(IFunction f, double epsilon, double dx) {
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

        double dx = delta;
        while (dx >= epsilon) {
            IVector xn = explore(xp, dx);
            if (f.valueAt(xn) < f.valueAt(xb)) {
                xp = xn.multiply(2).subtract(xb);
                xb = xn;
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
            double oldValue = f.valueAt(x);
            x.set(i, x.get(i) + dx);
            if (f.valueAt(x) > oldValue) {
                x.set(i, x.get(i) - 2 * dx);
                if (f.valueAt(x) > oldValue) {
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
