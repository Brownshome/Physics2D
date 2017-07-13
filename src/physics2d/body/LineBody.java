package physics2d.body;

import physics2d.collisiondetections.BroadShape;
import physics2d.collisiondetections.LineShape;
import physics2d.collisiondetections.NarrowShape;
import physics2d.maths.MutableVec2;
import physics2d.maths.Rotation;

public class LineBody extends DynamicBody{

	public LineBody(MutableVec2 position, Rotation direction, MutableVec2 velocity, double angularVelocity,
			BroadShape broadShape, NarrowShape narrowShape, double mass, double inertia, double restitution) {
		super(position, direction, velocity, angularVelocity, broadShape, narrowShape, mass, inertia, restitution);
	}
	
	public LineBody(MutableVec2 position, MutableVec2 velocity, MutableVec2 direction, double length) {
		super(position, new Rotation(), velocity, 0,new BroadShape(position, direction.y() * length, direction.x() * length), new LineShape(position, length, direction), 1, 100, 1.0);
	}
	
}
