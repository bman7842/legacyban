package me.bman7842.legacyban.utils;

import me.bman7842.legacyban.managers.ConfigManager;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brand on 11/28/2015.
 */
public class AdvancedMessages {

    private final ConfigManager config;

    private ArrayList<String> kickMessage = new ArrayList<String>();
    private ArrayList<String> banMessage = new ArrayList<String>();

    public AdvancedMessages(ConfigManager config) {
        this.config = config;

        if (config.get("Messages.KickMessage") == null) {
            kickMessage.add("You have been kicked from the server by %reason%");
            config.set("Messages.KickMessage", kickMessage);
        } else {
            for (String item : (List<String>)config.getList("Messages.KickMessage")) {
                kickMessage.add(ChatColor.translateAlternateColorCodes('&', item));
            }
        }
        if (config.get("Messages.BanMessage") == null) {
            banMessage.add(ChatColor.RED + "You have been banned from the server for %time%");
            banMessage.add(ChatColor.RED + "Banned by: " + ChatColor.WHITE + "%whobannedplayer%");
            banMessage.add(ChatColor.RED + "Reason: " + ChatColor.WHITE + "%reason%");
            config.set("Messages.BanMessage", banMessage);
        } else {
            for (String item : (List<String>)config.getList("Messages.BanMessage")) {
                banMessage.add(ChatColor.translateAlternateColorCodes('&', item));
            }
        }
    }

    public String createKickMessage(String whoKickedPlayer, String reason) {
        String msg = "";

        for (String currentLine : kickMessage) {
            String newCurrentLine = currentLine;
            if (newCurrentLine.contains("%reason%")) {
                newCurrentLine = newCurrentLine.replace("%reason%", reason);
            }
            if (newCurrentLine.contains("%whokickedplayer%")) {
                newCurrentLine = newCurrentLine.replace("%whokickedplayer%", whoKickedPlayer);
            }
            msg = msg + newCurrentLine + "\n";
        }

        return msg;
    }

    public String createBanMessage(String reason, String whoBannedPlayer, Integer time) {
        String msg = "";
        for (String currentLine : banMessage) {
            String newCurrentLine = currentLine;
            if (newCurrentLine.contains("%time%")) {
                if (time == null) {
                    newCurrentLine = newCurrentLine.replace("%time%", "EVER");
                } else {
                    //TODO: Time converter tsk
                    newCurrentLine = newCurrentLine.replace("%time%", timeToString(time));
                }
            }
            if (newCurrentLine.contains("%reason%")) {
            newCurrentLine = newCurrentLine.replace("%reason%", reason);
            }
            if (newCurrentLine.contains("%whobannedplayer%")) {
            newCurrentLine = newCurrentLine.replace("%whobannedplayer%", whoBannedPlayer);
            }
            msg = msg + newCurrentLine + "\n";
        }
        return msg;
    }


    public static String timeToString(Integer time) {
        int seconds = time;
        int minutes = 0;
        int hours = 0;
        int days = 0;
        int weeks = 0;
        int months = 0;
        int years = 0;
        if (time >= 60) {
            minutes = seconds / 60;
            seconds = seconds - ((seconds/60)*60);
            if (minutes >= 60) {
                hours = minutes/60;
                minutes = minutes - ((minutes/60)*60);
                if (hours >= 24) {
                    days = hours/24;
                    hours = hours - ((hours/24)*24);
                    if (days >= 7) {
                        weeks = days/7;
                        days = days - ((days/7)*7);
                        if (weeks >= 4) {
                            months = weeks/4;
                            weeks = weeks - ((weeks/4)*4);
                            if (months >= 12) {
                                years = months/12;
                                months = months - ((months/12)*12);
                            }
                        }
                    }
                }
            }
        }

        String msg = "";

        if (years > 0) {
            msg = msg + Integer.toString(years) + " years, ";
        }
        if (months > 0) {
            msg = msg + Integer.toString(months) + " months, ";
        }
        if (weeks > 0) {
            msg = msg + Integer.toString(weeks) + " weeks, ";
        }
        if (days > 0) {
            msg = msg + Integer.toString(days) + " days, ";
        }
        if (hours > 0) {
            msg = msg + Integer.toString(hours) + " hours, ";
        }
        if (minutes > 0) {
            msg = msg + Integer.toString(minutes) + " minutes, ";
        }
        if (seconds > 0) {
            msg = msg + Integer.toString(seconds) + " seconds";
        }

        return msg;
    }
}
