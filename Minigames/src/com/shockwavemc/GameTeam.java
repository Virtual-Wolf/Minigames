package com.shockwavemc;

import org.bukkit.ChatColor; 
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class GameTeam {

	private Team scoreTeam;
	public String name;
	public Team getScoreboardTeam() {
		return scoreTeam;
	}
	public void setScoreboardTeam(Team t) {
		scoreTeam = t;
	}
	private ChatColor colour;
	public ChatColor getColour() {
		return colour;
	}
	public void setColour(ChatColor c) {
		colour = c;
		scoreTeam.setPrefix(c + "");
	}
	
	@SuppressWarnings("deprecation")
	public void add(Player p) {
		Minigames.messageToPlayer(p, true, colour + "Joined team " + name);
		scoreTeam.addPlayer(p);
	}
	
	@SuppressWarnings("deprecation")
	public void remove(Player p) {
		Minigames.messageToPlayer(p, true, ChatColor.RED + "Left team " + name);
		scoreTeam.removePlayer(p);
	}
	
	public GameTeam(String name, ChatColor c, boolean friendlyFire) {
		this.name = name;
		scoreTeam = Minigames.getBoard().registerNewTeam(name);
		scoreTeam.setAllowFriendlyFire(friendlyFire);
		scoreTeam.setCanSeeFriendlyInvisibles(!friendlyFire);
		scoreTeam.setDisplayName(c + name);
		setColour(c);
	}
}
