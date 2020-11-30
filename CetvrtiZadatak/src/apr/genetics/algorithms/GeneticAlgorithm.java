package apr.genetics.algorithms;

import apr.genetics.algorithms.conditions.StoppingCondition;
import apr.genetics.chromosomes.population.Population;
import apr.genetics.algorithms.util.GeneticAlgorithmSubject;

public interface GeneticAlgorithm extends GeneticAlgorithmSubject {

    Population run(Population initial, StoppingCondition condition);
}
