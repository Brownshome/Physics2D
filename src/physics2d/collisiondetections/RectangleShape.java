package physics2d.collisiondetections;

import physics2d.maths.*;

public class RectangleShape extends NarrowShape{
	private double _width;
	private double _height;
	private Rotation _rotation;
	
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
	public BroadShape createBoundNarrowShape() {
		return BroadShape.getInfiniteBroadShape();
	}
}
