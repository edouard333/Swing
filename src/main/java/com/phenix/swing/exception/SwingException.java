package com.phenix.swing.exception;

import jakarta.validation.constraints.NotNull;

/**
 * Exception de base pour toutes les erreurs survenant dans le projet.<br>
 * <br>
 * Toutes les exceptions spécifiques doivent hériter de cette classe.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public class SwingException extends Exception {

    /**
     * Construit une {@link SwingException}.
     */
    public SwingException() {
        super();
    }

    /**
     * Construit une {@link SwingException} avec un message.
     *
     * @param message Le message.
     */
    public SwingException(String message) {
        super(message);
    }

    /**
     * Construit une {@link SwingException} avec un message et une cause.
     *
     * @param message Le message.
     * @param cause La cause.
     */
    public SwingException(String message, @NotNull Throwable cause) {
        super(message, cause);
    }
}
