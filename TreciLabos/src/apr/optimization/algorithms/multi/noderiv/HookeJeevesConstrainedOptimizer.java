package apr.optimization.algorithms.multi.noderiv;

import apr.linear.vector.IVector;
import apr.optimization.algorithms.multi.ConstrainedMultivariateCostFunction;

public class HookeJeevesConstrainedOptimizer extends ConstrainedOptimizer {

    private double delta;

    private static final double DEFAULT_DELTA = 0.5;

    public HookeJeevesConstrainedOptimizer(ConstrainedMultivariateCostFunction f) {
        super(f);
    }

    public HookeJeevesConstrainedOptimizer(ConstrainedMultivariateCostFunction f, double epsilon, double coefficient) {
        super(f, epsilon, coefficient);
    }

    public HookeJeevesConstrainedOptimizer(ConstrainedMultivariateCostFunction f, double coefficient, double epsilon, double delta) {
        super(f);
        this.delta = delta;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    @Override
    protected IVector argMin(IVector x0) {
        return new HookeJeeves(f, epsilon, delta).search(x0);
    }

    @Override
    public String getName() {
        return "Hooke-Jeeves constrained optimizer";
    }
}
