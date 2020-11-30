package apr.optimization.algorithms.multi.noderiv;

import apr.linear.vector.IVector;
import apr.optimization.algorithms.multi.ConstrainedMultivariateCostFunction;
import apr.optimization.algorithms.multi.IMultivariateCostFunction;

/**
 * Implementation of a constrained optimizer using Hooke-Jeeves optimizer
 */
public class HookeJeevesConstrainedOptimizer extends AbstractConstrainedOptimizer {

    private double delta = DEFAULT_DELTA;

    private static final double DEFAULT_DELTA = 0.5;

    public HookeJeevesConstrainedOptimizer(ConstrainedMultivariateCostFunction function) {
        super(function);
    }

    public HookeJeevesConstrainedOptimizer(ConstrainedMultivariateCostFunction function, double coefficient, double epsilon, int divergenceLimit, double delta) {
        super(function, coefficient, epsilon, divergenceLimit);
        this.delta = delta;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    @Override
    protected IVector argMin(IMultivariateCostFunction function, IVector x0) {
        return new HookeJeeves(function, epsilon, delta).search(x0);
    }

    @Override
    public String getName() {
        return "Hooke-Jeeves constrained optimizer";
    }
}
