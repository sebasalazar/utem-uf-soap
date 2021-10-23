package cl.sebastian.indicadores.exception;

/**
 *
 * @author seba
 */
public class IndicatorException extends RuntimeException {

    public IndicatorException() {
        super("Ha ocurrido un error desconocido");
    }

    public IndicatorException(String message) {
        super(message);
    }

    public IndicatorException(String messege, Throwable cause) {
        super(messege, cause);
    }

    public IndicatorException(Throwable cause) {
        super(cause);
    }

}
