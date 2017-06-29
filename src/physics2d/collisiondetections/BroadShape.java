package physics2d.collisiondetections;

import java.util.ArrayList;

import physics2d.maths.Vec2;

public class BroadShape {
	private Vec2 _position;
	private double _xExtension;
	private double _yExtension;
	
	//constructed using the bottom left corner of the box, with the width and height of the box.
	public BroadShape(Vec2 position, double width, double height){
		_position = position;
		_xExtension = width;
		_yExtension = height;
	}
	
	//returns the corner positions of the Shape's general Bounding Box.
	public ArrayList<Vec2> getCorners(){
		ArrayList<Vec2> corners = new ArrayList<Vec2>();
		corners.add(_position);
		Vec2 topLeftCorner = new Vec2(0, _yExtension);
		Vec2 topRightCorner = new Vec2(_xExtension, _yExtension);
		Vec2 bottomRightCorner = new Vec2(_xExtension, 0);
		topLeftCorner.add(_position);
		topRightCorner.add(_position);
		bottomRightCorner.add(_position);
		corners.add(topLeftCorner);
		corners.add(topRightCorner);
		corners.add(bottomRightCorner);
		return corners;
	}
}
