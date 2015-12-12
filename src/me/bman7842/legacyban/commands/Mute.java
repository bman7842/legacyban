package me.bman7842.legacyban.commands;

import me.bman7842.legacyban.managers.MuteManager;
import me.bman7842.legacyban.utils.Messages;
import me.bman7842.legacyban.utils.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by brand on 11/28/2015.
 */
public class Mute implements CommandExecutor {

    private final MuteManager muteManager;
    private final Permissions permissions;
    private final Messages messages;

    public Mute(MuteManager muteManager, Permissions permissions, Messages messages) {
        this.muteManager = muteManager;
        this.permissions = permissions;
        this.messages = messages;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (!sender.hasPermission(permissions.getKyraPunish())) {
            sender.sendMessage(Messages.MessageType.ERROR.getText() + "You don't have permission to run this command!");
            return false;
        }

        if (args.length == 0) {
            sender.sendMessage(Messages.MessageType.ERROR.getText() + "Invalid usage, /mute (name)");
            return false;
        }

        Player mutePlayer = Bukkit.getPlayer(args[0]);

        if (mutePlayer == null) {
            sender.sendMessage(Messages.MessageType.ERROR.getText() + "There is currently no player online with that name!");
            return false;
        }

        if (muteManager.isMuted(mutePlayer.getUniqueId())) {
            sender.sendMessage(Messages.MessageType.ERROR.getText() + "This player is already muted, do /unmute " + mutePlayer.getName() + " to unmute them");
            return false;
        }

        muteManager.mutePlayer(mutePlayer.getUniqueId(), sender.getName());
        sender.sendMessage(Messages.MessageType.ALERT.getText() + "You have muted " + mutePlayer.getName());
        messages.broadCastToPunishers(mutePlayer.getName() + " has been muted by " + sender.getName());
        mutePlayer.sendMessage(Messages.MessageType.ALERT.getText() + "You have been muted by " + sender.getName());

        return false;
    }

}
