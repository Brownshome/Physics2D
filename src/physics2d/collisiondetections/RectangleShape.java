package physics2d.collisiondetections;

import java.util.ArrayList;

import physics2d.maths.*;
import physics2d.update.*;

public class RectangleShape extends NarrowShape{
	private final double _width;
	private final double _height;
	private final Rotation _rotation;
	
	/**
	 * @param position The centre of the rectangle
	 * @param width The width of the rectangle
	 * @param height The height of the rectangle
	 * @param rotation The direction of the rectangle
	 **/
	public RectangleShape(Vec2 position, double width, double height, Rotation rotation){
		super(position, 2);
		_width = width;
		_height = height;
		_rotation = rotation;
	}
	
	public double getWidth(){
		return _width;
	}
	
	public double getHeight(){
		return _height;
	}
	
	public Rotation getRotation(){
		return _rotation;
	}

	@Override
	public BroadShape createBoundBroadShape() {
		return new BoundBroadShape(new MultipleUpdateTracker(new Vec2UpdateTracker(getPosition()), new Vec2UpdateTracker(getRotation()))) {
			@Override protected void update() {
				double w = getWidth();
				double h = getHeight();
				Rotation r = getRotation();
				
				MutableVec2 extent = new MutableVec2();
				//|w cos a| + |h sin a|, |h cos a| + |w sin a|
				extent.x(Math.abs(w * r.x()) + Math.abs(h * r.y()));
				extent.y(Math.abs(h * r.x()) + Math.abs(w * r.y()));
				
				_xExtension = extent.x();
				_yExtension = extent.y();
			}
		};
	}
}
