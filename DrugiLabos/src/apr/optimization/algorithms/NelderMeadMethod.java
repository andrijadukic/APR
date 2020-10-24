package apr.optimization.algorithms;

import apr.linear.vector.IVector;
import apr.optimization.function.IFunction;


public class NelderMeadMethod {

    public static double epsilon = 10e-6;

    public static double alpha = 1.0;
    public static double beta = 0.5;
    public static double gamma = 2.0;

    public static double sigma = 0.5;
    public static double step = 1;

    public static IVector simplex(IFunction f, IVector x0) {
        IVector[] simplex = buildInitialSimplex(x0);

        IVector xc;
        do {
            Pair argMaxMin = maxMinIndex(f, simplex);
            int h = argMaxMin.first;
            int l = argMaxMin.second;
            IVector xh = simplex[h];
            IVector xl = simplex[l];
            double min = f.valueAt(xl);
            double max = f.valueAt(xh);

            xc = centroid(f, simplex, xh);
            IVector xr = reflection(xc, xh);

            if (f.valueAt(xr) < min) {
                IVector xe = expansion(xc, xr);
                if (f.valueAt(xe) < min) {
                    simplex[h] = xe;
                } else {
                    simplex[h] = xr;
                }
            } else {
                double fxr = f.valueAt(xr);
                boolean isConditionMet = true;
                for (int i = 0, n = simplex.length; i < n; i++) {
                    if (i == h) continue;
                    if (fxr < f.valueAt(simplex[i])) {
                        isConditionMet = false;
                        break;
                    }
                }
                if (isConditionMet) {
                    if (fxr < max) {
                        simplex[h] = xr;
                    }
                    IVector xk = contraction(xc, simplex[h]);
                    if (f.valueAt(xk) < f.valueAt(simplex[h])) {
                        simplex[h] = xk;
                    } else {
                        for (int i = 0, n = simplex.length; i < n; i++) {
                            if (i == l) continue;
                            simplex[i] = simplex[i].multiply(sigma).add(xl.multiply(sigma));
                        }
                    }
                } else {
                    simplex[h] = xr;
                }
            }
        } while (isStopCriteriaMet(f, simplex, xc));

        return xc;
    }

    private static IVector[] buildInitialSimplex(IVector x0) {
        int dimension = x0.getDimension();

        IVector[] simplex = new IVector[dimension];
        for (int i = 0; i < dimension; i++) {
            IVector point = x0.copy();
            point.set(i, x0.get(i) + step);
            simplex[i] = point;
        }
        return simplex;
    }

    private static Pair maxMinIndex(IFunction f, IVector[] simplex) {
        double maxValue, minValue;
        int maxIndex, minIndex;
        maxValue = minValue = f.valueAt(simplex[0]);
        maxIndex = minIndex = 0;

        for (int i = 1, n = simplex.length; i < n; i++) {
            double temp = f.valueAt(simplex[i]);
            if (temp > maxValue) {
                maxValue = temp;
                maxIndex = i;
            } else if (temp < minValue) {
                minValue = temp;
                minIndex = i;
            }
        }

        return new Pair(maxIndex, minIndex);
    }

    private static IVector centroid(IFunction f, IVector[] simplex, IVector xh) {
        int n = xh.getDimension();
        IVector centroid = xh.newInstance(n);

        for (IVector point : simplex) {
            if (point.equals(xh)) continue;
            centroid = centroid.add(point);
        }

        return n == 1 ? centroid : centroid.multiply((double) 1 / (n - 1));
    }

    private static IVector reflection(IVector xc, IVector xh) {
        return xc.multiply(1 + alpha).subtract(xh.multiply(alpha));
    }

    private static IVector contraction(IVector xc, IVector xh) {
        return xc.multiply(1 - beta).add(xh.multiply(beta));
    }

    private static IVector expansion(IVector xc, IVector xr) {
        return xc.multiply(1 - gamma).add(xr.multiply(gamma));
    }

    private static boolean isStopCriteriaMet(IFunction f, IVector[] simplex, IVector centroid) {
        double val = 0;
        double fxc = f.valueAt(centroid);
        for (IVector point : simplex) {
            val += Math.pow(f.valueAt(point) - fxc, 2);
        }
        return Math.sqrt(val / simplex.length) <= epsilon;
    }

    private static record Pair(int first, int second) {
    }
}
