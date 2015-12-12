package me.bman7842.legacyban;

import me.bman7842.legacyban.commands.*;
import me.bman7842.legacyban.events.ChatEvent;
import me.bman7842.legacyban.events.JoinEvent;
import me.bman7842.legacyban.managers.BanManager;
import me.bman7842.legacyban.managers.ConfigManager;
import me.bman7842.legacyban.managers.MuteManager;
import me.bman7842.legacyban.utils.AdvancedMessages;
import me.bman7842.legacyban.utils.Messages;
import me.bman7842.legacyban.utils.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by brand on 11/27/2015.
 */
public class Main extends JavaPlugin {

    private BanManager banManager;
    private MuteManager muteManager;
    private AdvancedMessages advancedMessages;
    private Messages messages;
    private Permissions permissions;
    private ConfigManager mutesConfig;
    private ConfigManager mainConfig;
    private ConfigManager bansConfig;

    @Override
    public void onEnable() {
        mutesConfig = new ConfigManager("mutes");
        bansConfig = new ConfigManager("bans");
        mainConfig = new ConfigManager("config");
        advancedMessages = new AdvancedMessages(mainConfig);
        permissions = new Permissions(mainConfig);
        messages = new Messages(permissions, mainConfig);
        banManager = new BanManager(this, advancedMessages, bansConfig);
        muteManager = new MuteManager(mutesConfig);

        getCommand("kick").setExecutor(new Kick(permissions, messages, advancedMessages));
        getCommand("ban").setExecutor(new Ban(banManager, permissions, messages));
        getCommand("tempban").setExecutor(new TempBan(banManager, permissions, messages));
        getCommand("unban").setExecutor(new Unban(banManager, permissions, messages));
        getCommand("mute").setExecutor(new Mute(muteManager, permissions, messages));
        getCommand("unmute").setExecutor(new Unmute(muteManager, permissions, messages));
        getCommand("mutechat").setExecutor(new MuteChat(permissions, muteManager));

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new JoinEvent(banManager, advancedMessages), this);
        pm.registerEvents(new ChatEvent(muteManager, permissions), this);
    }

    @Override
    public void onDisable() {
        banManager.saveData();
        muteManager.saveMutes();
    }

    public static Plugin getPlugin() {
        return Bukkit.getPluginManager().getPlugin("LegacyBan");
    }
}
