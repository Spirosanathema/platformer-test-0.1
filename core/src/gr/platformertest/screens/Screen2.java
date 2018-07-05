package gr.platformertest.screens;

import static gr.platformertest.consts.MyConsts.STEP;
import static gr.platformertest.consts.MyConsts.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import gr.platformertest.managers.MyBox2DManager;

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
	
	private World world;
	private Box2DDebugRenderer b2drenderer;
	private Body playerBody;
	
	private float playerSpeed;
	private float bgStep;	// How far from  left bg has gone
	private float lerp;
	
	private final float GRAVITY = -9.81f;
	private final float V_HEIGHT = 4f;
	
	private ParallaxLayer plOne, plTwo, plThree, plFour;
	private ParallaxLayer[] parallaxLayers;
	private ParallaxBackground background;
	
	
	public Screen2(ScreenManager screenManager) {
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
		player.setPosition(0, 0);
		player.setSize(.4f, .6f);
		player.setOriginCenter();
		
		playerSpeed = 5f;
		bgStep = 0;
		lerp = .1f;
		
		// Potato Pixels for camera: VIRTUAL_HEIGHT * width / (float)height
		cam = new OrthographicCamera((V_HEIGHT * Gdx.graphics.getWidth()) /Gdx.graphics.getHeight(), V_HEIGHT);
		cam.position.set(cam.viewportWidth /2, cam.viewportHeight /2, 0);
		
		//background = new ParallaxBackground(parallaxLayers, cam.viewportWidth, cam.viewportHeight, new Vector2(0,0), sb);
		background = new ParallaxBackground(parallaxLayers, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new Vector2(0,0), sb);
		
		world = new World(new Vector2(0, GRAVITY), false);
		b2drenderer = new Box2DDebugRenderer();
		
		playerBody = MyBox2DManager.createPolygonBody(true, 0, 0, player.getWidth(), player.getHeight(), world, b2drenderer);
		
		Gdx.input.setInputProcessor(new MyInputManager());
	}

	@Override
	public void update(float dt) {
		world.step(STEP, 6, 2);
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
		
		b2drenderer.render(world, cam.combined);
		
		sb.enableBlending();
		sb.begin();
		
		player.setColor(player.getColor());
		player.setAlpha(.31f);
//		if(player.getX() <= 5)			//We put this trick in render method so player stays at pixel 5;
//			player.setX(5);
		sb.draw(player, player.getX(), player.getY(), player.getWidth(), player.getHeight());
		//sb.draw(player, 0, 0, .8f, 1.1f);
		
		//player.draw(sb);
	}

	@Override
	public void dispose() {		
		playerTex.dispose();
	}

	@Override
	public void resize(int width, int height) {	
		//System.out.println("width = "+width);
		
//		cam.viewportWidth = width;
//		cam.viewportHeight = height;
//		cam.update();
		cam = new OrthographicCamera((V_HEIGHT * (float)width) /(float)height, V_HEIGHT);
		cam.position.set(cam.viewportWidth /2, cam.viewportHeight /2, 0);
		background = new ParallaxBackground(parallaxLayers, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new Vector2(0,0), sb);
		background.getCamera().viewportWidth = (float)width;
		background.getCamera().viewportHeight = (float)height;
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
			cam.position.x = Gdx.graphics.getWidth() /2;		
	}	
}// END