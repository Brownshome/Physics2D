package physics2d.collisiondetections;

import java.util.Collection;

import physics2d.RigidBody;
import physics2d.contactsolver.ContactPoint;
import physics2d.maths.Vec2;

public class CircleShape extends NarrowShape{
	private double _radius;
	
	public CircleShape(Vec2 position, double radius, RigidBody rigidBody){
		super(position, 1, rigidBody);
		_radius = radius;
	}
	
	public double getRadius(){
		return _radius;
	}

}
