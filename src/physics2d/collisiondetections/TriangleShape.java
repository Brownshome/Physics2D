package physics2d.collisiondetections;

import physics2d.RigidBody;
import physics2d.maths.Vec2;

public class TriangleShape extends NarrowShape {

	public TriangleShape(Vec2 position, RigidBody rigidBody) {
		super(position, 3, rigidBody);
	}
	
	
	
}
