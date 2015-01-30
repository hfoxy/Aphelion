package me.hfox.aphelion.bukkit;

import me.hfox.aphelion.CommandRegistration;
import me.hfox.aphelion.bukkit.command.BungeeCommand;
import me.hfox.aphelion.command.CommandHandler;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;

public class BungeeCommandRegistration extends CommandRegistration<CommandSender> {

    private AphelionBungee bungee;

    public BungeeCommandRegistration(AphelionBungee aphelion) {
        super(aphelion);
        this.bungee = aphelion;
    }

    @Override
    public void register(CommandHandler<CommandSender> command) {
        ProxyServer.getInstance().getPluginManager().registerCommand(bungee.getPlugin(), new BungeeCommand(bungee, command));
        super.register(command);
    }

}
