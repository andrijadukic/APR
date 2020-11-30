package apr.genetics.algorithms.conditions;

import apr.genetics.chromosomes.population.Population;

public class StoppingConditions {

    public static StoppingCondition maxIter(final int maxIter) {
        return new StoppingCondition() {

            private int count;

            @Override
            public boolean isMet(Population population) {
                return count++ >= maxIter;
            }
        };
    }

    public static StoppingCondition precision(final double target, final double epsilon) {
        return population -> Math.abs(population.getFittest().getFitness() - target) < epsilon;
    }

    public static StoppingCondition infiniteLoop() {
        return population -> false;
    }
}
