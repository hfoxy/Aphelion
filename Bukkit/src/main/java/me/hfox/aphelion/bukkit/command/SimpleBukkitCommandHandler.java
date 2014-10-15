package me.hfox.aphelion.bukkit.command;

import me.hfox.aphelion.command.SimpleCommandHandler;
import org.bukkit.command.CommandSender;

public abstract class SimpleBukkitCommandHandler extends SimpleCommandHandler<CommandSender> {

    protected SimpleBukkitCommandHandler(String label, String[] aliases, String description, String usage) {
        super(label, aliases, description, usage);
    }

    protected SimpleBukkitCommandHandler(String[] aliases, String description, String usage) {
        super(aliases, description, usage);
    }

}
