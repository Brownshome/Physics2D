package physics2d.collisiondetections;

import java.util.function.ToDoubleFunction;

import physics2d.body.RigidBody;
import physics2d.maths.*;


public class LineShape extends NarrowShape {
	private Vec2 _direction;
	private double _length;
	
	public LineShape(Vec2 position, double length, Vec2 direction) {
		super(position, 5);
		_length = length;
		_direction = direction;
	}
	
	public double getLength(){
		return _length;
	}
	
	public Vec2 getDirection(){
		return _direction;
	}
	
	public Vec2 getLine(){
		MutableVec2 lineVector = new MutableVec2(_direction);
		lineVector.scale(_length);
		return lineVector;
	}

	@Override
	public BroadShape createBoundNarrowShape() {
		return BroadShape.getInfiniteBroadShape();
	}
}
