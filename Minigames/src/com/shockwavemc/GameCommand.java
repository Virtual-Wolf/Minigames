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
			sender.sendMessage("" + ChatColor.GOLD + ChatColor.ITALIC + "Minigames Commands" + ChatColor.GRAY + ":\n"
					+ ChatColor.GOLD + ChatColor.ITALIC + "/mg join" + ChatColor.GRAY + ": Join the minigames\n"
					+ ChatColor.GOLD + ChatColor.ITALIC + "/mg leave" + ChatColor.GRAY + ": Leave the minigames\n"
					+ ChatColor.GOLD + ChatColor.ITALIC + "/mg help" + ChatColor.GRAY + ": Display this help menu\n"
					+ ChatColor.GOLD + ChatColor.ITALIC + "/mg force" + ChatColor.GRAY + ": Force a certain game to be chosen\n " + ChatColor.RED  + ChatColor.BOLD + "OPS ONLY\n"
					+ ChatColor.GOLD + ChatColor.ITALIC + "/mg start" + ChatColor.GRAY + ": Make the minigames start forcibly " + ChatColor.RED  + ChatColor.BOLD + "OPS ONLY\n"
					+ ChatColor.GOLD + ChatColor.ITALIC + "/mg stop" + ChatColor.GRAY + ": Make the minigames stop forcibly " + ChatColor.RED  + ChatColor.BOLD + "OPS ONLY\n");
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
				if(Minigames.getPlayerList().contains(p.getName())) {
					 p.sendMessage(ChatColor.RED + "You're already playing minigames!");
					 return false;
				 }
				if(!Minigames.inGame) {
					p.setScoreboard(Minigames.getBoard());
					Minigames.getPlayerList().add(p.getName());
					p.teleport(Minigames.spawn);
					p.setGameMode(GameMode.ADVENTURE);
					p.setFlying(false);
					p.setHealth(p.getMaxHealth());
					p.setFoodLevel(20);
					p.setSaturation(20);
					p.getInventory().clear();
					p.sendMessage(ChatColor.GRAY + "Minigames will start shortly!");
					if(!Minigames.starting) {
						Minigames.run();
					}
					return false;
				} else {
					p.setScoreboard(Minigames.getBoard());
					Minigames.getPlayerList().add(p.getName());
					p.teleport(Minigames.getGameWorldFile().getLocation("lobby"));
					p.setGameMode(GameMode.SPECTATOR);
					p.setHealth(p.getMaxHealth());
					p.setFoodLevel(20);
					p.setSaturation(20);
					p.getInventory().clear();
					p.sendMessage(ChatColor.GRAY + "Minigames will start shortly!");
				}
			}
			
			if(args[0].equalsIgnoreCase("leave")) {
				if(args.length > 1) {
					sender.sendMessage(ChatColor.RED + "Incorrect Syntax. Use /mg leave");
					return false;
				}
				if(!isPlayer) {
					sender.sendMessage(ChatColor.RED + "Only players can leave minigames!");
					return false;
				}
				 if(Minigames.getPlayerList().contains(p.getName())) {
					Minigames.playerLeave(p); 
					p.sendMessage(ChatColor.RED + "You have left minigames!");
				 } else {
					 p.sendMessage(ChatColor.RED + "You aren't in minigames!");
				 }
			} 
			if(!isPlayer || p.isOp()) {
				if(args[0].equalsIgnoreCase("start")) {
					if(Minigames.starting != true && Minigames.inGame != true) {
						Minigames.run();
					}
				}
				if(args[0].equalsIgnoreCase("force")) {
					if(!Minigames.inGame) {
						Game g = null;
						for(Game game : Game.values()) {
							if(game.name.equalsIgnoreCase(args[1])) {
								g = game;
							}
						}
						if(g != null)  {
							Minigames.current = g;
							Minigames.Broadcast(true, "&9&o" + sender.getName() + " &7has forced &e&l" + g.name);
						}
					}
				}
				if(args[0].equalsIgnoreCase("stop")) {
					if(Minigames.starting = true) {
						Minigames.starting = false;
					} else if(Minigames.inGame) {
						Minigames.end(null);
					}					
				}
			}
		}
		return false;
	}

}
