package apr.optimization.algorithms;

import apr.linear.vector.IVector;
import apr.optimization.function.IFunction;

public class HookeJeeves extends AbstractOptimizationAlgorithm {

    private double dx = 0.5;

    public HookeJeeves(IFunction f) {
        super(f);
    }

    public HookeJeeves(IFunction f, double epsilon, double dx) {
        super(f, epsilon);
        this.dx = dx;
    }

    public double getEpsilon() {
        return epsilon;
    }

    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    @Override
    public IVector search(IVector x0) {
        IVector xp = x0;
        IVector xb = x0;

        double delta = dx;
        while (delta > epsilon) {
            IVector xn = explore(f, xp);
            if (f.valueAt(xn) < f.valueAt(xb)) {
                xp = xn.multiply(2).subtract(xb);
                xb = xn;
            } else {
                delta /= 2;
                xp = xb;
            }
        }

        return xb;
    }

    @Override
    public String getName() {
        return "Hooke Jeeves";
    }

    private IVector explore(IFunction f, IVector xp) {
        IVector x = xp.copy();
        for (int i = 0, n = x.getDimension(); i < n; i++) {
            double oldValue = f.valueAt(x);
            double xi = x.get(i);
            x.set(i, xi + dx);
            if (f.valueAt(x) > oldValue) {
                x.set(i, xi - dx);
                if (f.valueAt(x) > oldValue) {
                    x.set(i, xi);
                }
            }
        }
        return x;
    }
}
