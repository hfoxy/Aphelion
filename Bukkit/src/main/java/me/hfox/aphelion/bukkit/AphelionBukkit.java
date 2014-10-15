package me.hfox.aphelion.bukkit;

import me.hfox.aphelion.Aphelion;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class AphelionBukkit extends Aphelion<CommandSender> {

    private JavaPlugin plugin;

    public AphelionBukkit(JavaPlugin plugin) {
        super(CommandSender.class);
        this.plugin = plugin;
        this.registration = new BukkitCommandRegistration(this);
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    @Override
    public boolean hasPermission(CommandSender sender, String permission) {
        return sender.hasPermission("*") || sender.hasPermission(permission);
    }

}
