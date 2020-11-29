package apr.optimization.algorithms.multi.noderiv;

import apr.linear.vector.IVector;
import apr.optimization.algorithms.multi.IMultivariateCostFunction;
import apr.optimization.functions.constraints.InequalityConstraint;

/**
 * Implementation of an interior point search algorithm using Hooke-Jeeves optimizer
 */
public class HookeJeevesInteriorPointSearch extends AbstractInteriorPointSearch {

    private double delta = DEFAULT_DELTA;

    private static final double DEFAULT_DELTA = 0.5;

    public HookeJeevesInteriorPointSearch(InequalityConstraint... inequalityConstraints) {
        super(inequalityConstraints);
    }

    public HookeJeevesInteriorPointSearch(InequalityConstraint[] inequalityConstraints, double epsilon, double delta) {
        super(inequalityConstraints, epsilon);
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
        return "Hooke-Jeeves interior point search";
    }
}
