package apr.genetics.chromosomes.population;

import apr.genetics.chromosomes.Chromosome;
import apr.genetics.exceptions.EmptyPopulationException;
import apr.util.Copyable;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface Population extends Iterable<Chromosome>, Copyable<Population> {

    int size();

    Population empty();

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
}
