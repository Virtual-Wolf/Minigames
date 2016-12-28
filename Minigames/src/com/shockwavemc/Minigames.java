package com.shockwavemc;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

public class Minigames extends JavaPlugin {
	private ScoreboardManager sMan = Bukkit.getScoreboardManager();
	private static Scoreboard board;
	private static Game current;
	public static boolean inGame = false;
	
	public void onEnable() {
		board = sMan.getNewScoreboard();
		new GameCommand(this);
		new GameListener(this);
	}
	
	public void onDisable() {
		
	}
	
	public static Scoreboard getBoard() {
		return board;
	}
	
	public static Game getCurrentGame() {
		return current;
	}
}