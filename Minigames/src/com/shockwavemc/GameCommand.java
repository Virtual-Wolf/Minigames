package com.shockwavemc;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameCommand implements CommandExecutor {
	public GameCommand(Minigames mgs) {
		mgs.getCommand("mg").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = null;
		boolean isPlayer = false;
		if(sender instanceof Player) {
			p = (Player) sender;
			isPlayer = true;
		}
		if(args.length == 0 || args[0].equalsIgnoreCase("help")) {
			sender.sendMessage("" + ChatColor.GOLD + ChatColor.ITALIC + "Minigames Commands:\n"
					+ "/mg join" + ChatColor.GRAY + ": Join the minigames\n"
					+ "/mg leave" + ChatColor.GRAY + ": Leave the minigames\n"
					+ "/mg help" + ChatColor.GRAY + ": Display this help menu\n"
					+ "/mg force" + ChatColor.GRAY + ": Force a certain game to be chosen\n     " + ChatColor.RED  + ChatColor.BOLD + "OPS ONLY\n"
					+ "/mg start" + ChatColor.GRAY + ": Make the minigames start forcibly     " + ChatColor.RED  + ChatColor.BOLD + "OPS ONLY\n"
					+ "/mg stop" + ChatColor.GRAY + ": Make the minigames stop forcibly     " + ChatColor.RED  + ChatColor.BOLD + "OPS ONLY\n");
			return false;
		} else {
			if(args[0].equalsIgnoreCase("join")) {
				if(args.length > 1) {
					sender.sendMessage(ChatColor.RED + "Incorrect Syntax. Use /mg join");
					return false;
				}
				if(!isPlayer) {
					sender.sendMessage(ChatColor.RED + "Only players can join minigames!");
					return false;
				}
				if(!Minigames.inGame) {
					Minigames.getPlayerList().add(p.getName());
					p.teleport(Minigames.spawn);
					p.setGameMode(GameMode.ADVENTURE);
					p.setFlying(false);
					p.sendMessage(ChatColor.GRAY + "Minigames will start shortly!");
					if(!Minigames.starting) {
						Minigames.run();
					}
					return false;
				}
			}
		}
		return false;
	}

}
