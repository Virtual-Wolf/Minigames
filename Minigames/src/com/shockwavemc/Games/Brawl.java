package com.shockwavemc.Games;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.shockwavemc.Game;
import com.shockwavemc.Minigame;
import com.shockwavemc.Minigames;
import com.shockwavemc.Utils.WorldFile;

public class Brawl implements Minigame, Listener {
	
	public Brawl(Minigames mgs) {
		mgs.getServer().getPluginManager().registerEvents(this, mgs);
	}
	
	private String p1, p2;
	private ArrayList<String> players = new ArrayList<String>();

	@SuppressWarnings("deprecation")
	@Override
	public void start() {
		WorldFile wf = new WorldFile(Minigames.getGameWorld());
		for(String s : Minigames.getPlayerList()) {
			Bukkit.getPlayer(s).teleport(wf.getLocation("lobby"));
		}
		run();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void teamSplit() {
		for(String s : Minigames.getPlayerList()) {
			Minigames.getTeamList().get(0).add(Bukkit.getPlayer(s));
			players.add(s);
		}
	}
	
	private ArrayList<String> thisRound = new ArrayList<String>();
	Random r = new Random();
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		WorldFile wf = new WorldFile(Minigames.getGameWorld());
		if(players.size() > 1) {
			thisRound = players;
			if(thisRound.size() > 1) {
				p1 = thisRound.get(r.nextInt(thisRound.size()));
				thisRound.remove(p1);
				p2 = thisRound.get(r.nextInt(thisRound.size()));
				thisRound.remove(p2);
				Minigames.Broadcast(false, "&c&o" + p1 + "&7 vs &b&o" + p2);
				Bukkit.getPlayer(p1).teleport(wf.getLocation("spawn1"));
				Bukkit.getPlayer(p2).teleport(wf.getLocation("spawn2"));
			} else {
				thisRound.clear();
				thisRound = players;
				run();
			}
		} else {
			//end game
		}
	}
	
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
	public void playerLeave(PlayerQuitEvent e) {
		String n = e.getPlayer().getName();
		
	}
	
	@EventHandler
	public void onCombat(EntityDamageByEntityEvent e) {
		if(gameRunning()) {
			if(e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
				e.setDamage(0);
				Player p = (Player) e.getEntity();
				Player d = (Player) e.getDamager();
				if(!isBrawler(p) || !isBrawler(d)) {
					e.setCancelled(true);
				}
			}
		}
	}

	@Override
	public void killPlayer(Player p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void killPlayer(String n) {
		// TODO Auto-generated method stub
		
	}

}
