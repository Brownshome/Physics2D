package physics2d.body;

import physics2d.collisiondetections.BroadShape;
import physics2d.collisiondetections.LineShape;
import physics2d.collisiondetections.NarrowShape;
import physics2d.maths.MutableRotation;
import physics2d.maths.MutableVec2;
import physics2d.maths.Rotation;

public class LineBody extends DynamicBody{
	
	public LineBody(MutableVec2 position, MutableVec2 velocity, MutableVec2 direction, double length) {
		super(position, new MutableRotation(), velocity, 0, 1, 100, 1.0);
		MutableVec2 realPos = new MutableVec2(position);
		realPos.add(-Math.abs(direction.x()) * length, -Math.abs(direction.y()) * length);
		
	}
	
}
