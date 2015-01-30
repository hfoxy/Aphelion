package me.hfox.aphelion.bukkit;

import me.hfox.aphelion.Aphelion;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Plugin;

public class AphelionBungee extends Aphelion<CommandSender> {

    private Plugin plugin;

    public AphelionBungee(Plugin plugin) {
        super(CommandSender.class);
        this.plugin = plugin;
        this.registration = new BungeeCommandRegistration(this);
    }

    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public boolean hasPermission(CommandSender sender, String permission) {
        return sender.hasPermission("*") || sender.hasPermission(permission);
    }

}
