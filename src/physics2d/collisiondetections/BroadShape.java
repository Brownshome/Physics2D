package physics2d.collisiondetections;

import java.util.*;

import physics2d.maths.*;

public class BroadShape {
	private static final BroadShape INFINITE_BROAD_SHAPE = new BroadShape(null, 0, 0) {
		@Override
		public boolean doesCollide(BroadShape other) {
			return true;
		}
	};
	
	//Made protected so they can be updated by bound subclasses
	private Vec2 _position;
	protected double _xExtension;
	protected double _yExtension;
	
	//constructed using the bottom left corner of the box, with the width and height of the box.
	public BroadShape(Vec2 position, double width, double height){
		_position = position;
		_xExtension = width;
		_yExtension = height;
	}
	
	public static BroadShape getInfiniteBroadShape() {
		return INFINITE_BROAD_SHAPE;
	}
	
	public boolean doesCollide(BroadShape other) {
		if(other == INFINITE_BROAD_SHAPE) {
			return true;
		}
		
		List<Vec2> boundariesA = getCorners();
		List<Vec2> boundariesB = other.getCorners();
		
		if((boundariesA.get(0).x() <= boundariesB.get(0).x()) && (boundariesB.get(0).x() <= boundariesA.get(3).x())){
			if((boundariesA.get(0).y() <= boundariesB.get(0).y()) && (boundariesB.get(0).y() <= boundariesA.get(1).y())){
				return true;
			} if((boundariesB.get(0).y() <= boundariesA.get(0).y()) && (boundariesA.get(0).y() <= boundariesB.get(1).y())){
				return true;
			}
		} if((boundariesB.get(0).x() <= boundariesA.get(0).x()) && (boundariesA.get(0).x() <= boundariesB.get(3).x())){
			if((boundariesA.get(0).y() <= boundariesB.get(0).y()) && (boundariesB.get(0).y() <= boundariesA.get(1).y())){
				return true;
			} if((boundariesB.get(0).y() <= boundariesA.get(0).y()) && (boundariesA.get(0).y() <= boundariesB.get(1).y())){
				return true;
			}
		}
		
		return false;
	}
	
	protected Vec2 getPosition() {
		return _position;
	}
	
	//returns the corner positions of the Shape's general Bounding Box.
	protected ArrayList<Vec2> getCorners(){
		ArrayList<Vec2> corners = new ArrayList<Vec2>();
		corners.add(getPosition());
		MutableVec2 topLeftCorner = new MutableVec2(0, _yExtension);
		MutableVec2 topRightCorner = new MutableVec2(_xExtension, _yExtension);
		MutableVec2 bottomRightCorner = new MutableVec2(_xExtension, 0);
		topLeftCorner.add(getPosition());
		topRightCorner.add(getPosition());
		bottomRightCorner.add(getPosition());
		corners.add(topLeftCorner);
		corners.add(topRightCorner);
		corners.add(bottomRightCorner);
		return corners;
	}
}
