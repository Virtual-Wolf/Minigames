package com.shockwavemc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GameCommand implements CommandExecutor {
	public GameCommand(Minigames mgs) {
		mgs.getCommand("mg").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// TODO Auto-generated method stub
		return false;
	}

}
