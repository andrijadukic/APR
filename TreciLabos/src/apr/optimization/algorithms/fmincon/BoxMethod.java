package apr.optimization.algorithms.fmincon;

import apr.linear.vector.IVector;
import apr.optimization.algorithms.fmincon.constraints.ExplicitConstraint;
import apr.optimization.algorithms.fmincon.constraints.ImplicitConstraint;
import apr.optimization.algorithms.fminsearch.SimplexMethod;
import apr.optimization.functions.IMultivariableCostFunction;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class BoxMethod extends SimplexMethod {

    private final ExplicitConstraint explicitConstraint;
    private final ImplicitConstraint[] implicitConstraints;

    public BoxMethod(IMultivariableCostFunction f, ExplicitConstraint explicitConstraint, ImplicitConstraint[] implicitConstraints) {
        super(f);
        this.explicitConstraint = explicitConstraint;
        this.implicitConstraints = implicitConstraints;
    }

    public BoxMethod(IMultivariableCostFunction f, double epsilon, ExplicitConstraint explicitConstraint, ImplicitConstraint[] implicitConstraints) {
        super(f, epsilon);
        this.explicitConstraint = explicitConstraint;
        this.implicitConstraints = implicitConstraints;
    }

    @Override
    public IVector search(IVector x0) {
        return null;
    }

    private IVector[] initialSimplex(IVector x0) {
        int n = x0.getDimension() * 2;
        IVector[] simplex = new IVector[n];
        simplex[0] = x0.copy();
        Random random = ThreadLocalRandom.current();
        for (int i = 1; i < n; i++) {
        }
        return simplex;
    }

    @Override
    public String getName() {
        return "Box method";
    }
}
