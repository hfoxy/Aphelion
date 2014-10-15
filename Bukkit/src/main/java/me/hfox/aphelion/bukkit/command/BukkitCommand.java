package me.hfox.aphelion.bukkit.command;

import me.hfox.aphelion.bukkit.AphelionBukkit;
import me.hfox.aphelion.command.CommandHandler;
import me.hfox.aphelion.exception.CommandException;
import me.hfox.aphelion.exception.CommandPermissionException;
import me.hfox.aphelion.exception.CommandUsageException;
import me.hfox.aphelion.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class BukkitCommand extends Command {

    private AphelionBukkit aphelion;
    private CommandHandler<CommandSender> command;

    public BukkitCommand(AphelionBukkit aphelion, CommandHandler<CommandSender> command) {
        super(command.getName(), command.getDescription(), command.getUsage(), Utils.asList(command.getAliases()));
        this.aphelion = aphelion;
        this.command = command;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        try {
            aphelion.invoke(sender, commandLabel, args, command);
        } catch(CommandPermissionException e) {
            sender.sendMessage(ChatColor.RED + "No permission.");
        } catch(CommandUsageException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
            sender.sendMessage(ChatColor.RED + "/" + commandLabel + " " + command.getUsage());
        } catch(CommandException e) {
            if (e.getCause() != null) {
                if(e.getCause() instanceof NumberFormatException) {
                    sender.sendMessage(ChatColor.RED + "Number expected, string received instead.");
                } else {
                    sender.sendMessage(ChatColor.RED + "An error has occurred. See console.");
                    e.printStackTrace();
                }
            } else {
                sender.sendMessage(ChatColor.RED + e.getMessage());
            }
        }

        return true;
    }

}
