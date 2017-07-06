package physics2d.body;

import physics2d.collisiondetections.*;
import physics2d.contactsolver.ContactPoint;
import physics2d.maths.*;

public class CircleBody extends DynamicBody {
	public CircleBody(MutableVec2 position, MutableVec2 velocity, double radius) {
		super(position, new MutableRotation(), velocity, 0, radius * radius, 100, 1.0);
		setCollisionShapes(new CircleShape(position, radius));
	}

	public double radius() {
		return getNarrowShape().getRadius();
	}
	
	@Override
	public CircleShape getNarrowShape() {
		return (CircleShape) super.getNarrowShape();
	}
}
