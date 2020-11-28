package apr.optimization.algorithms.util;

import apr.linear.matrix.Matrix;
import apr.linear.vector.Vector;
import apr.optimization.algorithms.multi.IMultivariateCostFunction;
import apr.optimization.algorithms.multi.MultivariateCostFunction;
import apr.optimization.algorithms.multi.gradient.DifferentiableMultivariateCostFunction;
import apr.optimization.algorithms.multi.gradient.IDifferentiableMultivariateCostFunction;

/**
 * Utility class defining several cost functions through static factory methods
 */
public class CostFunctions {

    /**
     * f(x) = 100 * (x2 - x1^2)^2 + (1 - x1)^2
     *
     * @return cost function f1
     */
    public static DifferentiableMultivariateCostFunction f1() {
        return new DifferentiableMultivariateCostFunction(
                x -> {
                    double x1 = x.get(0);
                    double x2 = x.get(1);
                    return 100 * Math.pow(x2 - Math.pow(x1, 2), 2) + Math.pow(1 - x1, 2);
                },
                x -> {
                    double x1 = x.get(0);
                    double x2 = x.get(1);
                    return new Vector(
                            -400 * x1 * x2 + 400 * Math.pow(x1, 3) - 2 + 2 * x1,
                            200 * x2 - 200 * Math.pow(x1, 2));
                },
                x -> {
                    double x1 = x.get(0);
                    double x2 = x.get(1);
                    return new Matrix(new double[][]{
                            {-400 * x2 + 1200 * Math.pow(x1, 2) + 2, -400 * x1},
                            {-400 * x1, 200}
                    });
                }
        );
    }

    /**
     * f(x) = (x1 - 4)^2 + 4 * (x2 - 2)^2
     *
     * @return cost function f2
     */
    public static IDifferentiableMultivariateCostFunction f2() {
        return new DifferentiableMultivariateCostFunction(
                x -> {
                    double x1 = x.get(0);
                    double x2 = x.get(1);
                    return Math.pow(x1 - 4, 2) + 4 * Math.pow(x2 - 2, 2);
                },
                x -> {
                    double x1 = x.get(0);
                    double x2 = x.get(1);
                    return new Vector(2 * (x1 - 4), 8 * (x2 - 2));
                },
                x -> new Matrix(new double[][]{
                        {2, 0},
                        {0, 8}})
        );
    }

    /**
     * f(x) = (x1 - 2)^2 + 4 * (x2 + 3)^2
     *
     * @return cost function f3
     */
    public static IDifferentiableMultivariateCostFunction f3() {
        return new DifferentiableMultivariateCostFunction(
                x -> {
                    double x1 = x.get(0);
                    double x2 = x.get(1);
                    return Math.pow(x1 - 2, 2) + 4 * Math.pow(x2 + 3, 2);
                },
                x -> {
                    double x1 = x.get(0);
                    double x2 = x.get(1);
                    return new Vector(2 * (x1 - 2), 2 * (x2 + 3));
                },
                x -> new Matrix(new double[][]{
                        {2, 0},
                        {0, 2}})
        );
    }

    /**
     * f(x) = abs((x1 - x2) * (x1 + x2)) + sqrt(x1^2 + x2^2)
     *
     * @return cost function f4
     */
    public static IMultivariateCostFunction f4() {
        return new MultivariateCostFunction(x -> {
            double x1 = x.get(0);
            double x2 = x.get(1);
            return Math.abs((x1 - x2) * (x1 + x2)) + Math.sqrt(x1 * x1 + x2 * x2);
        });
    }

    /**
     * f(x) = 0.5 + (sin^2(sqrt(sum(xi^2)) - 0.5) / (1 + 0.001 * sum(xi^2))^2
     *
     * @return cost function f6
     */
    public static IMultivariateCostFunction f6() {
        return new MultivariateCostFunction(x -> {
            double sum = 0.;
            for (int i = 0, n = x.getDimension(); i < n; i++) {
                sum += Math.pow(x.get(i), 2);
            }

            double numerator = Math.pow(Math.sin(Math.sqrt(sum)), 2) - 0.5;
            double denominator = Math.pow(1 + 0.001 * sum, 2);
            return 0.5 + numerator / denominator;
        });
    }
}
