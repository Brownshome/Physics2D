package physics2d.collisiondetections;

import java.util.Arrays;
import java.util.function.ToDoubleFunction;

import physics2d.body.RigidBody;
import physics2d.maths.*;
import physics2d.update.*;


public class LineShape extends NarrowShape {
	private final MutableVec2 _direction;
	private final double _length;
	
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
	
	/** Returns a vector representing the displacement from one end of the line to the other */
	public Vec2 getLine(){
		MutableVec2 lineVector = new MutableVec2(_direction);
		lineVector.scale(_length * 2);
		return lineVector;
	}

	@Override
	public BroadShape createBoundBroadShape() {
		return BroadShape.getInfiniteBroadShape();
		/*return new BoundBroadShape(new MultipleUpdateTracker(new Vec2UpdateTracker(getPosition()), new Vec2UpdateTracker(_direction))) {
			@Override protected void update() {
				MutableVec2 scaledDirection = new MutableVec2(_direction);
				scaledDirection.scale(_length);

				//Position is the position of the line minus half the direction value.
				getPosition().set(LineShape.this.getPosition());
				getPosition().add(-Math.abs(scaledDirection.x() * 0.5), -Math.abs(scaledDirection.y() * 0.5));

				//The extension is the magnitude of the direction value
				_xExtension = Math.abs(scaledDirection.x());
				_yExtension = Math.abs(scaledDirection.y());
			}*/
	}
}
