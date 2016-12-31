package com.shockwavemc;

import com.shockwavemc.Games.*;

import org.bukkit.ChatColor;

public enum Game {
	Brawl(new Brawl(Minigames.instance), "Brawl", "", true, false, new GameTeam[] {}),
	Gladiators(new Gladiators(), "Gladiators", "", true, false, new GameTeam[] {
			new GameTeam("Red", ChatColor.RED, false),
			new GameTeam("Blue", ChatColor.BLUE, false),
			new GameTeam("Yellow", ChatColor.YELLOW, false),
			new GameTeam("Green", ChatColor.GREEN, false)});

	
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
