package physics2d.collisiondetections;

import physics2d.RigidBody;
import physics2d.maths.Rotation;
import physics2d.maths.Vec2;

public class RectangleShape extends NarrowShape{
	private double _width;
	private double _height;
	private Rotation _rotation;
	
	public RectangleShape(Vec2 position, RigidBody A, double width, double height, Rotation rotation){
		super(position, 2, A);
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
}
