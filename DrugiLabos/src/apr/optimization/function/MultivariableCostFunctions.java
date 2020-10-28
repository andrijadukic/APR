package apr.optimization.function;

/**
 * Utility class defining several cost functions through static factory methods
 */
public class MultivariableCostFunctions {

    /**
     * f(x) = 100 * (x2 - x1^2)^2 + (1 - x1)^2
     *
     * @return cost function f1
     */
    public static IMultivariableCostFunction f1() {
        return new MultivariableCostFunction(x -> {
            double x1 = x.get(0);
            double x2 = x.get(1);
            return 100 * Math.pow(x2 - Math.pow(x1, 2), 2) + Math.pow(1 - x1, 2);
        });
    }

    /**
     * f(x) = (x1 - 4)^2 + 4 * (x2 - 2)^2
     *
     * @return cost function f2
     */
    public static IMultivariableCostFunction f2() {
        return new MultivariableCostFunction(x -> {
            double x1 = x.get(0);
            double x2 = x.get(1);
            return Math.pow(x1 - 4, 2) + 4 * Math.pow(x2 - 2, 2);
        });
    }

    /**
     * f(x) = sum((xi - i)^2)
     *
     * @return cost function f3
     */
    public static IMultivariableCostFunction f3() {
        return new MultivariableCostFunction(x -> {
            double result = 0.;
            for (int i = 0, n = x.getDimension(); i < n; i++) {
                result += Math.pow(x.get(i) - (i + 1), 2);
            }
            return result;
        });
    }

    /**
     * f(x) = abs((x1 - x2) * (x1 + x2)) + sqrt(x1^2 + x2^2)
     *
     * @return cost function f4
     */
    public static IMultivariableCostFunction f4() {
        return new MultivariableCostFunction(x -> {
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
    public static IMultivariableCostFunction f6() {
        return new MultivariableCostFunction(x -> {
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
