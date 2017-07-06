package physics2d.collisiondetections;

import java.util.*;

import physics2d.body.RigidBody;
import physics2d.contactsolver.ContactPoint;
import physics2d.maths.*;
import physics2d.update.*;

public class CircleShape extends NarrowShape {
	private double _radius;
	
	public CircleShape(Vec2 position, double radius){
		super(position, 1);
		_radius = radius;
	}
	
	public double getRadius(){
		return _radius;
	}

	@Override
	public BroadShape createBoundBroadShape() {
		return new BoundBroadShape(new Vec2UpdateTracker(getPosition())) {
			{
				_xExtension = getRadius() * 2;
				_yExtension = getRadius() * 2;
			}
			
			@Override protected void update() {
				getPosition().set(CircleShape.this.getPosition());
				getPosition().add(-_radius, -_radius);
			}
		};
	}
}
