package me.hfox.aphelion.bukkit;

import me.hfox.aphelion.CommandRegistration;
import me.hfox.aphelion.bukkit.command.BukkitCommand;
import me.hfox.aphelion.command.CommandHandler;
import me.hfox.aphelion.utils.reflection.SimpleObject;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;

public class BukkitCommandRegistration extends CommandRegistration<CommandSender> {

    private AphelionBukkit bukkit;
    private CommandMap commandMap;

    public BukkitCommandRegistration(AphelionBukkit aphelion) {
        super(aphelion);
        this.bukkit = aphelion;

        SimpleObject object = new SimpleObject(aphelion.getPlugin().getServer().getPluginManager());
        this.commandMap = object.field("commandMap").value(CommandMap.class);
    }

    @Override
    public void register(CommandHandler<CommandSender> command) {
        // commandMap.
        commandMap.register(bukkit.getPlugin().getDescription().getName().toLowerCase(), new BukkitCommand(bukkit, command));
        super.register(command);
    }

}
