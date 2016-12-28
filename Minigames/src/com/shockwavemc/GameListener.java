package com.shockwavemc;

import org.bukkit.event.Listener;

public class GameListener implements Listener {
	
	public GameListener(Minigames mgs) {
		mgs.getServer().getPluginManager().registerEvents(this, mgs);
	}
	
}
