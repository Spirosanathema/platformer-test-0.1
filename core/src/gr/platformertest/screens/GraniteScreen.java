package gr.platformertest.screens;

import gr.platformertest.MyGame;
import gr.platformertest.managers.ScreenManager;

public abstract class GraniteScreen {
	private MyGame game;
	
	public GraniteScreen(ScreenManager screenManager) {
		this.game = screenManager.getGame();
	}
	
	
	public abstract void update(float dt);
	public abstract void render(float dt);
	public abstract void dispose();
	public abstract void resize(int width, int height);
	
	/////////////////////////////// GETTERS - SETTERS /////////////////////////////////
	public MyGame getGame() { return game; }
	
	
}// END
