package backend.proyect_doctic_is1.Exception;

public class FileNotFoundExceptionCustom extends RuntimeException {

    public FileNotFoundExceptionCustom(String message) {
        super(message);
    }

    public FileNotFoundExceptionCustom(String message, Throwable cause) {
        super(message, cause);
    }
}
