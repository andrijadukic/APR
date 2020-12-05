package apr.genetics.operators.crossover.floatinpoint;

import apr.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SingleArithmeticCrossover extends AbstractArithmeticCrossover {

    public SingleArithmeticCrossover() {
    }

    public SingleArithmeticCrossover(double alpha) {
        super(alpha);
    }

    @Override
    protected Pair<List<Double>, List<Double>> mate(List<Double> first, List<Double> second) {
        int length = first.size();
        List<Double> firstChild = new ArrayList<>(first);
        List<Double> secondChild = new ArrayList<>(second);

        Random random = ThreadLocalRandom.current();
        int rind = random.nextInt(length);
        double val = alpha * (first.get(rind) + second.get(rind));
        firstChild.set(rind, val);
        secondChild.set(rind, val);

        return new Pair<>(firstChild, secondChild);
    }
}
