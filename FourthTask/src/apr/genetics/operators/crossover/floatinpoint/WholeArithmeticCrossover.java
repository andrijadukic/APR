package apr.genetics.operators.crossover.floatinpoint;

import apr.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class WholeArithmeticCrossover extends AbstractArithmeticCrossover {

    public WholeArithmeticCrossover() {
    }

    public WholeArithmeticCrossover(double alpha) {
        super(alpha);
    }

    @Override
    protected Pair<List<Double>, List<Double>> mate(List<Double> first, List<Double> second) {
        int length = first.size();
        List<Double> firstChild = new ArrayList<>(length);
        List<Double> secondChild = new ArrayList<>(length);

        for (int i = 0; i < length; i++) {
            double val = alpha * (first.get(i) + second.get(i));
            firstChild.add(val);
            secondChild.add(val);
        }

        return new Pair<>(firstChild, secondChild);
    }
}
