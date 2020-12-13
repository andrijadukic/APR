package apr.genetics.operators.crossover.floatinpoint;

import apr.util.Interval;
import apr.util.Pair;
import apr.util.SourceOfRandomness;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BLXAlphaCrossover extends AbstractFloatingPointCrossover {

    private final double alpha;
    private final Interval interval;

    public BLXAlphaCrossover(double alpha, Interval interval) {
        this.alpha = alpha;
        this.interval = interval;
    }

    @Override
    protected Pair<List<Double>, List<Double>> mate(List<Double> first, List<Double> second) {
        int length = first.size();
        List<Double> firstChild = new ArrayList<>(length);
        List<Double> secondChild = new ArrayList<>(length);

        Random random = SourceOfRandomness.getSource();
        for (int i = 0; i < length; i++) {
            double max = Math.max(first.get(i), second.get(i));
            double min = Math.min(first.get(i), second.get(i));
            double d = max - min;
            double lb = min - d * alpha;
            double ub = max + d * alpha;
            firstChild.add(adjust(random.nextDouble() * (ub - lb) + lb));
            secondChild.add(adjust(random.nextDouble() * (ub - lb) + lb));
        }

        return new Pair<>(firstChild, secondChild);
    }

    private double adjust(double candidate) {
        double lb = interval.start();
        double ub = interval.end();
        if (candidate < lb) return lb;
        return Math.min(candidate, ub);
    }
}
