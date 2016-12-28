package com.shockwavemc;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

public class Minigames extends JavaPlugin {
	private ScoreboardManager sMan = Bukkit.getScoreboardManager();
	private static Scoreboard board;
	private static Game current;
	public static boolean inGame = false;
	private static ArrayList<String> participating = new ArrayList<String>();
	
	public void onEnable() {
		board = sMan.getNewScoreboard();
		new GameCommand(this);
		new GameListener(this);
	}
	
	public void onDisable() {
		
	}
	
	public static ArrayList<String> getPlayerList() {
		return participating;
	}
	
	public static Scoreboard getBoard() {
		return board;
	}
	
	public static Game getCurrentGame() {
		return current;
	}
	
	public static void Broadcast(String message) {
		Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "" + ChatColor.GOLD + ChatColor.ITALIC + "Minigames:" + ChatColor.GRAY + " "));
	}
	
	public static void run() {
		if(participating.size() < 2) {
			Broadcast("Not enough players to start!");
			return;
		}
	}
}