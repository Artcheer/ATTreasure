package me.artcheer.attreasure.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class YamlHelper extends YamlConfiguration {
    private FileInputStream fileInputStream;
    private File bruteFile;
    private Plugin plugin;

    public YamlHelper(String name, Plugin plugin) {
        this.plugin = plugin;

        bruteFile = new File(plugin.getDataFolder(), name.matches(".*(?i).yml$") ? name : name.concat(".yml"));

        try {
            if (!plugin.getDataFolder().exists()) {
                plugin.getDataFolder().mkdir();
            }

            if (!bruteFile.exists()) {
                bruteFile.createNewFile();
            }
            fileInputStream = new FileInputStream(bruteFile);
            load(new InputStreamReader(fileInputStream,Charset.forName("UTF-8")));
        } catch (IOException | InvalidConfigurationException ex) {
            Logger.getLogger(YamlHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void saveDefault() {
        if (plugin.getResource(bruteFile.getName()) == null) {
            System.err.println("[" + plugin.getName() + "] Nao foi possivel salvar o arquivo");
            System.err.println("[" + plugin.getName() + "] default da config " + bruteFile.getName() + " pois o jar nao");
            System.err.println("[" + plugin.getName() + "] contem um arquivo com teste nome.");
        } else {
            plugin.saveResource(bruteFile.getName(), true);
        }
    }

    @Override
    public void set(String path, Object obj) {
        super.set(path, obj);
        this.save();
    }

    public void save() {
        try {
            super.save(bruteFile);
        } catch (IOException e) {
            Logger.getLogger(YamlHelper.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void reload() {
        try {
            fileInputStream = new FileInputStream(bruteFile);
            load(new InputStreamReader(fileInputStream, Charset.forName("UTF-8")));
        } catch (IOException | InvalidConfigurationException e) {
            Logger.getLogger(YamlHelper.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
