package me.hfox.aphelion.bukkit.command;

import me.hfox.aphelion.bukkit.AphelionBungee;
import me.hfox.aphelion.command.CommandHandler;
import me.hfox.aphelion.exception.CommandException;
import me.hfox.aphelion.exception.CommandPermissionException;
import me.hfox.aphelion.exception.CommandUsageException;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;

public class BungeeCommand extends Command {

    private AphelionBungee aphelion;
    private CommandHandler<CommandSender> command;

    public BungeeCommand(AphelionBungee aphelion, CommandHandler<CommandSender> command) {
        super(command.getName(), null, command.getAliases());
        this.aphelion = aphelion;
        this.command = command;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        try {
            aphelion.invoke(sender, getName(), args, command);
        } catch(CommandPermissionException e) {
            sender.sendMessage(new ComponentBuilder("No permission.").color(ChatColor.RED).create());
        } catch(CommandUsageException e) {
            sender.sendMessage(new ComponentBuilder(e.getMessage()).color(ChatColor.RED).create());
            sender.sendMessage(new ComponentBuilder("/" + getName() + " " + command.getUsage()).color(ChatColor.RED).create());
        } catch(CommandException e) {
            if (e.getCause() != null) {
                if(e.getCause() instanceof NumberFormatException) {
                    sender.sendMessage(new ComponentBuilder("Number expected, string received instead.").color(ChatColor.RED).create());
                } else {
                    sender.sendMessage(new ComponentBuilder("An error has occurred. See console.").color(ChatColor.RED).create());
                    e.printStackTrace();
                }
            } else {
                sender.sendMessage(new ComponentBuilder(e.getMessage()).color(ChatColor.RED).create());
            }
        }
    }

}
