package me.hfox.aphelion;

import me.hfox.aphelion.command.CommandHandler;
import me.hfox.aphelion.command.StaticCommandHandler;
import me.hfox.aphelion.command.annotations.Command;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class CommandRegistration<U> {

    private Aphelion<U> aphelion;
    private List<CommandHandler<U>> commands;

    public CommandRegistration(Aphelion<U> aphelion) {
        this.aphelion = aphelion;
        this.commands = new ArrayList<>();
    }

    public Aphelion<U> getAphelion() {
        return aphelion;
    }

    public List<CommandHandler<U>> getCommands() {
        return commands;
    }

    public CommandHandler<U> getCommand(String label) {
        return getCommand(label, true);
    }

    public CommandHandler<U> getCommand(String label, boolean aliases) {
        for (CommandHandler<U> command : commands) {
            if (command.getName().equalsIgnoreCase(label)) {
                return command;
            }
        }

        if (aliases) {
            for (CommandHandler<U> command : commands) {
                for (String alias : command.getAliases()) {
                    if (alias.equalsIgnoreCase(label)) {
                        return command;
                    }
                }
            }
        }

        return null;
    }

    public void register(CommandHandler<U> command) {
        commands.add(command);
    }

    public void register(Class<?> cls) {
        Method[] methods = cls.getMethods();
        for (Method method : methods) {
            register(method);
        }
    }

    public void register(Method method) {
        if (Modifier.isStatic(method.getModifiers()) && method.isAnnotationPresent(Command.class)) {
            Command command = method.getDeclaredAnnotation(Command.class);
            register(new StaticCommandHandler<>(method, command));
        }
    }

}
