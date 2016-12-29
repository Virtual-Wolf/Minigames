package com.shockwavemc;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class GameTeam {

	private Team scoreTeam;
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
		scoreTeam.addPlayer(p);
	}
	
	public GameTeam(String name, ChatColor c, boolean friendlyFire) {
		scoreTeam = Minigames.getBoard().registerNewTeam(name);
		scoreTeam.setAllowFriendlyFire(friendlyFire);
		scoreTeam.setCanSeeFriendlyInvisibles(!friendlyFire);
		scoreTeam.setDisplayName(c + name);
		setColour(c);
	}
}
