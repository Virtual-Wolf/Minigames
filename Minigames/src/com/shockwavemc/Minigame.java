package com.shockwavemc;

import org.bukkit.entity.Player;

public interface Minigame {
	public void teamSplit();
	public void start();
	public void run();
	public void killPlayer(Player p);
	public void killPlayer(String n);
}
