package apr.genetics.operators.crossover.floatinpoint;

import apr.util.Pair;
import apr.util.SourceOfRandomness;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulatedBinaryCrossover extends AbstractFloatingPointCrossover {

    @Override
    protected Pair<List<Double>, List<Double>> mate(List<Double> first, List<Double> second) {
        int length = first.size();

        List<Double> firstChild = new ArrayList<>(length);
        List<Double> secondChild = new ArrayList<>(length);

        Random random = SourceOfRandomness.getSource();
        for (int i = 0; i < length; i++) {
            double a = first.get(i);
            double b = second.get(i);
            double r1 = random.nextDouble();
            double r2 = random.nextDouble();
            firstChild.add(r1 * a + (1 - r1) * b);
            secondChild.add(r2 * a + (1 - r2) * b);
        }

        return new Pair<>(firstChild, secondChild);
    }
}
