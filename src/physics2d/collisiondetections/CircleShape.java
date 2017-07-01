package physics2d.collisiondetections;

import java.util.Collection;

import physics2d.body.RigidBody;
import physics2d.contactsolver.ContactPoint;
import physics2d.maths.*;

public class CircleShape extends NarrowShape{
	private double _radius;
	
	public CircleShape(Vec2 position, double radius){
		super(position, 1);
		_radius = radius;
	}
	
	public double getRadius(){
		return _radius;
	}

	@Override
	public BroadShape createBoundNarrowShape() {
		Vec2 topCorner = new BoundVec2(getPosition(), v -> v.x() - getRadius(), v -> v.y() - getRadius());
		return new BroadShape(topCorner, _radius * 2, _radius * 2);
	}
}
