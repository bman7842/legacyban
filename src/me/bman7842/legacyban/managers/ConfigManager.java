package me.bman7842.legacyban.managers;

import me.bman7842.legacyban.Main;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;
import java.util.Set;

/**
 * Created by brand_000 on 8/2/2015.
 */
public class ConfigManager {

    File f;
    FileConfiguration config;

    public ConfigManager(String path) {
        if (!Main.getPlugin().getDataFolder().exists()) {
            Main.getPlugin().getDataFolder().mkdir();
        }

        f = new File(Main.getPlugin().getDataFolder(), path+".yml");

        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(f);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String path) {
        return (T) config.get(path);
    }

    public List<?> getList(String path) { return config.getList(path); }

    public Set<String> getKeys() {
        return config.getKeys(false);
    }

    public void set(String path, Object value) {
        config.set(path, value);
        save();
    }
    public ConfigurationSection createSection(String path) {
        ConfigurationSection cs = config.createSection(path);
        save();
        return cs;
    }

    public boolean contains(String path) {
        return config.contains(path);
    }

    public void save() {
        try {
            config.save(f);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


}
