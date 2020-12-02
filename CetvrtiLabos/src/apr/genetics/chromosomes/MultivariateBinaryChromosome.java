package apr.genetics.chromosomes;

import java.util.List;

public abstract class MultivariateBinaryChromosome extends AbstractFieldChromosome<List<Boolean>> {

    protected MultivariateBinaryChromosome(List<List<Boolean>> representation) {
        super(representation);
    }
}
