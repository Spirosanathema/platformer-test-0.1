package gr.platformertest.managers;

import static gr.platformertest.consts.MyConsts.PPM;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class MyBox2DManager {
	private World world;
	private Box2DDebugRenderer renderer;
	
	private static Body body;
	private static Fixture fixture;
	
	
	// Polygon Body Creation
	public static Body createPolygonBody(boolean isStatic, float positionX, float positionY, float dimensionX, float dimensionY,
			World world, Box2DDebugRenderer renderer) {
		BodyDef bdef = new BodyDef();
		if(isStatic)
			bdef.type = BodyDef.BodyType.StaticBody;
		else
			bdef.type = BodyDef.BodyType.DynamicBody;
		bdef.position.set(positionX + dimensionX /2, positionY + dimensionY /2);
		bdef.angle = 0f;
		body = world.createBody(bdef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(dimensionX /2, dimensionY /2);
		
		FixtureDef fdef = new FixtureDef();
		fdef.density = .5f;
		fdef.shape = shape;
		fixture = body.createFixture(fdef);
				
		shape.dispose();
		return body;
	}
	
	// Circle Body Creation 0000000000000000
	public static Body createCircleBody(boolean isStatic, float positionX, float positionY, float radius,
			World world, Box2DDebugRenderer renderer) {
		BodyDef bdef = new BodyDef();
		if(isStatic)
			bdef.type = BodyDef.BodyType.StaticBody;
		else
			bdef.type = BodyDef.BodyType.DynamicBody;
		bdef.position.set(positionX + radius/2, positionY + radius/2);
		body = world.createBody(bdef);
		
		CircleShape shape = new CircleShape();
		shape.setRadius(radius /2);
		
		FixtureDef fdef = new FixtureDef();
		fdef.density = .8f;
		fdef.shape = shape;
		fixture = body.createFixture(fdef);	
		
		shape.dispose();
		return body;
	}
	
	// Polygon Shape with vector points
	public static Body createTruePolygonBody(boolean isStatic, float positionX, float positionY, int numberOfPoints, 
			Vector2[] vectorOfPoints, World world, Box2DDebugRenderer renderer) {
		BodyDef bdef = new BodyDef();
		if(isStatic)
			bdef.type = BodyDef.BodyType.StaticBody;
		else
			bdef.type = BodyDef.BodyType.DynamicBody;
		bdef.position.set(positionX /2/PPM, positionY /2/PPM);
		bdef.angle = 0f;
		body = world.createBody(bdef);
		
		PolygonShape shape = new PolygonShape();
		shape.set(vectorOfPoints);
		
		FixtureDef fdef = new FixtureDef();
		fdef.density = .5f;
		fdef.shape = shape;
		fixture = body.createFixture(fdef);	
		
		shape.dispose();		
		return body;
	}
	
	// Create from PolylineMapObject
	public static Body createFromPolyline(boolean isStatic, World world, Shape shape) {
		BodyDef bdef = new BodyDef();
		if(isStatic)
			bdef.type = BodyDef.BodyType.StaticBody;
		else
			bdef.type = BodyDef.BodyType.DynamicBody;
		body = world.createBody(bdef);
		
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.density = 1f;
		fixture = body.createFixture(fdef);
		
		shape.dispose();
		return body;
	}

}// END
