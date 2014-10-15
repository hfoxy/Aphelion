package me.hfox.aphelion;

import me.hfox.aphelion.command.CommandHandler;
import me.hfox.aphelion.exception.CommandException;

public abstract class Aphelion<U> {

    private Class<U> user;
    protected CommandRegistration<U> registration;

    public Aphelion(Class<U> user, CommandRegistration<U> registration) {
        this.user = user;
        this.registration = registration != null ? registration : new CommandRegistration<>(this);
    }

    public Aphelion(Class<U> user) {
        this(user, null);
    }

    public Class<U> getUser() {
        return user;
    }

    public CommandRegistration<U> getRegistration() {
        return registration;
    }

    public CommandHandler<U> getCommand(String label) {
        return registration.getCommand(label);
    }

    public CommandHandler<U> invoke(U sender, String label, String[] args, CommandHandler<U> command) throws CommandException {
        if (command != null) {
            command.invoke(this, sender, label, args);
            return command;
        }

        return null;
    }

    public CommandHandler<U> invoke(U sender, String label, String[] args) throws CommandException {
        CommandHandler<U> command = registration.getCommand(label);
        if (command != null) {
            command.invoke(this, sender, label, args);
            return command;
        }

        return null;
    }

    public abstract boolean hasPermission(U sender, String permission);

}
