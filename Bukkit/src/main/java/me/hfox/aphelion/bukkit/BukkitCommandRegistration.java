package me.hfox.aphelion.bukkit;

import me.hfox.aphelion.CommandRegistration;
import me.hfox.aphelion.bukkit.command.BukkitCommand;
import me.hfox.aphelion.command.CommandHandler;
import me.hfox.aphelion.utils.reflection.SimpleObject;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;

import java.util.HashMap;
import java.util.Map;

public class BukkitCommandRegistration extends CommandRegistration<CommandSender> {

    private AphelionBukkit bukkit;
    private SimpleCommandMap commandMap;
    private Map<String, Command> knownCommands;

    @SuppressWarnings("unchecked")
    public BukkitCommandRegistration(AphelionBukkit aphelion) {
        super(aphelion);
        this.bukkit = aphelion;

        SimpleObject object = new SimpleObject(aphelion.getPlugin().getServer().getPluginManager());
        this.commandMap = object.field("commandMap").value(SimpleCommandMap.class);
        this.knownCommands = new SimpleObject(commandMap).field("knownCommands").value(Map.class);
    }

    @Override
    public void register(CommandHandler<CommandSender> command) {
        commandMap.register(bukkit.getPlugin().getDescription().getName().toLowerCase(), new BukkitCommand(bukkit, command));
        super.register(command);
    }

    @Override
    public void remove(CommandHandler<CommandSender> handler) {
        Map<String, Command> commands = getCommand(handler);
        commands.keySet().forEach(knownCommands::remove);

        super.remove(handler);
    }

    public Map<String, Command> getCommand(CommandHandler<CommandSender> handler) {
        Map<String, Command> commands = new HashMap<>();
        knownCommands.entrySet().stream().filter(entry -> entry.getValue() instanceof BukkitCommand).forEach(entry -> {
            BukkitCommand command = (BukkitCommand) entry.getValue();
            if (command.getCommand().equals(handler)) {
                commands.put(entry.getKey(), entry.getValue());
            }
        });

        return commands;
    }

    public void update(CommandHandler<CommandSender> handler) {
        BukkitCommand command = new BukkitCommand(bukkit, handler);
        Map<String, Command> commands = getCommand(handler);
        for (String string : commands.keySet()) {
            knownCommands.put(string, command);
        }
    }

}
