package me.hfox.aphelion.command;

import me.hfox.aphelion.command.annotations.Command;
import me.hfox.aphelion.command.annotations.CommandPermission;
import me.hfox.aphelion.exception.CommandUsageException;
import me.hfox.aphelion.Aphelion;
import me.hfox.aphelion.exception.CommandException;
import me.hfox.aphelion.exception.CommandPermissionException;
import org.hfox.commons.utils.reflection.SimpleMethod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class StaticCommandHandler<U> extends SimpleCommandHandler<U> {

    private SimpleMethod method;
    private String permission;

    private int min;
    private int max;
    private char[] valueFlags;
    private char[] flags;

    public StaticCommandHandler(Method method, Command command) {
        super(command.aliases(), command.description(), command.usage());
        this.method = new SimpleMethod(null, method);

        this.permission = null;
        if (method.isAnnotationPresent(CommandPermission.class)) {
            permission = method.getDeclaredAnnotation(CommandPermission.class).value();
        }

        this.min = command.min();
        this.max = command.max();
        this.valueFlags = command.valueFlags().toCharArray();
        this.flags = command.flags().toCharArray();
    }

    public void handle(U sender, CommandContext<U> context) throws Exception {
        if (permission != null && !context.hasPermission(sender, permission)) {
            throw new CommandPermissionException();
        }

        if (min > -1 && context.args().length < min) {
            throw new CommandUsageException();
        }

        if (max > -1 && context.args().length > max) {
            throw new CommandUsageException();
        }

        try {
            method.valueEx(sender, context);
        } catch (InvocationTargetException ex) {
            if (ex.getCause() != null && ex.getCause() instanceof Exception) {
                throw (Exception) ex.getCause();
            }

            throw ex;
        }
    }

    @Override
    public CommandContext<U> create(Aphelion<U> aphelion, String label, String[] args) throws CommandException {
        return new CommandContext<>(aphelion, label, args, valueFlags, flags);
    }

}
