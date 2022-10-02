package apr.genetics.algorithms.util;

import apr.genetics.chromosomes.Chromosome;
import apr.genetics.chromosomes.population.Population;

public record IntermediateResult(int iteration, Chromosome fittest, Population current) {
}
