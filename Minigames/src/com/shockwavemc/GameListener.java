package com.shockwavemc;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class GameListener implements Listener {
	
	public GameListener(Minigames mgs) {
		mgs.getServer().getPluginManager().registerEvents(this, mgs);
	}
	
	@EventHandler
	public void chat(AsyncPlayerChatEvent e) {
		e.setFormat(e.getFormat().replaceFirst("<", "").replaceFirst(">", ":"));
	}
	
	@EventHandler
	public void weather(WeatherChangeEvent e) {
		if(e.getWorld().getName().contains("game")) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void move(PlayerMoveEvent e) {
		if(e.getPlayer().getLocation().getY() <= 80) {
			Minigames.killPlayer(e.getPlayer());
		}
	}
	
	@EventHandler
	public void onCombat(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player && !Minigames.inGame && Minigames.getPlayerList().contains(((Player)e.getEntity()).getName())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void hunger(FoodLevelChangeEvent e) {
		if(Minigames.getPlayerList().contains(((Player)e.getEntity()).getName())) {
			e.setCancelled(true);
 		}
	}

}
