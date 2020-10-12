package apr.matrix.logger;

import java.io.OutputStream;

public class MatrixOperationsLogger implements Logger {

    private final OutputStream outputStream;

    public MatrixOperationsLogger(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void log(String message) {
    }
}
