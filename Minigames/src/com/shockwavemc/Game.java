package com.shockwavemc;

public enum Game {
;
	Game(Minigame g, String n, boolean solo, boolean res, GameTeam[] t) {
		game = g;
		name = n;
		this.solo = solo;
		respawn = res;
		teams = t;
	}
	public Minigame game;
	public String name;
	public boolean solo;
	public boolean respawn;
	public GameTeam[] teams;
}
