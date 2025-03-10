package com.mygdx.game;

import com.badlogic.gdx.Game;


public class Drop extends Game {
	@Override
	public void create () {
		this.setScreen(new MainMenu(this, 0));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {

	}
}
