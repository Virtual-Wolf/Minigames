package com.shockwavemc.Games;

import java.util.ArrayList; 
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import com.shockwavemc.Game;
import com.shockwavemc.Minigame;
import com.shockwavemc.Minigames;
import com.shockwavemc.Utils.WorldFile;

public class Brawl implements Minigame, Listener {
	
	public Brawl(Minigames mgs) {
		mgs.getServer().getPluginManager().registerEvents(this, mgs);
	}
	
	private String p1 = "", p2 = "";
	private ArrayList<String> players = new ArrayList<String>();

	@SuppressWarnings("deprecation")
	@Override
	public void start() {
		p1 = "";
		p2 = "";
		WorldFile wf = new WorldFile(Minigames.getGameWorld());
		for(String s : Minigames.getPlayerList()) {
			Bukkit.getPlayer(s).teleport(wf.getLocation("lobby"));
		}
		run();
	}

	@Override
	public void teamSplit() {
		players.clear();
		thisRound.clear();
		for(String s : Minigames.getPlayerList()) {
			players.add(s);
		}
	}
	
	private ArrayList<String> thisRound = new ArrayList<String>();
	Random r = new Random();
	@SuppressWarnings("deprecation")
	@Override
	public void run() { 
		if(Bukkit.getPlayer(p1) != null)
			Bukkit.getPlayer(p1).teleport(Minigames.getGameWorldFile().getLocation("lobby"));
		if(Bukkit.getPlayer(p2) != null)
			Bukkit.getPlayer(p2).teleport(Minigames.getGameWorldFile().getLocation("lobby"));
		if(players.size() > 1) {
			if(thisRound.size() > 1) {
				p1 = thisRound.get(r.nextInt(thisRound.size()));
				thisRound.remove(p1);
				p2 = thisRound.get(r.nextInt(thisRound.size()));
				thisRound.remove(p2);
				Minigames.Broadcast(false, "&c&o" + p1 + "&7 vs &b&o" + p2);
				Bukkit.getPlayer(p1).teleport(Minigames.getGameWorldFile().getLocation("spawn1"));
				Bukkit.getPlayer(p2).teleport(Minigames.getGameWorldFile().getLocation("spawn2"));
			} else {
				thisRound.clear();
				for(String s : players) {
					thisRound.add(s);
				}
				run();
			}
		} else {
			Minigames.end(players.get(0));
			players.clear();
			thisRound.clear();
		}
	}
	
	@Override
	public boolean gameRunning() {
		return (Minigames.inGame && Minigames.getCurrentGame() == Game.Brawl);
	}
	
	public boolean isBrawler(Player p) {
		if(p.getName() == p1 || p.getName() == p2) {
			return true;
		}
		return false;
	}
	
	@EventHandler
	public void onCombat(EntityDamageByEntityEvent e) {
		if(gameRunning()) {
			if(e.getDamager() instanceof Player && e.getEntity() instanceof Player && Minigames.getPlayerList().contains(((Player)e.getEntity()).getName())  ) {
				e.setDamage(0);
				Player p = (Player) e.getEntity();
				Player d = (Player) e.getDamager();
				if(!isBrawler(p) || !isBrawler(d)) {
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onCombat(EntityDamageEvent e) {
		if(gameRunning()) {
			if(e.getEntity() instanceof Player && Minigames.getPlayerList().contains(((Player)e.getEntity()).getName())  ) {
				e.setDamage(0);
			}
		}
	}

	@Override
	public void killPlayer(Player p, Player k) {
		if(gameRunning()) {
		p.teleport(Minigames.getGameWorldFile().getLocation("lobby"));
		p.setGameMode(GameMode.SPECTATOR);	
			p.setHealth(p.getMaxHealth());
			if(players.contains(p.getName())) {
				players.remove(p.getName());
				if(thisRound.contains(p.getName())) {
					thisRound.remove(p.getName());
				}
			}
			if(isBrawler(p)) {
				Minigames.Broadcast(false, "&6&o" + p.getName() + " &7fell off!");
				run();
			}
		}
	}

	@Override
	public void playerLeave(Player p) {
		if(gameRunning()) {
			p.setHealth(p.getMaxHealth());
			if(players.contains(p.getName())) {
				players.remove(p.getName());
				if(thisRound.contains(p.getName())) {
					thisRound.remove(p.getName());
				}
			}
			if(isBrawler(p)) {
				run();
			}
		}
	}

}
