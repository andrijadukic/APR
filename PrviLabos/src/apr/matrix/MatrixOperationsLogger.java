package apr.matrix;

import java.io.OutputStream;

public class MatrixOperationsLogger implements ILogger {

    private final OutputStream outputStream;

    public MatrixOperationsLogger(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void log(String message) {
    }
}
