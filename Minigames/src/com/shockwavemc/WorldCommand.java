package com.shockwavemc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.shockwavemc.Utils.WorldFile;
import com.shockwavemc.Utils.WorldUtils;


public class WorldCommand implements CommandExecutor {

	public WorldCommand(Minigames mgs) {
		mgs.getCommand("mw").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.isOp()) {
			sender.sendMessage(ChatColor.RED + "Permission denied");
			return false;
		}
		if(sender instanceof Player) {
			Player p = (Player) sender;
			WorldFile wf = new WorldFile(p.getWorld());
			if(args.length == 0) {
				p.sendMessage(ChatColor.BLUE + "You're currently in " + p.getWorld().getName());
				return false;
			}
			if(args[0].equalsIgnoreCase("goto")) {
				World target = Bukkit.getWorld(args[1]);
				if(target != null) {
					p.teleport(target.getSpawnLocation());
					p.sendMessage(ChatColor.GREEN + "Teleported to " + args[1]);
				} else {
					p.sendMessage(ChatColor.RED + "That world doesn't exist.");
				}
			}
			if(args[0].equalsIgnoreCase("set")) {
				wf.getConfig().set(args[1], args[2]);
			}
			
			if(args[0].equalsIgnoreCase("setloc")) {
				wf.saveLocation(args[1], p.getLocation());
			}
			
		}
		if(args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("load")) {
				WorldUtils.createWorld(args[1]);
				sender.sendMessage(ChatColor.GREEN + "World created");
			}
			if(args[0].equalsIgnoreCase("unload")) {
				Bukkit.unloadWorld(args[1], true);
				sender.sendMessage(ChatColor.RED + "Unloaded world");
			}
			return false;
	}
}
