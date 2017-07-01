package physics2d.body;

import physics2d.collisiondetections.*;
import physics2d.contactsolver.ContactPoint;
import physics2d.maths.*;

public class CircleBody extends DynamicBody {
	public CircleBody(MutableVec2 position, MutableVec2 velocity, double radius) {
		super(position, new Rotation(), velocity, 0, new CircleShape(position, radius), radius * radius, 100, 1.0);
	}

	public double radius() {
		return ((CircleShape) getNarrowShape()).getRadius();
	}
}
