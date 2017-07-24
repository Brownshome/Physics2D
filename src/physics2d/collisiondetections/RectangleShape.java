package physics2d.collisiondetections;

import java.util.*;

import physics2d.body.RigidBody;
import physics2d.contactsolver.ContactPoint;
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
		return BroadShape.getInfiniteBroadShape();
		
		/*return new BoundBroadShape(new MultipleUpdateTracker(new Vec2UpdateTracker(getPosition()), new Vec2UpdateTracker(getRotation()))) {
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
				
				extent.scale(-0.5);
				
				MutableVec2 position = getPosition();
				position.set(RectangleShape.this.getPosition());
				position.subtract(extent);
			}
		};*/
	}
	
	static Collection<ContactPoint> rectangleRectangleCollision(RigidBody a, RigidBody b) {
		assert a.getNarrowShape() instanceof RectangleShape && b.getNarrowShape() instanceof RectangleShape;
		
		//Find the points that are inside each other
		//Find the closest edge to all of these point
		//Generate contact points

		List<ContactPoint> result = new ArrayList<>(8);

		RectangleShape rectangleA = (RectangleShape) a.getNarrowShape();
		RectangleShape rectangleB = (RectangleShape) b.getNarrowShape();

		//Corners of A
		aCheck: {
			Collection<? extends Vec2> aCorners = rectangleA.getCorners();
			aCorners.removeIf((v) -> !rectangleB.isInShape(new MutableVec2(v)));

			if(aCorners.size() == 0)
				break aCheck;
			
			MutableVec2 average = new MutableVec2();
			for(Vec2 v : aCorners) {
				average.add(v);
			}
			average.scale(1.0 / aCorners.size());

			Edge edge = rectangleB.getClosestEdgeTo(average);

			for(Vec2 v : aCorners) {
				double penetration = edge.signedDistance(v);
				MutableVec2 norm = new MutableVec2(edge.normal);

				if(penetration < 0) {
					//The normal is pointing away from the point
					norm.negate();
					penetration = -penetration;
				}

				//Normal goes from the corner to the edge
				result.add(new ContactPoint(penetration, v, norm, a, b));
			}
		}

		//Corners of B
		bCheck: {
			Collection<? extends Vec2> bCorners = rectangleB.getCorners();
			bCorners.removeIf((v) -> !rectangleA.isInShape(new MutableVec2(v)));

			if(bCorners.size() == 0)
				break bCheck;
			
			MutableVec2 average = new MutableVec2();
			for(Vec2 v : bCorners) {
				average.add(v);
			}
			average.scale(1.0 / bCorners.size());

			Edge edge = rectangleA.getClosestEdgeTo(average);

			for(Vec2 v : bCorners) {
				double penetration = edge.signedDistance(v);
				MutableVec2 norm = new MutableVec2(edge.normal);

				if(penetration < 0) {
					penetration = -penetration;
				} else {
					//The normal is the wrong direction
					norm.negate();
				}
				
				//Normal goes from the edge to the corner
				result.add(new ContactPoint(penetration, v, norm, a, b));
			}
		}
		
		return result;
	}
	
	/** Converts a point in world space to shape space. In shape space the square has a width of one and is centered at the
	 * origin. */
	private void transformToShapeSpace(MutableVec2 vec) {
		MutableRotation rotation = new MutableRotation(getRotation());
		
		vec.subtract(getPosition());
		rotation.invert();
		rotation.rotate(vec);
		
		vec.x(vec.x() / getWidth());
		vec.y(vec.y() / getHeight());
	}
	
	private Edge getClosestEdgeTo(MutableVec2 vec) {
		transformToShapeSpace(vec);
		
		MutableVec2 position, normal = vec;
		Rotation rotation = getRotation();
		if(vec.x() > vec.y()) {
			if(vec.x() > -vec.y()) {
				//right
				position = new MutableVec2(getWidth() * 0.5, 0);
				normal.set(rotation);
			} else {
				//top
				position = new MutableVec2(0, -getHeight() * 0.5);
				normal.set(rotation.y(), -rotation.x());
			}
		} else {
			if(vec.x() > -vec.y()) {
				//bottom
				position = new MutableVec2(0, getHeight() * 0.5);
				normal.set(-rotation.y(), rotation.x());
			} else {
				//left
				position = new MutableVec2(-getWidth() * 0.5, 0);
				normal.set(-rotation.x(), -rotation.y());
			}
		}
		
		//Transform scaled body space coordinates to world space coordinates
		rotation.rotate(position);
		position.add(getPosition());
		
		return new Edge(normal, position);
	}

	private static class Edge {
		public final Vec2 normal, position;
		
		public Edge(Vec2 normal, Vec2 position) {
			assert Math.abs(normal.lengthSq() - 1) < 0.00001;
			
			this.normal = normal;
			this.position = position;
		}
		
		public double signedDistance(Vec2 vec) {
			MutableVec2 tmp = new MutableVec2(vec);
			tmp.subtract(position);
			return tmp.dot(normal);
		}
	}
	
	private boolean isInShape(Vec2 point) {
		//Subtract the position, then rotate to undo the rotation
		
		MutableRotation rotation = new MutableRotation(getRotation());
		MutableVec2 tmp = new MutableVec2(point);
		
		transformToShapeSpace(tmp);
		
		return tmp.x() > -0.5 && tmp.x() < 0.5 && tmp.y() > -0.5 && tmp.y() < 0.5;
	}
	
	/** Points form a valid polygon */
	public ArrayList<MutableVec2> getCorners() {
		Rotation rotation = getRotation();
		MutableVec2 xBasis = new MutableVec2(getWidth() * 0.5, 0);
		MutableVec2 yBasis = new MutableVec2(0, getHeight() * 0.5);
		
		rotation.rotate(xBasis);
		rotation.rotate(yBasis);
		
		Vec2 position = getPosition();
		MutableVec2 topLeft = new MutableVec2(position);
		MutableVec2 topRight = new MutableVec2(position);
		MutableVec2 bottomLeft = new MutableVec2(position);
		MutableVec2 bottomRight = new MutableVec2(position);
		
		topLeft.subtract(xBasis);
		topLeft.subtract(yBasis);
		
		topRight.add(xBasis);
		topRight.subtract(yBasis);

		bottomLeft.subtract(xBasis);
		bottomLeft.add(yBasis);
		
		bottomRight.add(xBasis);
		bottomRight.add(yBasis);
		
		return new ArrayList<>(Arrays.asList(topLeft, topRight, bottomRight, bottomLeft));
	}
	
	static Collection<ContactPoint> rectangleCircleCollision(RigidBody a, RigidBody b){
		return Collections.emptyList();
	}

	static Collection<ContactPoint> planeRectangleCollision(RigidBody a, RigidBody b){
		//Check if any points are below the plain and generate the collision
		PlaneShape plane = (PlaneShape) a.getNarrowShape();
		RectangleShape rectangle = (RectangleShape) b.getNarrowShape();
		Collection<ContactPoint> contacts = new ArrayList<>();
		
		for(MutableVec2 v : rectangle.getCorners()) {
			
			MutableVec2 tmp = new MutableVec2(v);
			tmp.subtract(plane.getPosition());
			double distance = tmp.dot(plane.getNormal());
			
			if(distance <= 0) {
				v.scaleAdd(plane.getNormal(), distance * 0.5);
				contacts.add(new ContactPoint(-distance, v, plane.getNormal(), a, b));
			}
		}
		
		return contacts;
	}
 }
