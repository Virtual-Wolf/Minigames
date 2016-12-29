package com.shockwavemc.Utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.shockwavemc.Minigames;

public class WorldFile {
	private World world;
	private FileConfiguration config = null;
	private File configFile = null;
	
	public WorldFile(World w) {
		world = w;
		reloadConfig();
	}
	
	public void reloadConfig() {
	    if(configFile == null) {
	    	configFile = new File(world.getWorldFolder(), "worldfile.yml");
			if(!configFile.exists()) {
			    configFile.mkdir();	
			}
	    }
	    config = YamlConfiguration.loadConfiguration(configFile);
	}
	
	public FileConfiguration getConfig() {
	    if(config == null) {
	        reloadConfig();
	    }
	    return config;
	}
	
	public void saveConfig() {
	    if(config == null || configFile == null) {
	        return;
	    }
	    try {
	        getConfig().save(configFile);
	    } catch (IOException ex) {
	        Minigames.instance.getLogger().log(Level.SEVERE, "Could not save config to " + configFile, ex);
	    }
	}
	
	public Location getLocation(String path) {
		try {
		double x, y, z;
			x = getConfig().getDouble(path + ".x");
			y = getConfig().getDouble(path + ".y");
			z = getConfig().getDouble(path + ".z");
			return new Location(world, x, y, z);
		} catch(Exception e) {
			return null;
		}
	}
	
	public void saveLocation(String path, Location loc) {
		getConfig().set(path + ".x", loc.getX());
		getConfig().set(path + ".y", loc.getY());
		getConfig().set(path + ".z", loc.getZ());
		saveConfig();
	}

}
