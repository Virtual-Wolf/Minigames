package com.shockwavemc;

import org.bukkit.Color;
import org.bukkit.scoreboard.Team;

public class GameTeam {

	private Team scoreTeam;
	public Team getScoreboardTeam() {
		return scoreTeam;
	}
	public void setScoreboardTeam(Team t) {
		scoreTeam = t;
	}
	private Color colour;
	public Color getColour() {
		return colour;
	}
	public void setColour(Color c) {
		colour = c;
		scoreTeam.setPrefix(c + "");
	}
	
	public GameTeam(Color c) {
		
	}
}
