package gr.platformertest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gr.platformertest.backgrounds.ParallaxBackground;
import gr.platformertest.backgrounds.ParallaxLayer;
import gr.platformertest.consts.MyInputs;
import gr.platformertest.managers.MyInputManager;
import gr.platformertest.managers.ScreenManager;

public class Screen1 extends GraniteScreen{
	private SpriteBatch sb;
	private Texture playerTex;
	private Sprite player;
	private OrthographicCamera cam;
	private float playerSpeed;
	private float bgStep;	// How far from  left bg has gone
	private float lerp;
	
	private ParallaxLayer plOne, plTwo, plThree, plFour;
	private ParallaxLayer[] parallaxLayers;
	private ParallaxBackground background;
	
	
	public Screen1(ScreenManager screenManager) {
		super(screenManager);
		sb = screenManager.getGame().getSpiteBatch();
		
		plOne = new ParallaxLayer(new TextureRegion(new Texture("backgrounds/bg1sky.png")), new Vector2(0,0), new Vector2(0,0));
		plTwo = new ParallaxLayer(new TextureRegion(new Texture("backgrounds/bg2.png")), new Vector2(.1f,0), new Vector2(0,0));
		plThree = new ParallaxLayer(new TextureRegion(new Texture("backgrounds/bg3.png")), new Vector2(5,0), new Vector2(0,0));
		plFour = new ParallaxLayer(new TextureRegion(new Texture("backgrounds/bg4.png")), new Vector2(10,0), new Vector2(0,0));
		parallaxLayers = new ParallaxLayer[4];
		parallaxLayers[0] = plOne;
		parallaxLayers[1] = plTwo;
		parallaxLayers[2] = plThree;
		parallaxLayers[3] = plFour;
				
		playerTex = new Texture("player.png");
		player = new Sprite(playerTex);
		player.setPosition(0, 16);
		
		playerSpeed = 300;
		bgStep = 0;
		lerp = .1f;
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(cam.viewportWidth /2, cam.viewportHeight /2, 0);
		
		background = new ParallaxBackground(parallaxLayers, cam.viewportWidth, cam.viewportHeight, new Vector2(0,0), sb);
		
		Gdx.input.setInputProcessor(new MyInputManager());
	}

	@Override
	public void update(float dt) {
		cam.update();
		handlePlayerInput(dt);
		handleCameraPosition(dt);
		
//		System.out.println("bgCam = " + background.getCamera().position.x);
//		System.out.println("bgStep = " + bgStep);
//		System.out.println("player camera = " + player.getX());
//		System.out.println("camera X = " + cam.position.x);
//		System.out.println("\n");
	}

	@Override
	public void render(float dt) {
		Gdx.gl.glClearColor(0, 0, 1, .15f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		sb.end();
		background.render(dt);
		
		sb.setProjectionMatrix(cam.combined);
		
		sb.enableBlending();
		sb.begin();
		
		player.setColor(player.getColor());
		player.setAlpha(.31f);
		if(player.getX() <= 0)			//We put this trick in render method so player stays at pixel 5;
			player.setX(0);
		sb.draw(player, player.getX(), player.getY());
		
		//player.draw(sb);
	}

	@Override
	public void dispose() {		
		playerTex.dispose();
		for(int i=0; i<parallaxLayers.length; i++) {
			parallaxLayers[i].getTexture().getTexture().dispose();
		}
	}

	@Override
	public void resize(int width, int height) {		
		cam.viewportWidth = width;
		cam.viewportHeight = height;
		cam.update();
	}
	
	// Handle input
	private void handlePlayerInput(float dt) {
		background.setBackgroundSpeed(0, 0);
		player.translate(0, 0);
		
		if(MyInputs.isMyKeyDown(MyInputs.RIGHT_KEY)) {
			player.translate(playerSpeed * dt, 0);
		}
		if(MyInputs.isMyKeyDown(MyInputs.LEFT_KEY)) {
			player.translate(-playerSpeed * dt, 0);
		}
	}
	
	// Handle Camera
	// LERP=======> cam.pos = cam.pos + (player.pos - cam.pos) * lerp	
	private void handleCameraPosition(float dt) {
		
		if(player.getX() > cam.viewportWidth /2) {
			cam.position.x = player.getX();
			background.setCameraPos(background.getCamera().position.x + ((player.getX() - cam.viewportWidth /2) /10 - background.getCamera().position.x) * lerp , 0);
		}
			
		else
			cam.position.x = cam.viewportWidth /2;		
	}	
}// END