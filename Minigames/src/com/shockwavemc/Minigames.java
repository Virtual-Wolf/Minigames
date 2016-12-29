package com.shockwavemc;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

import com.shockwavemc.Utils.Database;
import com.shockwavemc.Utils.WorldFile;
import com.shockwavemc.Utils.WorldUtils;

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
	
	public static WorldFile getGameWorldFile() {
		return new WorldFile(gameWorld);
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
	
	public static void killPlayer(Player p) {
		p.setHealth(p.getMaxHealth());
		getCurrentGame().game.killPlayer(p);
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
		countdown(45);
	}
	
	private static void countdown(int num) {
		if(num> 0) {
			if(num == 45 || num == 30 || num == 15 || num == 10 || num ==5 || num == 4 || num == 3 || num == 2 || num == 1) {
				Broadcast(true, "&6&o" + current.name + "&7 will start in &6&o" + num + "&7 seconds");
				if(num == 15) {
					loadWorld();
				}
			}
			Bukkit.getScheduler().runTaskLater(instance, new Runnable() {public void run() {countdown(num-1);}}, 20);
		} else {
			inGame = true;
			starting = false;
			getCurrentGame().game.teamSplit();
			getCurrentGame().game.start();
		}
	}
	public static int currentID;
	private static void loadWorld() {
		ArrayList<String> maps = Database.MAPS.getAll("Game", current.name);
		String map = maps.get(r.nextInt(maps.size()));
		Broadcast(true, "&7The map &6&o" + map + " &7has been selected");
		Broadcast(false, "&7Loading world...");
		currentID= r.nextInt(100000);
		WorldUtils.copyWorld(new File("./Maps/" + current.name + "-" + map), new File("./Game" + currentID));
		WorldUtils.createWorld("Game" + currentID);
		gameWorld = Bukkit.getWorld("Game" + currentID);
	}
	
	@SuppressWarnings("deprecation")
	public static void end() {
		for(String s : Minigames.getPlayerList()) {
			Player p = Bukkit.getPlayer(s);
			p.teleport(Bukkit.getWorld("Minigames").getSpawnLocation());
			p.setGameMode(GameMode.ADVENTURE);
		}
		run();
	} 
}