package me.bman7842.legacyban.utils;

import me.bman7842.legacyban.managers.ConfigManager;
import org.bukkit.permissions.Permission;

/**
 * Created by brand on 11/27/2015.
 */
public class Permissions {

    private final ConfigManager config;

    private final Permission kyraPunish;
    private final Permission kyraSafe;

    public Permissions(ConfigManager config) {
        this.config = config;

        if (config.get("Permissions.kyrapunish") == null) {
            kyraPunish = new Permission("kyra.punish");
            config.set("Permissions.kyrapunish", "kyra.punish");
        } else {
            kyraPunish = new Permission(config.get("Permissions.kyrapunish"));
        }

        if (config.get("Permissions.kyrasafe") == null) {
            kyraSafe = new Permission("kyra.safe");
            config.set("Permissions.kyrasafe", "kyra.safe");
        } else {
            kyraSafe = new Permission(config.get("Permissions.kyrasafe"));
        }
    }

    public Permission getKyraPunish() { return kyraPunish; }
    public Permission getKyraSafe() { return kyraSafe; }
}
