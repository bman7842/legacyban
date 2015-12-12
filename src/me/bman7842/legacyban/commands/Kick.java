package me.bman7842.legacyban.commands;

import me.bman7842.legacyban.utils.AdvancedMessages;
import me.bman7842.legacyban.utils.Messages;
import me.bman7842.legacyban.utils.Permissions;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by brand on 11/27/2015.
 */
public class Kick implements CommandExecutor {

    private final Permissions permissions;
    private final Messages messages;
    private final AdvancedMessages advancedMessages;

    public Kick(Permissions permissions, Messages messages, AdvancedMessages advancedMessages) {
        this.permissions = permissions;
        this.messages = messages;
        this.advancedMessages = advancedMessages;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!sender.hasPermission(permissions.getKyraPunish())) {
            sender.sendMessage(Messages.MessageType.ERROR.getText() + "You don't have permission to run this command!");
            return false;
        }

        if (args.length == 0) {
            sender.sendMessage(Messages.MessageType.ERROR.getText() + "Invalid usage, do /kick (username) (reason)");
            return false;
        }

        if (Bukkit.getPlayer(args[0]) == null) {
            sender.sendMessage(Messages.MessageType.ERROR.getText() + "The user you specified is invalid!");
            return false;
        }

        if ((Bukkit.getPlayer(args[0]).hasPermission(permissions.getKyraSafe())) || (Bukkit.getPlayer(args[0]).isOp())) {
            sender.sendMessage(Messages.MessageType.ERROR.getText() + "You can't kick this player!");
            return false;
        }

        String kickMSG = StringUtils.join(args, " ", 1, args.length);

        Bukkit.getPlayer(args[0]).kickPlayer(advancedMessages.createKickMessage(sender.getName(), kickMSG));
        sender.sendMessage(Messages.MessageType.ALERT.getText() + "You kicked " + args[0]);
        messages.broadCastToPunishers(args[0] + " has been kicked by " + sender.getName() + ", reason: " + kickMSG);

        return false;
    }

}
