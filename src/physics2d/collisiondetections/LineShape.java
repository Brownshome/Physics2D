package physics2d.collisiondetections;

import physics2d.RigidBody;
import physics2d.maths.Vec2;

public class LineShape extends NarrowShape {
	private Vec2 _direction;
	private double _length;
	
	
	public LineShape(Vec2 position, RigidBody rigidBody, double length, Vec2 direction) {
		super(position, 5, rigidBody);
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
		Vec2 lineVector = new Vec2(_direction);
		lineVector.scale(_length);
		return lineVector;
	}
	
}
