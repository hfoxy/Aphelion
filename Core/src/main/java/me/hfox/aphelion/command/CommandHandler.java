package me.hfox.aphelion.command;

import me.hfox.aphelion.Aphelion;
import me.hfox.aphelion.exception.CommandException;

public interface CommandHandler<U> {

    public default String getName() {
        return getAliases()[0];
    }

    public String[] getAliases();

    public String getDescription();

    public String getUsage();

    public default String getFlags() {
        return "";
    }

    public default String getValueFlags() {
        return "";
    }

    public default void invoke(Aphelion<U> aphelion, U sender, String label, String[] args) throws CommandException {
        try {
            handle(sender, create(aphelion, label, args));
        } catch (CommandException | RuntimeException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new CommandException(ex);
        }
    }

    public default CommandContext<U> create(Aphelion<U> aphelion, String label, String[] args) throws CommandException {
        return new CommandContext<>(aphelion, label, args, getValueFlags().toCharArray(), getFlags().toCharArray());
    }

    public void handle(U sender, CommandContext<U> context) throws Exception;

}
