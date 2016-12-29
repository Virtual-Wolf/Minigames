package com.shockwavemc;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

public class Minigames extends JavaPlugin {
	private ScoreboardManager sMan = Bukkit.getScoreboardManager();
	private static Scoreboard board;
	private static Game current;
	public static boolean inGame, starting = false;
	private static ArrayList<String> participating = new ArrayList<String>();
	private static ArrayList<GameTeam> activeTeams = new ArrayList<GameTeam>();
	public static Location spawn = new Location(Bukkit.getWorld("Minigames"), 0, 100.5, 0);
	public static Minigames instance;
	private static World gameWorld;
	
	public void onEnable() {
		instance = this;
		board = sMan.getNewScoreboard();
		new GameCommand(this);
		new GameListener(this);
	}
	
	public void onDisable() {
		
	}
	
	public static World getGameWorld() {
		return gameWorld;
	}
	
	public static ArrayList<String> getPlayerList() {
		return participating;
	}
	
	public static ArrayList<GameTeam> getTeamList() {
		return activeTeams;
	}
	
	public static Scoreboard getBoard() {
		return board;
	}
	
	public static Game getCurrentGame() {
		return current;
	}
	
	public static void Broadcast(boolean prefix, String message) {
		Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', prefix ? ("" + ChatColor.GOLD + ChatColor.ITALIC + "Minigames:") : ("") + ChatColor.GRAY + " "));
	}
	
	private static void getNewGame() {
		Game g = Game.values()[r.nextInt(Game.values().length)];
		/*while(g == current) {
			g = Game.values()[r.nextInt(Game.values().length)];
		}*/ // To implement once there's atleast 2 games
		current = g;
	}
	
	private static Random r = new Random();
	public static void run() {
		if(participating.size() < 2) {
			Broadcast(true, "Not enough players to start!");
			return;
		}
		starting = true;
		getNewGame();
		Broadcast(false, "&e&l" + current.name + " &7" + ChatColor.ITALIC + " has been chosen as the next game.");
	}
}