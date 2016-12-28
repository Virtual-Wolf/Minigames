package com.shockwavemc;

import com.shockwavemc.Games.*;
import org.bukkit.Color;

public enum Game {
	Brawl(new Brawl(), "Brawl", "", true, false, new GameTeam[] {new GameTeam("Players", Color.YELLOW, true)});
	
	
	
	
	Game(Minigame g, String n, String desc,  boolean solo, boolean res, GameTeam[] t) {
		game = g;
		name = n;
		description = desc;
		this.solo = solo;
		respawn = res;
		teams = t;
	}
	public Minigame game;
	public String name;
	public String description;
	public boolean solo;
	public boolean respawn;
	public GameTeam[] teams;
}
