package apr.genetics.chromosomes;

import java.util.List;

public abstract class SimpleBinaryChromosome extends AbstractFieldChromosome<Boolean> {

    protected SimpleBinaryChromosome(List<Boolean> representation) {
        super(representation);
    }

    protected SimpleBinaryChromosome(Boolean[] representation) {
        super(representation);
    }
}
