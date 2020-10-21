package apr.optimization.algorithms;

import apr.linear.vector.IVector;
import apr.linear.vector.Vector;
import apr.optimization.function.IFunction;

public class GoldenSectionSearch {

    public static final double e = 10e-6;
    public static final double K = 0.5 * (Math.sqrt(5) - 1);

    public static Interval goldenRatio(IFunction f, double h, double x0) {
        return goldenRatio(f, unimodalInterval(f, h, x0), e);
    }

    public static Interval goldenRatio(IFunction f, double h, double x0, double e) {
        return goldenRatio(f, unimodalInterval(f, h, x0), e);
    }

    public static Interval goldenRatio(IFunction f, Interval interval) {
        return goldenRatio(f, interval, e);
    }

    public static Interval goldenRatio(IFunction f, Interval interval, double e) {
        double a = interval.getStart();
        double b = interval.getEnd();

        double c = b - K * (b - a);
        double d = a + K * (b - a);

        double fc = f.valueAt(new Vector(c));
        double fd = f.valueAt(new Vector(d));

        while ((b - a) > e) {
            if (fc < fd) {
                b = d;
                d = c;
                c = b - K * (b - a);
                fd = fc;
                fc = f.valueAt(new Vector(c));
            } else {
                a = c;
                c = d;
                d = a + K * (b - a);
                fc = fd;
                fd = f.valueAt(new Vector(d));
            }
        }

        return new Interval(a, b);
    }

    public static Interval unimodalInterval(IFunction f, double h, double x0) {
        double l = x0 - h;
        double r = x0 + h;
        double m = x0;
        double fl, fm, fr;
        int step = 1;

        fm = f.valueAt(new Vector(x0));
        fl = f.valueAt(new Vector(l));
        fr = f.valueAt(new Vector(r));

        if (fm < fr && fm < fl) {
            return new Interval(l, r);
        } else if (fm > fr) {
            do {
                l = m;
                m = r;
                fm = fr;
                r = x0 + h * (step *= 2);
                fr = f.valueAt(new Vector(r));
            } while (fm > fr);
        } else {
            do {
                r = m;
                m = l;
                fm = fl;
                l = x0 - h * (step *= 2);
                fl = f.valueAt(new Vector(l));
            } while (fm > fl);
        }

        return new Interval(l, r);
    }

    public static class Interval {

        private final double start;
        private final double end;

        public Interval(double start, double end) {
            this.start = start;
            this.end = end;
        }

        public double getStart() {
            return start;
        }

        public double getEnd() {
            return end;
        }
    }
}
