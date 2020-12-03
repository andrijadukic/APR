package apr.genetics.demo;

import apr.functions.IMultivariateFunction;

/**
 * Utility class defining several cost functions through static factory methods
 */
public class FitnessFunctions {

    /**
     * f(x) = 100 * (x2 - x1^2)^2 + (1 - x1)^2
     *
     * @return function f1
     */
    public static IMultivariateFunction f1() {
        return x -> {
            double x1 = x.get(0);
            double x2 = x.get(1);
            return 100 * Math.pow(x2 - Math.pow(x1, 2), 2) + Math.pow(1 - x1, 2);
        };
    }

    /**
     * f(x) = sum((xi - i)^2)
     *
     * @return function f3
     */
    public static IMultivariateFunction f3() {
        return x -> {
            double result = 0.;
            for (int i = 0, n = x.getDimension(); i < n; i++) {
                result += Math.pow(x.get(i) - (i + 1), 2);
            }
            return result;
        };
    }

    /**
     * f(x) = 0.5 + (sin^2(sqrt(sum(xi^2)) - 0.5) / (1 + 0.001 * sum(xi^2))^2
     *
     * @return function f6
     */
    public static IMultivariateFunction f6() {
        return x -> {
            double sum = 0.;
            for (int i = 0, n = x.getDimension(); i < n; i++) {
                sum += Math.pow(x.get(i), 2);
            }
            double numerator = Math.pow(Math.sin(Math.sqrt(sum)), 2) - 0.5;
            double denominator = Math.pow(1 + 0.001 * sum, 2);
            return 0.5 + numerator / denominator;
        };
    }

    /**
     * f(x) = (sum(xi^2))^0.25 * (1 + sin^2(50(sum(xi^2))^0.1))
     *
     * @return function f7
     */
    public static IMultivariateFunction f7() {
        return x -> {
            double sum = 0;
            for (double xi : x) {
                sum += Math.pow(xi, 2);
            }
            return Math.pow(sum, 0.25) * 1 + Math.pow(Math.sin(50 * Math.pow(sum, 0.1)), 2);
        };
    }
}
