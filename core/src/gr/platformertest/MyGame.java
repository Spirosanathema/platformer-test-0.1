package gr.platformertest;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import gr.platformertest.consts.MyInputs;
import gr.platformertest.managers.ScreenManager;
import static gr.platformertest.consts.MyConsts.STEP;

public class MyGame extends ApplicationAdapter {	//700 x 400
	private SpriteBatch batch;
	private ScreenManager screenManager;
	
	private float accum;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		screenManager = new ScreenManager(this);
	}
	
	public void update(float dt) {
		
		screenManager.update(STEP);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
	//................................................	
		accum += Gdx.graphics.getDeltaTime();
		while(accum >= STEP) {
			accum -=STEP;
			update(STEP);
		}
	//................................................	
		
		screenManager.render(STEP);
		
		batch.end();
	}
	
	@Override
	public void dispose () {
		screenManager.dispose();
		batch.dispose();
	}
	@Override
	public void resize(int width, int height) {
		screenManager.resize(width, height);
	}
	
	/////////////////////////////// GETTERS - SETTERS //////////////////////////////////////
	public SpriteBatch getSpiteBatch() { return batch; }
	
	
}// END
