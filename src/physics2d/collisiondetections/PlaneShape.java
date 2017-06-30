package physics2d.collisiondetections;

import physics2d.RigidBody;
import physics2d.maths.Vec2;

public class PlaneShape extends NarrowShape{
	private Vec2 _direction;

	public PlaneShape(Vec2 position, RigidBody rigidBody, Vec2 direction) {
		super(position, 4, rigidBody);
		_direction = direction;
	}
	
	public Vec2 getDirection(){
		return _direction;
	}
	
}
