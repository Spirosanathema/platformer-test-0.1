package gr.platformertest.managers;

import com.badlogic.gdx.InputProcessor;

import gr.platformertest.consts.MyInputs;

import com.badlogic.gdx.Input.Keys;

public class MyInputManager implements InputProcessor{
	
	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.UP) {
			MyInputs.setMyKey(MyInputs.UP_KEY, true);
		}
		if(keycode == Keys.DOWN) {
			MyInputs.setMyKey(MyInputs.DOWN_KEY, true);
		}
		if(keycode == Keys.LEFT) {
			MyInputs.setMyKey(MyInputs.LEFT_KEY, true);
		}
		if(keycode == Keys.RIGHT) {
			MyInputs.setMyKey(MyInputs.RIGHT_KEY, true);
		}
		if(keycode == Keys.SPACE) {
			MyInputs.setMyKey(MyInputs.SPACE_KEY, true);
		}
		if(keycode == Keys.ESCAPE) {
			MyInputs.setMyKey(MyInputs.ESC_KEY, true);
		}
		if(keycode == Keys.F1) {
			MyInputs.setMyKey(MyInputs.F1_KEY, true);
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.UP) {
			MyInputs.setMyKey(MyInputs.UP_KEY, false);
		}
		if(keycode == Keys.DOWN) {
			MyInputs.setMyKey(MyInputs.DOWN_KEY, false);
		}
		if(keycode == Keys.LEFT) {
			MyInputs.setMyKey(MyInputs.LEFT_KEY, false);
		}
		if(keycode == Keys.RIGHT) {
			MyInputs.setMyKey(MyInputs.RIGHT_KEY, false);
		}
		if(keycode == Keys.SPACE) {
			MyInputs.setMyKey(MyInputs.SPACE_KEY, false);
		}
		if(keycode == Keys.ESCAPE) {
			MyInputs.setMyKey(MyInputs.ESC_KEY, false);
		}
		if(keycode == Keys.F1) {
			MyInputs.setMyKey(MyInputs.F1_KEY, false);
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}	
	
}// END
