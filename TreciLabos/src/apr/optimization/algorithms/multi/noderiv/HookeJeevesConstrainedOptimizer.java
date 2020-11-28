package apr.optimization.algorithms.multi.noderiv;

import apr.linear.vector.IVector;
import apr.optimization.algorithms.multi.ConstrainedMultivariateCostFunction;
import apr.optimization.algorithms.multi.IMultivariateCostFunction;

public class HookeJeevesConstrainedOptimizer extends ConstrainedOptimizer {

    private double delta;

    private static final double DEFAULT_DELTA = 0.5;

    public HookeJeevesConstrainedOptimizer(ConstrainedMultivariateCostFunction f) {
        super(f);
        delta = DEFAULT_DELTA;
    }

    public HookeJeevesConstrainedOptimizer(ConstrainedMultivariateCostFunction f, double coefficient, double epsilon, double delta) {
        super(f, epsilon, coefficient);
        this.delta = delta;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    @Override
    protected IVector argMin(IMultivariateCostFunction f, IVector x0) {
        return new HookeJeeves(f, epsilon, delta).search(x0);
    }

    @Override
    public String getName() {
        return "Hooke-Jeeves constrained optimizer";
    }
}
