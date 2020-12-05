package apr.genetics.chromosomes.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface ChromosomeDecoder<R, V> {

    V decode(R value);

    default List<V> decode(List<R> representation) {
        return representation.stream().map(this::decode).collect(Collectors.toUnmodifiableList());
    }

    R instance();

    default List<R> instance(int n) {
        return Stream.generate(this::instance).limit(n).collect(Collectors.toUnmodifiableList());
    }
}
