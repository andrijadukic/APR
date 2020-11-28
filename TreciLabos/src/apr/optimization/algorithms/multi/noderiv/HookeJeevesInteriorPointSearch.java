package apr.optimization.algorithms.multi.noderiv;

import apr.linear.vector.IVector;
import apr.optimization.algorithms.multi.IMultivariateCostFunction;
import apr.optimization.functions.constraints.InequalityConstraint;

public class HookeJeevesInteriorPointSearch extends InteriorPointSearch {

    private double delta;

    private static final double DEFAULT_DELTA = 0.5;

    public HookeJeevesInteriorPointSearch(InequalityConstraint[] inequalityConstraints) {
        super(inequalityConstraints);
        delta = DEFAULT_DELTA;
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
    protected IVector argMin(IMultivariateCostFunction f, IVector x0) {
        return new HookeJeeves(f, epsilon, delta).search(x0);
    }

    @Override
    public String getName() {
        return "Hooke-Jeeves interior point search";
    }
}
