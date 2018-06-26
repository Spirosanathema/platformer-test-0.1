package gr.platformertest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import gr.platformertest.backgrounds.ParallaxBackground;
import gr.platformertest.backgrounds.ParallaxLayer;
import gr.platformertest.consts.MyInputs;
import gr.platformertest.managers.MyInputManager;
import gr.platformertest.managers.ScreenManager;

public class Screen2 extends GraniteScreen{
	private SpriteBatch sb;
	private Texture playerTex;
	private Sprite player;
	private OrthographicCamera cam;
	private boolean playerLooksRight;
	private boolean playerLooksLeft;
	private int currentBgFrame;
	
	private ParallaxLayer plOne;
	private ParallaxLayer[] parallaxLayers;
	private ParallaxBackground background;
	private Vector2 bgSpeed;
	private int pLayersWidth;
	private float bgStep;		// how much left (or right) the bg moved from starting point
	
	
	public Screen2(ScreenManager screenManager) {
		super(screenManager);
		sb = screenManager.getGame().getSpiteBatch();
		
		plOne = new ParallaxLayer(new TextureRegion(new Texture("mainbg.png")), new Vector2(5,0), new Vector2(0,0));
		parallaxLayers = new ParallaxLayer[1];
		parallaxLayers[0] = plOne;
		
		bgSpeed = new Vector2(0,0);
		background = new ParallaxBackground(parallaxLayers, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), bgSpeed, sb);
				
		playerTex = new Texture("player.png");
		player = new Sprite(playerTex);
		player.setPosition(50, 16);
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(Gdx.graphics.getWidth() /2, Gdx.graphics.getHeight() /2, 0);
		
		playerLooksRight = true;
		playerLooksLeft = false;
		
		currentBgFrame = 0;
		pLayersWidth = 0;
		bgStep = 0;
		
		for(int i=0; i<parallaxLayers.length; i++) {
			pLayersWidth += parallaxLayers[i].region.getRegionWidth();
		}
		
		Gdx.input.setInputProcessor(new MyInputManager());
	}

	@Override
	public void update(float dt) {
		cam.update();
		currentBgFrame = (int)(player.getX() / Gdx.graphics.getWidth());
		bgStep = (bgStep + 50 * dt) % pLayersWidth;
		//cameraLeftBound();
		handlePlayerInput(dt);
		handleCameraPosition(dt);
		
		System.out.println("player x = " + player.getX());
		System.out.println("cam x = " + cam.position.x);
		System.out.println("background cam width = " + background.getCamera().viewportWidth);
		System.out.println("region width = " + pLayersWidth);
		//System.out.println("background speed = " + background.getBackgroundSpeed().x);
		System.out.println("bgStep = " + bgStep);
		System.out.println("backgroundg cam x = " + background.getCamera().position.x + "\n");
	}

	@Override
	public void render(float dt) {
		Gdx.gl.glClearColor(0, 0, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		sb.end();
		background.render(dt);
		
		sb.setProjectionMatrix(cam.combined);
		
		sb.begin();
		
		sb.draw(player, player.getX(), player.getY());
	}

	@Override
	public void dispose() {		
		
	}

	@Override
	public void resize(int width, int height) {		
		
	}
	
	// Handle input
	private void handlePlayerInput(float dt) {
		
		if(MyInputs.isMyKeyDown(MyInputs.RIGHT_KEY) && player.getX() <= pLayersWidth-player.getWidth()) {
			playerLooksRight = true;
			playerLooksLeft = false;
			player.translate(5, 0);
		}
		if(MyInputs.isMyKeyDown(MyInputs.LEFT_KEY) && player.getX() >= 5) {
			playerLooksRight = false;
			playerLooksLeft = true;
			player.translate(-5, 0);
		}
	}
	
	// Handle Camera
	// LERP=======> cam.pos = cam.pos + (player.pos - cam.pos) * lerp
	private void handleCameraPosition(float dt) {
		background.setBackgroundSpeed(0,0);
		if(playerLooksRight) {
			if(cam.position.x < player.getX()) {
				cam.position.x = player.getX();
				background.setBackgroundSpeed(50, 0);
			}			
		}
		
		if(playerLooksLeft) {
			if(player.getX() > Gdx.graphics.getWidth() /2) {
				cam.position.x = player.getX();
				background.setBackgroundSpeed(-50, 0);
			}
		}
	}
	private void cameraLeftBound() {
		if(player.getX() < Gdx.graphics.getWidth() /2)
			cam.position.x = Gdx.graphics.getWidth() /2;
		if(player.getX() > (pLayersWidth - Gdx.graphics.getWidth() /2)) {
			cam.position.x = (float)(pLayersWidth - Gdx.graphics.getWidth() /2);
		}
	}
}
