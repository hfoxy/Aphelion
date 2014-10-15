package me.hfox.aphelion.exception;

public class CommandUsageException extends CommandException {

    private static final long serialVersionUID = 6822127098156437695L;

    public CommandUsageException() {
        super("Illegal command usage");
    }

    public CommandUsageException(String message) {
        super(message);
    }

}
