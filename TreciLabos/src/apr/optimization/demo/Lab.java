package apr.optimization.demo;

import apr.linear.vector.IVector;
import apr.linear.vector.Vector;
import apr.optimization.algorithms.multi.ConstrainedMultivariateCostFunction;
import apr.optimization.algorithms.multi.IMultivariateCostFunction;
import apr.optimization.algorithms.multi.MultivariateCostFunction;
import apr.optimization.algorithms.multi.deriv.GradientDescent;
import apr.optimization.algorithms.multi.deriv.IDifferentiableMultivariateCostFunction;
import apr.optimization.algorithms.multi.deriv.NewtonRaphson;
import apr.optimization.algorithms.multi.noderiv.*;
import apr.optimization.exceptions.DivergenceLimitReachedException;
import apr.optimization.functions.ConstrainedMultivariateFunction;
import apr.optimization.functions.constraints.*;

public class Lab {

    public static void main(String[] args) {
        zadatak1();
        System.out.println();

        zadatak2();
        System.out.println();

        zadatak3();
        System.out.println();

        zadatak4();
        System.out.println();

        zadatak5();
    }

    private static void zadatak1() {
        System.out.println("Zadatak 1");
        System.out.println("---------");

        var f3 = CostFunctions.f3();
        IVector startingPoint = new Vector(0., 0.);
        var gradientDescent = new GradientDescent(f3);

        System.out.println("Without line search:");
        gradientDescent.setComputeOptimalStep(false);
        test(f3, startingPoint, gradientDescent);

        System.out.println();

        System.out.println("With line search");
        gradientDescent.setComputeOptimalStep(true);
        test(f3, startingPoint, gradientDescent);
    }

    private static void zadatak2() {
        System.out.println("Zadatak 2");
        System.out.println("---------");

        System.out.println("Function f1:");
        System.out.println();
        var f1 = CostFunctions.f1();
        IVector startingPoint1 = new Vector(-1.9, 2.);
        test(f1, startingPoint1, new GradientDescent(f1));
        System.out.println();
        test(f1, startingPoint1, new NewtonRaphson(f1));

        System.out.println();

        System.out.println("Function f2:");
        System.out.println();
        var f2 = CostFunctions.f2();
        IVector startingPoint2 = new Vector(0.1, 0.3);
        test(f2, startingPoint2, new GradientDescent(f2));
        System.out.println();
        test(f2, startingPoint2, new NewtonRaphson(f2));
    }

    private static void zadatak3() {
        System.out.println("Zadatak 3");
        System.out.println("---------");

        var implicitConstraints = new ImplicitConstraint[]{
                Constraints.inequality(x -> x.get(1) - x.get(0)),
                Constraints.inequality(x -> 2 - x.get(0))};

        var explicitConstraints = new ExplicitConstraint[]{
                Constraints.explicit(-100., 100.),
                Constraints.explicit(-100, 100.)};

        System.out.println("Function f1:");
        System.out.println();
        var f1 = CostFunctions.f1();
        IVector startingPoint1 = new Vector(-1.9, 2.);
        var box = new BoxMethod(f1, explicitConstraints, implicitConstraints);
        test(f1, startingPoint1, box);
        IVector newStartingPoint1 = new Vector(0., 1.);
        test(f1, newStartingPoint1, box);

        System.out.println();

        System.out.println("Function f2:");
        System.out.println();
        var f2 = CostFunctions.f2();
        IVector startingPoint2 = new Vector(0.1, 0.3);
        test(f2, startingPoint2, new BoxMethod(f2, explicitConstraints, implicitConstraints));
    }

    private static void zadatak4() {
        System.out.println("Zadatak 4");
        System.out.println("---------");

        var inequalityConstraints = new InequalityConstraint[]{
                Constraints.inequality(x -> x.get(1) - x.get(0)),
                Constraints.inequality(x -> 2 - x.get(0))};

        System.out.println("Function f1:");
        System.out.println();
        var constrained1 = new ConstrainedMultivariateCostFunction(new ConstrainedMultivariateFunction(CostFunctions.f1(), inequalityConstraints));
        IVector startingPoint1 = new Vector(-1.9, 2.);
        var constrainedOptimizer = new HookeJeevesConstrainedOptimizer(constrained1);
        test(constrained1, startingPoint1, constrainedOptimizer);
        IVector newStartingPoint1 = new Vector(1, 1.5);
        test(constrained1, newStartingPoint1, constrainedOptimizer);

        System.out.println();

        System.out.println("Function f2:");
        System.out.println();
        var constrained2 = new ConstrainedMultivariateCostFunction(new ConstrainedMultivariateFunction(CostFunctions.f2(), inequalityConstraints));
        IVector startingPoint2 = new Vector(0.1, 0.3);
        test(constrained2, startingPoint2, new HookeJeevesConstrainedOptimizer(constrained2));
    }

    private static void zadatak5() {
        System.out.println("Zadatak 5");
        System.out.println("---------");

        var inequalityConstraints = new InequalityConstraint[]{
                Constraints.inequality(x -> 3 - x.get(0) - x.get(1)),
                Constraints.inequality(x -> 3 + 1.5 * x.get(0) - x.get(1))};
        var equalityConstraints = new EqualityConstraint[]{Constraints.equality(x -> x.get(1) - 1)};

        IVector startingPoint = new Vector(5., 5.);
        IVector newStartingPoint = new HookeJeeves(new MultivariateCostFunction(Constraints.sum(inequalityConstraints))).search(startingPoint);

        var function = new ConstrainedMultivariateCostFunction(new ConstrainedMultivariateFunction(CostFunctions.f4(), equalityConstraints, inequalityConstraints));
        test(function, newStartingPoint, new HookeJeevesConstrainedOptimizer(function));
    }

    private static void test(IDifferentiableMultivariateCostFunction function, IVector startingPoint, IMultivariateOptimizer algorithm) {
        try {
            System.out.println("Algorithm: " + algorithm.getName());
            System.out.println("Min: [" + algorithm.search(startingPoint) + "]");
            System.out.println("Function evaluations: " + function.getFunctionEvaluationCount());
            System.out.println("Gradient evaluations: " + function.getGradientEvaluationCount());
            System.out.println("Hessian evaluations: " + function.getHessianEvaluationCount());
        } catch (DivergenceLimitReachedException e) {
            System.out.println(e.getMessage());
        } finally {
            function.reset();
        }
    }

    private static void test(IMultivariateCostFunction function, IVector startingPoint, IMultivariateOptimizer algorithm) {
        try {
            System.out.println("Algorithm: " + algorithm.getName());
            System.out.println("Min: [" + algorithm.search(startingPoint) + "]");
            System.out.println("Function evaluations: " + function.getFunctionEvaluationCount());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            function.reset();
        }
    }
}
