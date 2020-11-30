package apr.genetics.operators.mutation;

public class BinarySinglePointMutation extends SinglePointMutation<Boolean> {

    @Override
    protected Boolean mutatedValue(Boolean original) {
        return !original;
    }
}
