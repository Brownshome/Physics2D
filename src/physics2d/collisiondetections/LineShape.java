package physics2d.collisiondetections;

import java.util.Arrays;
import java.util.function.ToDoubleFunction;

import physics2d.body.RigidBody;
import physics2d.maths.*;
import physics2d.update.*;


public class LineShape extends NarrowShape {
	private MutableVec2 _direction;
	private double _length;
	
	public LineShape(MutableVec2 position, double length, MutableVec2 direction) {
		super(position, 5);
		_length = length;
		_direction = direction;
	}
	
	public LineShape(Vec2 VertexA, Vec2 VertexB){
		super(VertexA, 5);
		MutableVec2 edge = new MutableVec2(VertexA);
		edge.subtract(VertexB);
		_length = edge.length();
		_direction = new MutableVec2(edge);
		_direction.scale(1 / _length);
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
	public BroadShape createBoundBroadShape() {
		return new BoundBroadShape(new Vec2UpdateTracker(getPosition())) {
			@Override protected void update() {
				MutableVec2 scaledDirection = new MutableVec2(_direction);
				scaledDirection.scale(_length);
				
				getPosition().set(LineShape.this.getPosition());
				getPosition().add(Math.min(0, getDirection().x() * getLength()), Math.min(0, getDirection().y() * getLength()));
				
				_xExtension = Math.min(0, getDirection().x() * getLength());
				_yExtension = Math.min(0, getDirection().y() * getLength());
			}
		};
	}
}
