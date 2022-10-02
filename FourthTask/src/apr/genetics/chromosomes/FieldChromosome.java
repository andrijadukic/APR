package apr.genetics.chromosomes;

import java.util.List;

public interface FieldChromosome<T> extends Chromosome {

    int getLength();

    List<T> getRepresentation();

    FieldChromosome<T> newInstance(List<T> representation);
}
