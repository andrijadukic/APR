package apr.optimization.demo;

import apr.linear.vector.IVector;
import apr.linear.vector.Vector;
import apr.optimization.algorithms.multi.ConstrainedMultivariateCostFunction;
import apr.optimization.algorithms.multi.deriv.GradientDescent;
import apr.optimization.algorithms.multi.deriv.IDifferentiableMultivariateCostFunction;
import apr.optimization.algorithms.multi.deriv.NewtonRaphson;
import apr.optimization.algorithms.multi.noderiv.*;
import apr.optimization.exceptions.MaximumIterationCountExceededException;
import apr.optimization.functions.ConstrainedMultivariateFunction;
import apr.optimization.functions.constraints.*;

public class Lab {

    public static void main(String[] args) {
//        zadatak1();
//        zadatak2();
//        zadatak3();
//        zadatak4();
        zadatak5();
    }

    private static void zadatak1() {
        var f3 = CostFunctions.f3();
        IVector startingPoint = new Vector(0., 0.);
        var gradientDescent = new GradientDescent(f3);

        System.out.println(gradientDescent.getName());

        System.out.println("Without line search:");
        test(f3, startingPoint, gradientDescent);

        System.out.println();

        gradientDescent.setComputeOptimalStep(true);
        System.out.println("With line search");
        test(f3, startingPoint, gradientDescent);
    }

    private static void zadatak2() {
        var f1 = CostFunctions.f1();
        IVector startingPoint1 = new Vector(-1.9, 2.);

        var gradientDescent1 = new GradientDescent(f1);
        gradientDescent1.setComputeOptimalStep(true);
        var newtonRaphson1 = new NewtonRaphson(f1);
        newtonRaphson1.setComputeOptimalStep(true);

        System.out.println("Function f1:");
        System.out.println(gradientDescent1.getName());
        test(f1, startingPoint1, gradientDescent1);

        System.out.println();

        System.out.println(newtonRaphson1.getName());
        test(f1, startingPoint1, newtonRaphson1);

        System.out.println();
        System.out.println();

        var f2 = CostFunctions.f2();
        IVector startingPoint2 = new Vector(0.1, 0.3);

        var gradientDescent2 = new GradientDescent(f2);
        gradientDescent2.setComputeOptimalStep(true);
        var newtonRaphson2 = new NewtonRaphson(f2);
        newtonRaphson2.setComputeOptimalStep(true);

        System.out.println("Function f2:");
        System.out.println(gradientDescent2.getName());
        test(f2, startingPoint2, gradientDescent2);

        System.out.println();

        System.out.println(newtonRaphson2.getName());
        test(f2, startingPoint2, newtonRaphson2);
    }

    private static void zadatak3() {
        ImplicitConstraint[] implicitConstraints = new ImplicitConstraint[]{
                Constraints.inequality(x -> x.get(1) - x.get(0)),
                Constraints.inequality(x -> 2 - x.get(0))};

        ExplicitConstraint[] explicitConstraints = new ExplicitConstraint[]{
                Constraints.explicit(-100., 100.),
                Constraints.explicit(-100, 100.)};

        System.out.println("Function f1:");
        var f1 = CostFunctions.f1();
        IVector startingPoint1 = new Vector(-1.9, 2.);

        var box = new BoxMethod(f1, explicitConstraints, implicitConstraints);
        test(f1, startingPoint1, box);

        System.out.println("Function f2:");
        var f2 = CostFunctions.f2();
        IVector startingPoint2 = new Vector(0.1, 0.3);

        box = new BoxMethod(f2, explicitConstraints, implicitConstraints);
        test(f2, startingPoint2, box);
    }

    private static void zadatak4() {
        InequalityConstraint[] inequalityConstraints = new InequalityConstraint[]{
                (InequalityConstraint) Constraints.inequality(x -> x.get(1) - x.get(0)),
                (InequalityConstraint) Constraints.inequality(x -> 2 - x.get(0))};

        var constrained = new ConstrainedMultivariateCostFunction(new ConstrainedMultivariateFunction(CostFunctions.f1(), inequalityConstraints));
        IVector startingPoint = new Vector(-1.9, 2.);
        var constrainedOptimizer = new HookeJeevesConstrainedOptimizer(constrained);
        System.out.println("Min: " + constrainedOptimizer.search(startingPoint));
        System.out.println("Evaluations: " + constrained.getFunctionEvaluationCount());
    }

    private static void zadatak5() {
        InequalityConstraint[] inequalityConstraints = new InequalityConstraint[]{
                (InequalityConstraint) Constraints.inequality(x -> 3 - x.get(0) - x.get(1)),
                (InequalityConstraint) Constraints.inequality(x -> 3 + 1.5 * x.get(0) - x.get(1))};
        EqualityConstraint[] equalityConstraints = new EqualityConstraint[]{(EqualityConstraint) Constraints.equality(x -> x.get(1) - 1)};

        IVector startingPoint = new Vector(5., 5.);

        var function = new ConstrainedMultivariateCostFunction(new ConstrainedMultivariateFunction(CostFunctions.f4(), equalityConstraints, inequalityConstraints));
        var optimizer = new HookeJeeves(function);
        System.out.println("Min: " + optimizer.search(new HookeJeevesInteriorPointSearch(inequalityConstraints).search(startingPoint)));
        System.out.println("Evaluations: " + function.getFunctionEvaluationCount());
    }


    private static void test(IDifferentiableMultivariateCostFunction function, IVector startingPoint, IMultivariateOptimizer algorithm) {
        try {
            System.out.println("Min: " + algorithm.search(startingPoint));
            System.out.println("Evaluations: " + function.getFunctionEvaluationCount());
            System.out.println("Gradient evaluations: " + function.getGradientEvaluationCount());
            System.out.println("Hessian evaluations: " + function.getHessianEvaluationCount());
        } catch (MaximumIterationCountExceededException e) {
            System.out.println(e.getMessage());
        } finally {
            function.reset();
        }
    }
}
