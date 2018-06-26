package gr.platformertest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import gr.platformertest.consts.MyInputs;
import gr.platformertest.managers.MyInputManager;
import gr.platformertest.managers.ScreenManager;

public class Screen3 extends GraniteScreen{
	private SpriteBatch sb;
	private Array<Texture> bgTextures;
	private Array<Sprite> bgSprites;
	private Texture playerTex;
	private Sprite player;
	private OrthographicCamera cam;
	private boolean playerLooksRight;
	private boolean playerLooksLeft;
	private int bgRotationReseter;
	private float bgPosition;	//current background distance from start in pixels according to camera speed and delta time
	private float camDrawX;	// camera position for draw method
	
	
	public Screen3(ScreenManager screenManager) {
		super(screenManager);
		sb = screenManager.getGame().getSpiteBatch();
				
		bgTextures = new Array<Texture>();
		bgTextures.add(new Texture("mainbg1-3.png"));
		bgTextures.add(new Texture("mainbg2-3.png"));
		bgTextures.add(new Texture("mainbg3-3.png"));
		
		bgSprites = new Array<Sprite>();
		for(int i=0; i<bgTextures.size; i++) {
			bgSprites.add(new Sprite(bgTextures.get(i)));
			bgSprites.get(i).setPosition(i * Gdx.graphics.getWidth(), 0);
		}
		
		playerTex = new Texture("player.png");
		player = new Sprite(playerTex);
		player.setPosition(0, 0);
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(Gdx.graphics.getWidth() /2, Gdx.graphics.getHeight() /2, 0);
		
		playerLooksRight = true;
		playerLooksLeft = false;
		
		bgPosition = 0;
		bgRotationReseter = 0;
		
		Gdx.input.setInputProcessor(new MyInputManager());
	}

	@Override
	public void update(float dt) {
		cam.update();
		//bgPosition = (bgPosition + 150 * dt) % 2100;
		bgRotationReseter = ((int)bgPosition) / 700;
		player.setPosition(cam.position.x, 16);
		camDrawX = cam.position.x - cam.viewportWidth / 2;
		handleInput(dt);
		cameraLeftBound();
		handleCameraPosition(dt);	
		//System.out.println(currentBgFrame);
		//System.out.println(camDrawX);
		//System.out.println(bgRotationReseter);
		
	}

	@Override
	public void render(float dt) {
		Gdx.gl.glClearColor(0, 0, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sb.setProjectionMatrix(cam.combined);		
		
		// Render backgrounds
//		for(int i=0; i<bgTextures.size; i++) {
//			Texture texture = bgTextures.get((bgRotationReseter + i) % bgTextures.size);
//			//sb.draw(texture, -350 + -bgPosition + i * 700, 0);
//			sb.draw(texture, camDrawX-bgPosition + i * 700, 0);
//		}
		//camDrawX = cam.position.x - cam.viewportWidth / 2;
		//bgPosition = (bgPosition + 350 * dt) % 2100;
		
		sb.draw(bgTextures.get((bgRotationReseter + 2) % bgTextures.size), 0, 0);
		System.out.println(camDrawX-bgPosition + 2 * 700);
		
		sb.draw(player, player.getX(), player.getY());
	}

	@Override
	public void dispose() {
		
		
	}

	@Override
	public void resize(int width, int height) {
		
		
	}
	
	// Handle input
	private void handleInput(float dt) {
		if(MyInputs.isMyKeyDown(MyInputs.RIGHT_KEY)) {
			playerLooksRight = true;
			playerLooksLeft = false;
			bgPosition = (bgPosition + 350 * dt) % 2100;
			cam.translate(bgPosition, 0);
		}
		if(MyInputs.isMyKeyDown(MyInputs.LEFT_KEY)) {
			playerLooksRight = false;
			playerLooksLeft = true;
			player.translate(-5, 0);
		}
	}
	
	// Handle Camera
	private void handleCameraPosition(float dt) {
		
		if(playerLooksRight) {
			
		}
		
		if(playerLooksLeft) {
						
		}
	}
	private void cameraLeftBound() {
		
	}
}