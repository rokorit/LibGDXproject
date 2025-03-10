package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		Graphics.DisplayMode primaryMode = Lwjgl3ApplicationConfiguration.getDisplayMode();
		Graphics graphics = Gdx.graphics;
		config.setForegroundFPS(60);
		config.setTitle("test1");
		config.setFullscreenMode(primaryMode);
		config.setResizable(false);


		config.useVsync(true);
		new Lwjgl3Application(new Drop(), config);
	}
}
