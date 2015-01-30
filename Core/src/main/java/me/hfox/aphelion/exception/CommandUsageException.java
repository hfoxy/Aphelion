package me.hfox.aphelion.exception;

public class CommandUsageException extends CommandException {

    private static final long serialVersionUID = 6822127098156437695L;

    public CommandUsageException() {
        this(null);
    }

    public CommandUsageException(String message) {
        super(message == null || message.length() < 1 ? "Illegal command usage" : message);
    }

}
