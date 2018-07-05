package gr.platformertest.managers;

import static gr.platformertest.consts.MyConsts.PPM;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class MyTileManager {
	private static Body body;
	
	public static Body parseTiledObjectLayer(World world, MapObjects objects) {
		Shape shape = null;
		
		for(MapObject object: objects) {			
			if(object instanceof PolylineMapObject) {
				shape = createPolyline((PolylineMapObject) object);
			}
			else
				continue;
		}
		
		body = MyBox2DManager.createFromPolyline(true, world, shape);
		
//		BodyDef bdef = new BodyDef();
//		bdef.type = BodyDef.BodyType.StaticBody;
//		body = world.createBody(bdef);
//		
//		FixtureDef fdef = new FixtureDef();
//		fdef.shape = shape;
//		fdef.density = 1f;
//		body.createFixture(fdef);
//		
//		shape.dispose();
		
		return body;
	}
	
	private static ChainShape createPolyline(PolylineMapObject polyline) {
		float[] vertices = polyline.getPolyline().getTransformedVertices();
		Vector2[] worldVertices = new Vector2[vertices.length /2];
		
		for(int i=0; i<worldVertices.length; i++) {
			worldVertices[i] = new Vector2(vertices[i*2] /PPM, vertices[i*2 + 1] /PPM);
		}
		
		ChainShape cs = new ChainShape();
		cs.createChain(worldVertices);
		
		return cs;
	}	

}// END
