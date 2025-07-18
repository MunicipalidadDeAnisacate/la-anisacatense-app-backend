package sistemaDeAlumbrado.demo.exceptions;

public class IncorrectOldPasswordException extends RuntimeException {
    public IncorrectOldPasswordException() {
        super("La contraseña actual es incorrecta.");
    }
}

