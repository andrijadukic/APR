package apr.optimization.algorithms;

import apr.linear.matrix.IMatrix;
import apr.linear.vector.IVector;
import apr.optimization.function.IFunction;

public class NelderMeadMethod {

    public static double epsilon = 10e-6;

    public static double alpha = 1.0;
    public static double beta = 0.5;
    public static double gamma = 2.0;

    public static double sigma = 0.5;
    public static double step = 1;

    public static IMatrix simplex(IFunction f, IVector x0) {
        return null;
    }

    private static IMatrix reflection(IMatrix xc, IMatrix xh) {
        return xc.multiply(1 + alpha).subtract(xh.multiply(alpha));
    }

    private static IMatrix expansion(IMatrix xc, IMatrix xh) {
        return xc.multiply(1 - gamma).subtract(xh.multiply(gamma));
    }

    private static IMatrix contraction(IMatrix xc, IMatrix xh) {
        return xc.multiply(1 - beta).add(xh.multiply(beta));
    }
}
