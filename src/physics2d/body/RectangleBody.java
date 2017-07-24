package physics2d.body;

import physics2d.collisiondetections.RectangleShape;
import physics2d.maths.*;

public class RectangleBody extends DynamicBody {
	public RectangleBody(MutableVec2 position, MutableRotation direction, MutableVec2 velocity, double xExtent, double yExtent) {
		super(position, direction, velocity, 0.0, 1, 10000.0, 0.1);
		setCollisionShapes(new RectangleShape(position, xExtent, yExtent, direction));
	}
}
