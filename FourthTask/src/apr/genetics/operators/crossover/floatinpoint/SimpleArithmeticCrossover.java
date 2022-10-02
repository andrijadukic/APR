package apr.genetics.operators.crossover.floatinpoint;

import apr.util.Pair;
import apr.util.SourceOfRandomness;

import java.util.ArrayList;
import java.util.List;

public class SimpleArithmeticCrossover extends AbstractArithmeticCrossover {

    public SimpleArithmeticCrossover() {
    }

    public SimpleArithmeticCrossover(double alpha) {
        super(alpha);
    }

    @Override
    protected Pair<List<Double>, List<Double>> mate(List<Double> first, List<Double> second) {
        int length = first.size();
        List<Double> firstChild = new ArrayList<>(length);
        List<Double> secondChild = new ArrayList<>(length);

        int rind = SourceOfRandomness.getSource().nextInt(length);

        for (int i = 0; i < rind; i++) {
            firstChild.add(first.get(i));
            secondChild.add(second.get(i));
        }
        for (int i = rind; i < length; i++) {
            double val = alpha * (first.get(i) + second.get(i));
            firstChild.add(val);
            secondChild.add(val);
        }

        return new Pair<>(firstChild, secondChild);
    }
}

