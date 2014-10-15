package me.hfox.aphelion.exception;

public class CommandException extends Exception {

    private static final long serialVersionUID = 2839723992120092580L;

    public CommandException(String message) {
        super(message);
    }

    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandException(Throwable cause) {
        super(cause);
    }

}
