package gr.platformertest.backgrounds;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class ParallaxBackground {
	
	private ParallaxLayer[] layers;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Vector2 speed = new Vector2();

    /**
     * @param layers  The  background layers
     * @param width   The screenWith => camera width
     * @param height The screenHeight => camera height
     * @param speed A Vector2 attribute to point out the x and y speed => The TOTAL speed of ALL BACKGROUNDS
     */
    public ParallaxBackground(ParallaxLayer[] layers,float width,float height,Vector2 speed, SpriteBatch batch){
        this.layers = layers;
        this.speed.set(speed);
        camera = new OrthographicCamera(width, height);
        //this.batch = new SpriteBatch();
        this.batch = batch;
    }

    public void render(float delta){
        this.camera.position.add(speed.x*delta,speed.y*delta, 0);
                
        for(ParallaxLayer layer:layers){
            batch.setProjectionMatrix(camera.projection);
            batch.begin();
            float currentX = - camera.position.x*layer.parallaxRatio.x % ( layer.region.getRegionWidth() + layer.padding.x) ;

            if( speed.x < 0 )currentX += -( layer.region.getRegionWidth() + layer.padding.x);
            do{
                float currentY = - camera.position.y*layer.parallaxRatio.y % ( layer.region.getRegionHeight() + layer.padding.y) ;
                if( speed.y < 0 )currentY += - (layer.region.getRegionHeight()+layer.padding.y);
                do{
                    batch.draw(layer.region,
                            -this.camera.viewportWidth/2+currentX + layer.startPosition.x ,
                            -this.camera.viewportHeight/2 + currentY +layer.startPosition.y);
                    currentY += ( layer.region.getRegionHeight() + layer.padding.y );
                }while( currentY < camera.viewportHeight);
                currentX += ( layer.region.getRegionWidth()+ layer.padding.x);
            }while( currentX < camera.viewportWidth);
            batch.end();
        }
    }
    
    public Vector2 getBackgroundSpeed() { return speed; }
    public void setBackgroundSpeed(Vector2 speed) {
    	this.speed = speed;
    }
    public void setBackgroundSpeed(float x, float y) {
    	this.speed.x = x;
    	this.speed.y = y;
    }

	public OrthographicCamera getCamera() {
		return camera;
	}

	public void setCameraPos(float cx, float cy) {
		camera.position.x = cx;
		camera.position.y = cy;
	}
	
}//END