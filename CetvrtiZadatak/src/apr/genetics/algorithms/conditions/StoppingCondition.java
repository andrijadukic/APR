package apr.genetics.algorithms.conditions;

import apr.genetics.chromosomes.population.Population;

@FunctionalInterface
public interface StoppingCondition {

    boolean isMet(Population population);

    default StoppingCondition and(StoppingCondition other) {
        return population -> isMet(population) && other.isMet(population);
    }

    default StoppingCondition or(StoppingCondition other) {
        return population -> isMet(population) || other.isMet(population);
    }

    default StoppingCondition not() {
        return population -> !isMet(population);
    }
}
