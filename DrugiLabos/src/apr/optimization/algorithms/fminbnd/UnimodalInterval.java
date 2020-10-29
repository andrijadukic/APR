package apr.optimization.algorithms.fminbnd;

import apr.optimization.function.ISingleVariableFunction;
import apr.optimization.util.Interval;

/**
 * Class implementing unimodal interval search
 */
public class UnimodalInterval {

    public static Interval findInterval(ISingleVariableFunction f, double h, double x0) {
        double l = x0 - h;
        double m = x0;
        double r = x0 + h;
        double fl, fm, fr;
        int step = 1;

        fl = f.valueAt(l);
        fm = f.valueAt(m);
        fr = f.valueAt(r);

        if (fm < fr && fm < fl) return new Interval(l, r);

        if (fm > fr) {
            do {
                l = m;
                m = r;
                fm = fr;
                r = x0 + h * (step *= 2);
                fr = f.valueAt(r);
            } while (fm > fr);
        } else {
            do {
                r = m;
                m = l;
                fm = fl;
                l = x0 - h * (step *= 2);
                fl = f.valueAt(l);
            } while (fm > fl);
        }

        return new Interval(l, r);
    }
}
