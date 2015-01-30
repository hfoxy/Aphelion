package me.hfox.aphelion.bukkit;

import com.google.common.collect.Multimap;
import me.hfox.aphelion.CommandRegistration;
import me.hfox.aphelion.bukkit.command.BungeeCommand;
import me.hfox.aphelion.command.CommandHandler;
import me.hfox.aphelion.utils.reflection.SimpleObject;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BungeeCommandRegistration extends CommandRegistration<CommandSender> {

    private AphelionBungee bungee;
    private Map<String, Command> commandMap;
    private Multimap<Plugin, Command> pluginCommands;

    @SuppressWarnings("unchecked")
    public BungeeCommandRegistration(AphelionBungee aphelion) {
        super(aphelion);
        this.bungee = aphelion;

        SimpleObject pluginManager = new SimpleObject(ProxyServer.getInstance().getPluginManager(), PluginManager.class);
        this.commandMap = pluginManager.field("commandMap").value(Map.class);
        this.pluginCommands = pluginManager.field("commandsByPlugin").value(Multimap.class);
    }

    @Override
    public void register(CommandHandler<CommandSender> handler) {
        BungeeCommand command = new BungeeCommand(bungee, handler);
        ProxyServer.getInstance().getPluginManager().registerCommand(bungee.getPlugin(), command);
        super.register(handler);
    }

    @Override
    public void remove(CommandHandler<CommandSender> handler) {
        List<String> strings = getNames(handler);

        for (String string : strings) {
            Command command = commandMap.get(string);
            if (command == null) {
                continue;
            }

            ProxyServer.getInstance().getPluginManager().unregisterCommand(command);
        }

        super.remove(handler);
    }

    public void update(CommandHandler<CommandSender> handler) {
        BungeeCommand command = new BungeeCommand(bungee, handler);
        List<String> strings = getNames(handler);
        for (String string : strings) {
            Command old = commandMap.get(string);
            pluginCommands.remove(bungee.getPlugin(), old);

            commandMap.put(string, command);
            pluginCommands.put(bungee.getPlugin(), command);
        }
    }

    public List<String> getNames(CommandHandler<CommandSender> handler) {
        List<String> strings = new ArrayList<>();

        strings.add(handler.getName());
        Collections.addAll(strings, handler.getAliases());

        return strings;
    }

}
