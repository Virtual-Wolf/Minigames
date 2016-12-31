package com.shockwavemc;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class GameListener implements Listener {
	
	public GameListener(Minigames mgs) {
		mgs.getServer().getPluginManager().registerEvents(this, mgs);
	}
	
	
	@EventHandler
	public void playerLeave(PlayerQuitEvent e) {
	 if(Minigames.getPlayerList().contains(e.getPlayer().getName()))
			 Minigames.playerLeave(e.getPlayer());
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
		if(Minigames.getPlayerList().contains(e.getPlayer().getName())) {
				if(e.getPlayer().getLocation().getY() <= 80) {
					Minigames.killPlayer(e.getPlayer(), null);
				}
		}
	}
	
	@EventHandler
	public void onCombat(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player)e.getEntity();
			if(Minigames.getPlayerList().contains(p.getName())) {
				if(!Minigames.inGame) {
					e.setCancelled(true);
				} else {
					if((p.getHealth() - e.getDamage()) <= 0) {
						e.setDamage(0);
						Bukkit.getScheduler().runTaskLater(Minigames.instance, new Runnable() {public void run() {Minigames.killPlayer(p, null);}}, 4);
					}
				}
			}
		} 
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void combat(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player)e.getEntity();
			if(Minigames.getPlayerList().contains(p.getName())) {
				if(Minigames.inGame) {
					if(e.getDamager() instanceof Player) {
						Player k = (Player) e.getDamager(); 
						if((p.getHealth() - e.getDamage()) <= 0) {
							e.setDamage(0);
							Bukkit.getScheduler().runTaskLater(Minigames.instance, new Runnable() {public void run() {Minigames.killPlayer(p, k);}}, 4);
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void hunger(FoodLevelChangeEvent e) {
		if(Minigames.getPlayerList().contains(((Player)e.getEntity()).getName())) {
			e.setCancelled(true);
 		}
	}

}
