package apr.linear.util;

import java.text.DecimalFormat;

public class MatrixFormat {

    private static DecimalFormat format = new DecimalFormat("0.########");

    public static void setPrintFormat(DecimalFormat format) {
        MatrixFormat.format = format;
    }

    public static DecimalFormat getPrintFormat() {
        return format;
    }
}
