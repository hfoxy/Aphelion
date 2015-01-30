package me.hfox.aphelion.bukkit.command;

import me.hfox.aphelion.command.SimpleCommandHandler;
import net.md_5.bungee.api.CommandSender;

public abstract class SimpleBungeeCommandHandler extends SimpleCommandHandler<CommandSender> {

    protected SimpleBungeeCommandHandler(String label, String[] aliases, String description, String usage) {
        super(label, aliases, description, usage);
    }

    protected SimpleBungeeCommandHandler(String[] aliases, String description, String usage) {
        super(aliases, description, usage);
    }

}
