package apr.genetics.chromosomes.population;

import apr.genetics.chromosomes.Chromosome;
import apr.genetics.exceptions.EmptyPopulationException;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface Population extends Iterable<Chromosome> {

    int size();

    Population empty();

    Population copy();

    void addChromosome(Chromosome chromosome);

    void removeChromosome(Chromosome chromosome);

    Chromosome getChromosome(int index);

    void setChromosome(int index, Chromosome chromosome);

    List<Chromosome> getChromosomes();

    default Chromosome getFittest() {
        return stream().max(Chromosome::compareTo).orElseThrow(EmptyPopulationException::new);
    }

    default Stream<Chromosome> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    default Stream<Chromosome> parallelStream() {
        return StreamSupport.stream(spliterator(), true);
    }

    static Chromosome[] generate(Supplier<Chromosome> chromosomeSupplier, int n) {
        return Stream.generate(chromosomeSupplier).limit(n).toArray(Chromosome[]::new);
    }

    static int argMin(Chromosome[] chromosomes) {
        int index = 0;
        double min = chromosomes[0].getFitness();
        for (int i = 1, n = chromosomes.length; i < n; i++) {
            double temp = chromosomes[i].getFitness();
            if (temp < min) {
                min = temp;
                index = i;
            }
        }
        return index;
    }
}
