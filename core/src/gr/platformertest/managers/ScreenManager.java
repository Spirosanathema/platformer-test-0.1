package gr.platformertest.managers;

import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import gr.platformertest.MyGame;
import gr.platformertest.consts.MyInputs;
import gr.platformertest.screens.GraniteScreen;
import gr.platformertest.screens.Screen0;
import gr.platformertest.screens.Screen1;
import gr.platformertest.screens.Screen2;
import gr.platformertest.screens.Screen3;
import gr.platformertest.screens.Screen4;

public class ScreenManager {
	private MyGame game;
	private Stack<GraniteScreen> screens;
	
	private final int SCREEN0 = 0;	//yellow
	private final int SCREEN1 = 1;	//green
	private final int SCREEN2 = 2;	//blue
	private final int SCREEN3 = 3;	//light blue
	private final int SCREEN4 = 4;	//pink
	private int currentScreen;
	
	public ScreenManager(MyGame game) {
		this.game = game;
		screens = new Stack<GraniteScreen>();
		
		currentScreen = 0;
		pushScreen(currentScreen);
		
		Gdx.input.setInputProcessor(new MyInputManager());
	}	
	
	//---------------------------------- 4 Screen Manager Methods (IMPORTANT!!!) -----------------------------------
		public void popScreenAndDispose() {
			GraniteScreen graniteScreen = screens.pop();			// 1
			graniteScreen.dispose();
		}
		
		public GraniteScreen getScreenFromUser(int state) {			//	  2
			if(state == SCREEN0) {
				//currentScreen = SCREEN0;
				return new Screen0(this);
			}
			
			else if(state == SCREEN1) {
				//currentScreen = SCREEN1;
				return new Screen1(this);
			}
			
			else if(state == SCREEN2) {
				//currentScreen = SCREEN2;
				return new Screen2(this);
			}
			
			else if(state == SCREEN3) {
				//currentScreen = SCREEN3;
				return new Screen3(this);
			}
			
			else if(state == SCREEN4) {
				//currentScreen = SCREEN4;
				return new Screen4(this);
			}
			else {
				//currentScreen = SCREEN0;
				return new Screen0(this);
			}
		}
		
		
		public void pushScreen(int state) {							//    3
			screens.push(getScreenFromUser(state));
		}
		public void setScreenToStack(int state) {					//   4
			popScreenAndDispose();
			pushScreen(state);
		}
	//--------------------------------------------------------------------------------------------------------------
	//------------------------------ InputHandler for ScreenManager ------------------------------------------------
		
		public void inputHandler(float dt) {
			
			if(MyInputs.isMyKeyPressed(MyInputs.F1_KEY)) {
				if(currentScreen < 4) {
					popScreenAndDispose();
					pushScreen(++currentScreen);
				}
				else {
					popScreenAndDispose();
					currentScreen = 0;
					pushScreen(currentScreen);
				}
			}
		}
		
	//--------------------------------------------------------------------------------------------------------------
	
		
	public void update(float dt) {				// UPDATE for ScreenManager
		screens.peek().update(dt);
		inputHandler(dt);
		MyInputs.myInputUpdate();				// Update MyInputs AFTER InputHandler (important!!!)
	}		
		
	public void render(float dt) {				// RENDER for ScreenManager
		screens.peek().render(dt);
	}
	
	public void dispose() {
		
	}
	
	
	////////////////////////// GETTERS - SETTERS //////////////////////////////////
	public MyGame getGame() { return game; }
	
}// END
