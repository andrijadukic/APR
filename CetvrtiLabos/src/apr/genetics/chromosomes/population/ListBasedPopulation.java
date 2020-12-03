package apr.genetics.chromosomes.population;

import apr.genetics.chromosomes.Chromosome;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListBasedPopulation implements Population {

    List<Chromosome> chromosomes;
    List<Chromosome> roChromosomes;

    public ListBasedPopulation() {
        this(new ArrayList<>());
    }

    public ListBasedPopulation(Chromosome[] chromosomes) {
        this(Arrays.asList(chromosomes));
    }

    public ListBasedPopulation(List<Chromosome> chromosomes) {
        this.chromosomes = chromosomes;
        roChromosomes = Collections.unmodifiableList(chromosomes);
    }

    public ListBasedPopulation(int populationSize, Supplier<Chromosome> supplier) {
        this(Stream.generate(supplier).limit(populationSize).collect(Collectors.toList()));
    }

    @Override
    public int size() {
        return chromosomes.size();
    }

    @Override
    public Population empty() {
        return new ListBasedPopulation();
    }

    @Override
    public Population copy() {
        return new ListBasedPopulation(new ArrayList<>(getChromosomes()));
    }

    @Override
    public void addChromosome(Chromosome chromosome) {
        chromosomes.add(chromosome);
    }

    @Override
    public void removeChromosome(Chromosome chromosome) {
        chromosomes.remove(chromosome);
    }

    @Override
    public Chromosome getChromosome(int index) {
        return chromosomes.get(index);
    }

    @Override
    public void setChromosome(int index, Chromosome chromosome) {
        chromosomes.set(index, chromosome);
    }

    @Override
    public List<Chromosome> getChromosomes() {
        return roChromosomes;
    }

    @Override
    public Iterator<Chromosome> iterator() {
        return chromosomes.iterator();
    }
}
