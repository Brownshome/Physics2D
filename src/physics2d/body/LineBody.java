package physics2d.body;

import physics2d.collisiondetections.BroadShape;
import physics2d.collisiondetections.LineShape;
import physics2d.collisiondetections.NarrowShape;
import physics2d.maths.MutableRotation;
import physics2d.maths.MutableVec2;
import physics2d.maths.Rotation;
import physics2d.maths.Vec2;

public class LineBody extends DynamicBody{
	
	public LineBody(MutableVec2 position, MutableVec2 velocity, MutableVec2 direction, double length) {
		super(position, new MutableRotation(direction.x(), direction.y()), velocity, 0, 1, 1e10, 1.0);
		LineShape lineShape = new LineShape(position, length, direction){
			@Override
			public Vec2 getDirection() {
				return LineBody.this.direction();
			}
		};
		setCollisionShapes(lineShape);
	}
}
