package me.bman7842.legacyban.utils;

import me.bman7842.legacyban.managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by brand on 11/27/2015.
 */
public class Messages {

    private ConfigManager config;
    private Permissions permissions;

    public enum MessageType {
        ERROR(ChatColor.RED + "" + ChatColor.BOLD + "ERROR" + ChatColor.GRAY + " | " + ChatColor.WHITE),
        MUTED(ChatColor.RED + "" + ChatColor.BOLD + "MUTED" + ChatColor.GRAY + " | " + ChatColor.WHITE),
        ALERT(ChatColor.GREEN + "" + ChatColor.BOLD + "ALERT" + ChatColor.GRAY + " | " + ChatColor.WHITE),
        STAFFBROADCAST(ChatColor.BLUE + "" + ChatColor.BOLD + "KYRABAN-STAFF" + ChatColor.GRAY + " | " + ChatColor.DARK_GRAY);

        private String text;

        MessageType(String text) {
            this.text = text;
        }

        public void setText(String text) { this.text = text; }

        public String getText() {
            return text;
        }
    }

    public Messages(Permissions permissions, ConfigManager config) {
        this.permissions = permissions;
        this.config = config;

        if (config.get("MessageTypes.Error") == null) {
            config.set("MessageTypes.Error", MessageType.ERROR.getText());
        } else {
            MessageType.ERROR.setText(ChatColor.translateAlternateColorCodes('&', config.get("MessageTypes.Error")));
        }
        if (config.get("MessageTypes.Muted") == null) {
            config.set("MessageTypes.Muted", MessageType.MUTED.getText());
        } else {
            MessageType.MUTED.setText(ChatColor.translateAlternateColorCodes('&', config.get("MessageTypes.Muted")));
        }
        if (config.get("MessageTypes.Alert") == null) {
            config.set("MessageTypes.Alert", MessageType.ALERT.getText());
        } else {
            MessageType.ALERT.setText(ChatColor.translateAlternateColorCodes('&', config.get("MessageTypes.Alert")));
        }
        if (config.get("MessageTypes.StaffBroadcast") == null) {
            config.set("MessageTypes.StaffBroadcast", MessageType.STAFFBROADCAST.getText());
        } else {
            MessageType.STAFFBROADCAST.setText(ChatColor.translateAlternateColorCodes('&', config.get("MessageTypes.StaffBroadcast")));
        }
    }

    public void broadCastToPunishers(String msg) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission(permissions.getKyraPunish()) || p.isOp()) {
                p.sendMessage(MessageType.STAFFBROADCAST.getText() + msg);
            }
        }
    }

}
