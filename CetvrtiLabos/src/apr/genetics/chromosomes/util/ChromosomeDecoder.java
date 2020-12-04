package apr.genetics.chromosomes.util;

import java.util.List;
import java.util.stream.Collectors;

public interface ChromosomeDecoder<R, V> {

    V decode(R value);

    default List<V> decode(List<R> representation) {
        return representation.stream().map(this::decode).collect(Collectors.toUnmodifiableList());
    }
}
