package apr.genetics.chromosomes;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractFieldChromosome<T> extends AbstractChromosome implements FieldChromosome<T> {

    protected final List<T> representation;

    protected AbstractFieldChromosome(List<T> representation) {
        this.representation = Collections.unmodifiableList(representation);
    }

    protected AbstractFieldChromosome(T[] representation) {
        this(Arrays.asList(representation));
    }

    @Override
    public int getLength() {
        return representation.size();
    }

    @Override
    public List<T> getRepresentation() {
        return representation;
    }

    @Override
    public String toString() {
        return "[" + representation.stream().map(String::valueOf).collect(Collectors.joining(", ")) + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractFieldChromosome<?> that = (AbstractFieldChromosome<?>) o;
        return representation.equals(that.representation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(representation);
    }
}
