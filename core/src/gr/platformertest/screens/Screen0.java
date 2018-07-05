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

public class Screen0 extends GraniteScreen{
	private SpriteBatch sb;
	private Array<Texture> bgTextures;
	private Array<Sprite> bgSprites;
	private Texture playerTex;
	private Sprite player;
	private OrthographicCamera cam;
	private int currentFrame, pPosition, nPosition, cPosition;
	private float playerSpeed;
	private float lerp;
	
	
	public Screen0(ScreenManager screenManager) {
		super(screenManager);
		sb = screenManager.getGame().getSpiteBatch();
		
		//load background array
		bgTextures = new Array<Texture>();
		bgTextures.add(new Texture("mainbg1-3.png"));
		bgTextures.add(new Texture("mainbg2-3.png"));
		bgTextures.add(new Texture("mainbg3-3.png"));
		
		//create background sprites from bg array
		bgSprites = new Array<Sprite>();
		for(int i=0; i<bgTextures.size; i++) {
			bgSprites.add(new Sprite(bgTextures.get(i)));
			bgSprites.get(i).setPosition(i * Gdx.graphics.getWidth(), 0);
		}
		
		playerTex = new Texture("player.png");
		player = new Sprite(playerTex);
		player.setPosition(0, 16);
		
		//cam = new OrthographicCamera(Gdx.graphics.getWidth() /2, Gdx.graphics.getHeight() /2);
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(cam.viewportWidth /2, cam.viewportHeight /2, 0);
		
		playerSpeed = 300;
		lerp = 0.1f;
		currentFrame = 0;
		pPosition = 0;
		nPosition = 0;
		cPosition = 0;
		
		Gdx.input.setInputProcessor(new MyInputManager());
	}

	@Override
	public void update(float dt) {
		cam.update();
		currentFrame = Math.abs((int)(player.getX() + player.getWidth() /2) / 700);
		handleCameraPosition(dt);
		handleInput(dt);
		cameraTransition(dt);
	}

	@Override
	public void render(float dt) {
		Gdx.gl.glClearColor(0, 0, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sb.setProjectionMatrix(cam.combined);
		
		//draw background
		sb.draw(bgTextures.get(pPosition), currentFrame * cam.viewportWidth - cam.viewportWidth, 0);
		sb.draw(bgTextures.get(cPosition), currentFrame * cam.viewportWidth, 0);
		sb.draw(bgTextures.get(nPosition), currentFrame * cam.viewportWidth + cam.viewportWidth, 0);
		
		
		// Draw Player
		sb.draw(player, player.getX(), player.getY());
	}

	@Override
	public void dispose() {
		for(int i=0; i<bgTextures.size; i++) {
			bgTextures.get(i).dispose();
		}
		playerTex.dispose();
	}

	@Override
	public void resize(int width, int height) {
		cam.viewportWidth = width;
		cam.viewportHeight = height;
		cam.position.set(cam.viewportWidth /2, cam.viewportHeight /2, 0);
		cam.update();
	}
	
	// Handle input
	private void handleInput(float dt) {
		if(player.getX() < 0)
			player.setX(0);
		
		if(MyInputs.isMyKeyDown(MyInputs.RIGHT_KEY)) {
			player.setX(player.getX() + playerSpeed * dt );
		}
		if(MyInputs.isMyKeyDown(MyInputs.LEFT_KEY)) {
			player.setX(player.getX() - playerSpeed * dt);
		}
	}
	
	// Handle Camera
	// LERP=======> cam.pos = cam.pos + (player.pos - cam.pos) * lerp
	private void handleCameraPosition(float dt) {
		if(player.getX() > cam.viewportWidth /2)
			cam.position.x = cam.position.x + (player.getX() - cam.position.x) * lerp;
			//cam.position.x = player.getX();
		else
			cam.position.x = cam.position.x + (cam.viewportWidth /2 - cam.position.x) * lerp;
	}
	
	
	private void cameraTransition(float dt) {
		cPosition = Math.abs(currentFrame % bgTextures.size );
		
		nPosition = cPosition + 1;
		if(nPosition > bgTextures.size - 1)
			nPosition = 0;
		
		pPosition = cPosition - 1;
		if(pPosition < 0)
			pPosition = bgTextures.size - 1;
		
	}
}// END