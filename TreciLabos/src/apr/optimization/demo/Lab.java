package apr.optimization.demo;

import apr.linear.vector.IVector;
import apr.linear.vector.Vector;
import apr.optimization.algorithms.multi.gradient.GradientDescent;
import apr.optimization.algorithms.multi.gradient.IDifferentiableMultivariateCostFunction;
import apr.optimization.algorithms.multi.gradient.NewtonRaphson;
import apr.optimization.algorithms.multi.noderiv.IMultivariateOptimizer;
import apr.optimization.algorithms.util.CostFunctions;
import apr.optimization.exceptions.MaximumIterationCountExceededException;

public class Lab {

    public static void main(String[] args) {
//        zadatak1();
        zadatak2();
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

        var f2 = CostFunctions.f2();
        IVector startingPoint2 = new Vector(0.1, 0.3);

        var gradientDescent1 = new GradientDescent(f1);
        gradientDescent1.setComputeOptimalStep(true);
        var gradientDescent2 = new GradientDescent(f2);
        gradientDescent1.setComputeOptimalStep(true);

        var newtonRaphson1 = new NewtonRaphson(f1);
        newtonRaphson1.setComputeOptimalStep(true);
        var newtonRaphson2 = new NewtonRaphson(f1);
        newtonRaphson2.setComputeOptimalStep(true);

        System.out.println("Function f1:");
        System.out.println(gradientDescent1.getName());
        test(f1, startingPoint1, gradientDescent1);

        System.out.println();

        System.out.println(newtonRaphson1.getName());
        test(f1, startingPoint1, newtonRaphson1);

        System.out.println();
        System.out.println();

        System.out.println("Function f2:");
        System.out.println(gradientDescent2.getName());
        test(f2, startingPoint2, gradientDescent2);

        System.out.println();

        System.out.println(newtonRaphson2.getName());
        test(f2, startingPoint2, newtonRaphson2);
    }

    private static void test(IDifferentiableMultivariateCostFunction function, IVector startingPoint, IMultivariateOptimizer algorithm) {
        try {
            System.out.println("Min: " + algorithm.search(startingPoint));
            System.out.println("Evaluations: " + function.getFunctionEvaluationCount());
            System.out.println("Gradient evaluations: " + function.getGradientEvaluationCount());
            System.out.println("Hessian evaluations: " + function.getHessianEvaluationCount());
        } catch (MaximumIterationCountExceededException e) {
            System.out.println("Maximum iteration count exceeded");
        } finally {
            function.reset();
        }
    }
}
