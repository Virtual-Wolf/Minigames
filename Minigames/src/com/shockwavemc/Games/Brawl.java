package com.shockwavemc.Games;

import com.shockwavemc.Minigame;
import com.shockwavemc.Minigames;

public class Brawl implements Minigame {

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void teamSplit() {
		for(String s : Minigames.getPlayerList()) {
			//add player to 'Players' team
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
