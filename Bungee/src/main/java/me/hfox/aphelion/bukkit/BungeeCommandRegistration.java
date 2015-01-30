package me.hfox.aphelion.bukkit;

import me.hfox.aphelion.CommandRegistration;
import me.hfox.aphelion.bukkit.command.BungeeCommand;
import me.hfox.aphelion.command.CommandHandler;
import me.hfox.aphelion.utils.reflection.SimpleMethod;
import me.hfox.aphelion.utils.reflection.SimpleObject;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.PluginManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BungeeCommandRegistration extends CommandRegistration<CommandSender> {

    private AphelionBungee bungee;
    private SimpleMethod method;

    public BungeeCommandRegistration(AphelionBungee aphelion) {
        super(aphelion);
        this.bungee = aphelion;
        this.method = new SimpleObject(ProxyServer.getInstance().getPluginManager(), PluginManager.class).field("commandMap").method("get", Object.class);
    }

    @Override
    public void register(CommandHandler<CommandSender> handler) {
        BungeeCommand command = new BungeeCommand(bungee, handler);
        ProxyServer.getInstance().getPluginManager().registerCommand(bungee.getPlugin(), command);
        super.register(handler);
    }

    @Override
    public void remove(CommandHandler<CommandSender> handler) {
        List<String> strings = new ArrayList<>();

        strings.add(handler.getName());
        Collections.addAll(strings, handler.getAliases());

        for (String string : strings) {
            Command command = method.value(Command.class, string);
            if (command == null) {
                continue;
            }

            ProxyServer.getInstance().getPluginManager().unregisterCommand(command);
        }

        super.remove(handler);
    }

}
