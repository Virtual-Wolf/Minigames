package com.shockwavemc;

import java.io.File; 
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

import com.shockwavemc.Utils.Database;
import com.shockwavemc.Utils.WorldFile;
import com.shockwavemc.Utils.WorldUtils;

public class Minigames extends JavaPlugin {
	private static ScoreboardManager sMan;
	private static Scoreboard board;
	public static Game current;
	public static boolean inGame, starting = false;
	private static ArrayList<String> participating = new ArrayList<String>();
	private static ArrayList<GameTeam> activeTeams = new ArrayList<GameTeam>();
	public static Location spawn;
	public static Minigames instance;
	private static World gameWorld;
	
	public void onEnable() {
		instance = this;
		new GameCommand(this);
		new WorldCommand(this);
		new GameListener(this);
		WorldUtils.createWorld("Minigames");
		spawn = new Location(Bukkit.getWorld("Minigames"), 0.5, 100.5, 0.5);
		WorldUtils.createWorld("Prison");
		Database.enable();
		sMan = Bukkit.getScoreboardManager();
		board = sMan.getNewScoreboard();
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
		if(board == null) {
			sMan = Bukkit.getScoreboardManager();
			board = sMan.getNewScoreboard();
		}
		return board;
	}
	
	public static Game getCurrentGame() {
		return current;
	}
	
	public static void killPlayer(Player p, Player k) {
		if(inGame) {
			p.setHealth(p.getMaxHealth());
			getCurrentGame().game.killPlayer(p, k);
		} else {
			p.teleport(spawn);
		}
	}
	
	public static void playerLeave(Player p) {
		p.teleport(Bukkit.getWorld("Prison").getSpawnLocation());
		if(p.getGameMode() != GameMode.CREATIVE)
			p.setGameMode(GameMode.SURVIVAL);
		getPlayerList().remove(p.getName());
		if(inGame)
			getCurrentGame().game.playerLeave(p);
	}
	
	public static void Broadcast(boolean prefix, String message) {
		Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', (prefix ? ("&9&oMinigames&7: ") : ("")) + "&7" + message));
	}
	
	public static void messageToPlayer(Player p, boolean prefix, String message) {
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', (prefix ? ("&9&oMinigames&7: ") : ("")) + "&7" + message));
	}
	
	private static void getNewGame() {
		Game g = Game.values()[r.nextInt(Game.values().length)];
		while(g == current) {
			g = Game.values()[r.nextInt(Game.values().length)];
		}  
		for(GameTeam gt : g.teams) {
			getTeamList().add(gt);
		}
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
		Broadcast(false, "&e&l" + current.name + "&7&o has been chosen as the next game.");
		countdown(45);
	}
	
	private static void countdown(int num) {
		if(getPlayerList().size() < 2){
			Broadcast(false, "&7Game cancelled! not enough players.");
			starting = false;
			return;
		}
		if(!starting) {
			return;
		}
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
			Collections.shuffle(participating);
			getCurrentGame().game.teamSplit();
			getCurrentGame().game.start();
		}
	}
	public static int currentID;
	private static void loadWorld() {
		ArrayList<String> maps = Database.MAPS.getAll("Game", current.name, "Name");
		String map = maps.get(r.nextInt(maps.size()));
		Broadcast(true, "&7The map &6&o" + map + " &7has been selected");
		Broadcast(false, "&7Loading world...");
		currentID= r.nextInt(100000);
		WorldUtils.copyWorld(new File("./Maps/" + current.name + "-" + map), new File("./Game" + currentID));
		WorldUtils.createWorld("Game" + currentID);
		gameWorld = Bukkit.getWorld("Game" + currentID);
	}
	
	@SuppressWarnings("deprecation")
	public static void end(String winner) {
		inGame = false;
		if(winner != null)
			Broadcast(true, "&a&l" + winner + "&7 has won &6&o" + getCurrentGame().name);
		Bukkit.getScheduler().runTaskLater(Minigames.instance, new Runnable() {
			public void run() {
				for(GameTeam t : getTeamList()) {
					for(OfflinePlayer p : t.getScoreboardTeam().getPlayers()) {
						t.remove((Player) p);
					}
				}
				getTeamList().clear();
				for(String s : Minigames.getPlayerList()) {
					Player p = Bukkit.getPlayer(s);
					p.teleport(spawn);
					p.setGameMode(GameMode.ADVENTURE);
					p.getInventory().clear();
					p.getInventory().setArmorContents(null);
					WorldUtils.deleteWorld(getGameWorld());
				}
				Minigames.run();
			}
		}, 100);
	} 
}