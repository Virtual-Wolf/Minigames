package com.shockwavemc.Games;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.shockwavemc.Game;
import com.shockwavemc.GameTeam;
import com.shockwavemc.Minigame;
import com.shockwavemc.Minigames;
import com.shockwavemc.Utils.ItemMetaUtils;

public class Gladiators implements Minigame {
	
	private HashMap<String, String> inArena = new HashMap<String, String>();
	private ArrayList<String> red = new ArrayList<String>();
	private ArrayList<String> blue = new ArrayList<String>();
	private ArrayList<String> yellow = new ArrayList<String>();
	private ArrayList<String> green = new ArrayList<String>();

	Random r = new Random();
	
	@SuppressWarnings("deprecation")
	@Override
	public void teamSplit() {
		ArrayList<String> noTeam = new ArrayList<String>();
		for(String s : Minigames.getPlayerList()) {
			noTeam.add(s);
		}
		while(noTeam.size() > 0) {
			for(GameTeam gt : Minigames.getTeamList()) {
				if(noTeam.size() > 0) {
					int i = r.nextInt(noTeam.size());
					Player p = Bukkit.getPlayer(noTeam.get(i));
					gt.add(p);
					if(gt.name.equalsIgnoreCase("red")) {
						red.add(p.getName());
					} else if(gt.name.equalsIgnoreCase("blue")) {
						blue.add(p.getName());
					} else if(gt.name.equalsIgnoreCase("yellow")) {
						yellow.add(p.getName());
					} else if(gt.name.equalsIgnoreCase("green")) {
						green.add(p.getName());
					}
					noTeam.remove(p.getName());
					p.teleport(Minigames.getGameWorldFile().getLocation(gt.name.toLowerCase() + "-spawn"));
				} else {
					return;
				}
			}
		}
	}
	
	public ArrayList<String> getAliveTeams() {
		ArrayList<String> teams = new ArrayList<String>();
		if(red.size() > 0)
			teams.add("Red");
		if(blue.size() > 0)
			teams.add("Blue");		
		if(yellow.size() > 0)
			teams.add("Yellow");	
		if(green.size() > 0)
			teams.add("Green");	
		return teams;
	}

	@Override
	public void start() {
		run();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		if(getAliveTeams().size() > 1) {
			for(String t : getAliveTeams()) {
				if(inArena.get(t) == null) {
					String s = "";
					Color c = Color.GRAY;
					if(t == "Red") {
						s = red.get(r.nextInt(red.size()));
						c = Color.RED;
					}
					if(t == "Blue") {
						s = blue.get(r.nextInt(blue.size()));
						c = Color.BLUE;
					}
					if(t == "Yellow") {
						s = yellow.get(r.nextInt(yellow.size()));
						c = Color.YELLOW;
					}
					if(t == "Green") {
						s = green.get(r.nextInt(green.size()));
						c = Color.LIME;
					}			
					Player p = Bukkit.getPlayer(s);
					p.teleport(Minigames.getGameWorldFile().getLocation(t.toLowerCase() + "-arena"));
					p.getInventory().addItem(new ItemStack(Material.STONE_SWORD));
					p.getInventory().setChestplate(ItemMetaUtils.setLeatherColor(new ItemStack(Material.LEATHER_CHESTPLATE), c));
				}
			}
		} else {
			String t = getAliveTeams().get(0);
			Minigames.end((t == "Red" ? ChatColor.RED : t == "Blue" ? ChatColor.BLUE : t == "Yellow" ? ChatColor.YELLOW : t == "Green" ? ChatColor.GREEN : "") + t);
		}
	}
	
	@Override
	public boolean gameRunning() {
		return (Minigames.inGame && Minigames.getCurrentGame() == Game.Gladiators);
	}
	
	public ChatColor getPlayersTeam(Player p) {
		ChatColor t = ChatColor.GRAY;
		if(red.contains(p.getName())) 
			t = ChatColor.RED;
		if(blue.contains(p.getName())) 
			t = ChatColor.BLUE;
		if(yellow.contains(p.getName())) 
			t = ChatColor.YELLOW;
		if(green.contains(p.getName())) 
			t = ChatColor.GREEN;
		return t;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void killPlayer(Player p, Player k) {
		if(gameRunning()) {
			p.setHealth(p.getMaxHealth());
			for(GameTeam gt : Minigames.getTeamList()) {
				if(gt.getScoreboardTeam().getPlayers().contains(p)) {
					gt.remove(p);
					if(gt.name.equalsIgnoreCase("red")) {
						red.remove(p.getName());
					} else if(gt.name.equalsIgnoreCase("blue")) {
						blue.remove(p.getName());
					} else if(gt.name.equalsIgnoreCase("yellow")) {
						yellow.remove(p.getName());
					} else if(gt.name.equalsIgnoreCase("green")) {
						green.remove(p.getName());
					}
					p.teleport(Minigames.getGameWorldFile().getLocation("lobby"));
					Minigames.Broadcast(false, gt.getColour() + p.getName() + "&7 " + (k == null ? "died" : "was slaughtered by " + (getPlayersTeam(k)) + k.getName()) + "&7.");
					run();
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void playerLeave(Player p) {
		if(gameRunning()) {
			p.setHealth(p.getMaxHealth());
			for(GameTeam gt : Minigames.getTeamList()) {
				if(gt.getScoreboardTeam().getPlayers().contains(p)) {
					gt.remove(p);
					if(gt.name.equalsIgnoreCase("red")) {
						red.remove(p.getName());
					} else if(gt.name.equalsIgnoreCase("blue")) {
						blue.remove(p.getName());
					} else if(gt.name.equalsIgnoreCase("yellow")) {
						yellow.remove(p.getName());
					} else if(gt.name.equalsIgnoreCase("green")) {
						green.remove(p.getName());
					}
					run();
				}
			}
		}
	}
	
}
