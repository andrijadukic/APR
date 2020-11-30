package apr.genetics.operators.mutation;

public class BinarySimpleMutation extends SimpleMutation<Boolean>{

    public BinarySimpleMutation(double pm) {
        super(pm);
    }

    @Override
    protected Boolean mutatedValue(Boolean original) {
        return !original;
    }
}
