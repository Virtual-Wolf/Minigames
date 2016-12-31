package com.shockwavemc;

import org.bukkit.entity.Player;

public interface Minigame {
	public void teamSplit();
	public void start();
	public void run();
	public boolean gameRunning();
	public void killPlayer(Player p, Player k);
	public void playerLeave(Player p);
}
