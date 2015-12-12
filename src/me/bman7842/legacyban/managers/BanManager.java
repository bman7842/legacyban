package me.bman7842.legacyban.managers;

import me.bman7842.legacyban.Main;
import me.bman7842.legacyban.utils.AdvancedMessages;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by brand on 11/27/2015.
 */
public class BanManager {

    private ConfigManager bans;
    private AdvancedMessages advancedMessages;

    private HashMap<UUID, String> bannedPlayers = new HashMap<UUID, String>();
    private HashMap<UUID, Integer> banTime = new HashMap<UUID, Integer>();
    private HashMap<UUID, String> whobannedPlayer = new HashMap<UUID, String>();

    public BanManager(Main main, AdvancedMessages advancedMessages, ConfigManager bans) {
        this.bans = bans;
        this.advancedMessages = advancedMessages;

        for (String uuid : bans.getKeys()) {
            bannedPlayers.put(UUID.fromString(uuid), bans.get(uuid + ".reason"));
            whobannedPlayer.put(UUID.fromString(uuid), bans.get(uuid + ".whobannedplayer"));
            if (bans.get(uuid + ".time") == null) {
                banTime.put(UUID.fromString(uuid), null);
            } else {
                banTime.put(UUID.fromString(uuid), bans.get(uuid + ".time"));
            }
        }

        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {
            @Override
            public void run() {
                for (UUID pUUID : banTime.keySet()) {
                    if (banTime.get(pUUID) != null) {
                        if (banTime.get(pUUID) == 1) {
                            banTime.remove(pUUID);
                            bannedPlayers.remove(pUUID);
                            bans.set(pUUID.toString(), null);
                        } else {
                            banTime.put(pUUID, banTime.get(pUUID) - 1);
                        }
                    }
                }
            }
        },20L, 20L);
    }

    public void saveData() {
        for (UUID playerUUID : bannedPlayers.keySet()) {
            bans.set(playerUUID.toString() + ".reason", bannedPlayers.get(playerUUID));
            bans.set(playerUUID.toString() + ".whobannedplayer", whobannedPlayer.get(playerUUID));
            if (banTime.get(playerUUID) == null) {
                bans.set(playerUUID.toString() + ".time", null);
            } else {
                bans.set(playerUUID.toString() + ".time", banTime.get(playerUUID));
            }
        }
        Bukkit.getLogger().info("Data saved");
    }

    public void banPlayer(String player, String whoBannedPlayer, String reason) {
        if (Bukkit.getPlayer(player) == null) {
            bannedPlayers.put(Bukkit.getOfflinePlayer(player).getUniqueId(), reason);
        } else {
            Player p = Bukkit.getPlayer(player);
            p.kickPlayer(advancedMessages.createBanMessage(reason, whoBannedPlayer, null));

            bannedPlayers.put(p.getUniqueId(), reason);
            banTime.put(p.getUniqueId(), null);
            whobannedPlayer.put(p.getUniqueId(), whoBannedPlayer);
        }
    }

    public void tempBan(String playerBanned, String whoBannedPlayer,  Integer time, String reason) {
        if (Bukkit.getPlayer(playerBanned) == null) {
            OfflinePlayer p = Bukkit.getOfflinePlayer(playerBanned);
            bannedPlayers.put(p.getUniqueId(), reason);
            banTime.put(p.getUniqueId(), time);
        } else {
            Player p = Bukkit.getPlayer(playerBanned);
            bannedPlayers.put(p.getUniqueId(), reason);
            banTime.put(p.getUniqueId(), time);
            whobannedPlayer.put(p.getUniqueId(), whoBannedPlayer);
            p.kickPlayer(advancedMessages.createBanMessage(reason, whoBannedPlayer, time));
        }
    }

    public void unBan(UUID pUUID) {
        if (bannedPlayers.containsKey(pUUID)) {
            bannedPlayers.remove(pUUID);
            banTime.remove(pUUID);
            whobannedPlayer.remove(pUUID);

            bans.set(pUUID.toString(), null);
        } else {
            Bukkit.getLogger().warning("Trying to unban a already unbanned player!");
        }
    }

    public boolean isBan(UUID p) {
        return bannedPlayers.keySet().contains(p);
    }

    public String getReason(UUID p) {
        return (bannedPlayers.keySet().contains(p) ? bannedPlayers.get(p) : null);
    }

    public Integer getTime(UUID p) {
        return (banTime.containsKey(p) ? banTime.get(p) : null);
    }

    public String getWhoBannedPlayer(UUID p) {
        return (whobannedPlayer.containsKey(p) ? whobannedPlayer.get(p) : null);
    }
}
