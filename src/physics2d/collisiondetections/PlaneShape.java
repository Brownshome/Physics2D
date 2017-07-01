package physics2d.collisiondetections;

import physics2d.body.RigidBody;
import physics2d.maths.Vec2;

public class PlaneShape extends NarrowShape{
	private Vec2 _normal;

	/**
	 * @param normal points out from the side objects will bounce off 
	 **/
	public PlaneShape(Vec2 position, Vec2 normal) {
		super(position, 4);
		_normal = normal;
	}
	
	public Vec2 getNormal(){
		return _normal;
	}

	@Override
	public BroadShape createBoundNarrowShape() {
		return BroadShape.getInfiniteBroadShape();
	}
	
}
