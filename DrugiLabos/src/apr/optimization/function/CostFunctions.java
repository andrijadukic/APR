package apr.optimization.function;

import apr.linear.vector.Vector;

public class CostFunctions {

    public static IFunction f1() {
        return new CostFunction(new Vector(-1.9, 2), new Vector(1., 1.), x -> {
            double x1 = x.get(0);
            double x2 = x.get(1);
            return 100 * Math.pow(x2 - Math.pow(x1, 2), 2) + Math.pow(1 - x1, 2);
        });
    }

    public static IFunction f2() {
        return new CostFunction(new Vector(0.1, 0.3), new Vector(4., 2.), x -> {
            double x1 = x.get(0);
            double x2 = x.get(1);
            return Math.pow(x1 - 4, 2) + 4 * Math.pow(x2 - 2, 2);
        });
    }

    public static IFunction f3() {
        return new CostFunction(new Vector(0.0, 0.0), new Vector(1., 2.), x -> {
            double result = 0.;
            for (int i = 0, n = x.getDimension(); i < n; i++) {
                result += Math.pow(x.get(i) - i, 2);
            }
            return result;
        });
    }

    public static IFunction f4() {
        return new CostFunction(new Vector(0.0, 0.0), new Vector(1., 2.), x -> {
            double x1 = x.get(0);
            double x2 = x.get(1);
            return Math.abs((x1 - x2) * (x1 + x2)) + Math.sqrt(x1 * x1 + x2 * x2);
        });
    }

    public static IFunction f6() {
        return new CostFunction(new Vector(0.0, 0.0), new Vector(1., 2.), x -> {
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
