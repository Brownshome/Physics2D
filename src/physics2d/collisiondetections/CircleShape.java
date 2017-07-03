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
		return BroadShape.getInfiniteBroadShape();
	}
}
