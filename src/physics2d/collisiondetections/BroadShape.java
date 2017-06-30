package physics2d.collisiondetections;

import java.util.*;

import physics2d.maths.Vec2;

public class BroadShape {
	private static final BroadShape INFINITE_BROAD_SHAPE = new BroadShape(null, 0, 0) {
		@Override
		public boolean doesCollide(BroadShape other) {
			return true;
		}
	};
	
	private Vec2 _position;
	private double _xExtension;
	private double _yExtension;
	
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
		
		/*if(_position.x() > other._position.x() + other._xExtension) return false;
		if(other._position.x() > _position.x() + _xExtension) return false;
		if(_position.y() > other._position.y() + other._yExtension) return false;
		if(other._position.y() > _position.y() + _yExtension) return false;
		
		return true;*/
		
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
	
	//returns the corner positions of the Shape's general Bounding Box.
	private ArrayList<Vec2> getCorners(){
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
