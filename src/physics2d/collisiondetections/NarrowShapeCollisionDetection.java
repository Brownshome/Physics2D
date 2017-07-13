package physics2d.collisiondetections;

import java.util.*;

import physics2d.body.RigidBody;
import physics2d.contactsolver.ContactPoint;
import physics2d.maths.*;

public class NarrowShapeCollisionDetection {
	private static NarrowShapeCollisionDetection _isInitialised;
	private Map<CollisionType, NSNSCollisionFunction> _HashMap; 


	//id 1 = circle, id 2 = rectangle, id 3 = triangle, id 4 = plane, id 5 = line
	private  NarrowShapeCollisionDetection(){
		_HashMap = new HashMap<>();
		_HashMap.put(new CollisionType(1,1), this::circleCircleCollision);
		_HashMap.put(new CollisionType(4, 1), this::planeCircleCollision);
		_HashMap.put(new CollisionType(1, 4), (a, b) -> planeCircleCollision(b, a));
		_HashMap.put(new CollisionType(4, 4), (a, b) -> Collections.emptyList());
		_HashMap.put(new CollisionType(2, 1), this::rectangleCircleCollision);
		_HashMap.put(new CollisionType(1, 2), (a, b) -> rectangleCircleCollision(b, a));
		_HashMap.put(new CollisionType(4, 2), this::planeRectangleCollision);
		_HashMap.put(new CollisionType(2, 4), (a, b) -> planeRectangleCollision(b, a));
		_HashMap.put(new CollisionType(2, 2), this::rectangleRectangleCollision);
		_HashMap.put(new CollisionType(4, 5), this::planeLineCollision);
		_HashMap.put(new CollisionType(5, 4), (a, b) -> planeLineCollision(b, a));
		_HashMap.put(new CollisionType(5, 2), this::lineRectangleCollision);
		_HashMap.put(new CollisionType(2, 5), (a, b) -> lineRectangleCollision(b, a));
		_HashMap.put(new CollisionType(5, 1), this::lineCircleCollision);
		_HashMap.put(new CollisionType(1, 5), (a, b) -> lineCircleCollision(b, a));
		_HashMap.put(new CollisionType(5, 5), this::lineLineCollision);
		_HashMap.put(new CollisionType(3, 1), this::triangleCircleCollision);
		_HashMap.put(new CollisionType(3, 2), this::triangleRectangleCollision);
		_HashMap.put(new CollisionType(3, 3), this::triangleTriangleCollision);
		_HashMap.put(new CollisionType(3, 4), (a, b) -> planeTriangleCollision(b, a));
		_HashMap.put(new CollisionType(3, 5), this::triangleLineCollision);
		_HashMap.put(new CollisionType(1, 3), (a, b) -> triangleCircleCollision(b, a));
		_HashMap.put(new CollisionType(2, 3), (a, b) -> triangleRectangleCollision(b, a));
		_HashMap.put(new CollisionType(4, 3), this::planeTriangleCollision);
		_HashMap.put(new CollisionType(5, 3), (a, b) -> triangleLineCollision(b, a));
		
		
	}

	public static NarrowShapeCollisionDetection getInstance(){
		if(_isInitialised == null){
			_isInitialised = new NarrowShapeCollisionDetection();
		} return _isInitialised;
	}



	public Collection<ContactPoint> isColliding(RigidBody A, RigidBody B){
		return _HashMap.get(new CollisionType(A, B)).generateContactPoints(A, B);
	}
	
	private Collection<ContactPoint> triangleCircleCollision(RigidBody A, RigidBody B){
		assert false;
		return null;
	}
	
	private Collection<ContactPoint> triangleRectangleCollision(RigidBody A, RigidBody B){
		assert false;
		return null;
	}
	
	private Collection<ContactPoint> triangleTriangleCollision(RigidBody A, RigidBody B){
		assert false;
		return null;
	}
	
	private Collection<ContactPoint> planeTriangleCollision(RigidBody A, RigidBody B){
		assert false;
		return null;
	}
	
	private Collection<ContactPoint> triangleLineCollision(RigidBody A, RigidBody B){
		assert false;
		return null;
	}

	private Collection<ContactPoint> circleCircleCollision(RigidBody A, RigidBody B){
		CircleShape circleShapeA = (CircleShape) A.getNarrowShape();
		Vec2 APosition = circleShapeA.getPosition();
		CircleShape circleShapeB = (CircleShape) B.getNarrowShape();
		Vec2 BPosition = circleShapeB.getPosition();
		Collection<ContactPoint> output = new ArrayList<ContactPoint>();

		if(APosition.distanceSq(BPosition) < (circleShapeB.getRadius() + circleShapeA.getRadius()) * (circleShapeB.getRadius() + circleShapeA.getRadius())){
			MutableVec2 normal = new MutableVec2(circleShapeB.getPosition());
			normal.subtract(circleShapeA.getPosition());
			normal.normalize();
			double penetration = circleShapeA.getRadius() + circleShapeB.getRadius() - circleShapeA.getPosition().distance(circleShapeB.getPosition());
			MutableVec2 position = new MutableVec2(circleShapeA.getPosition());
			position.add(circleShapeB.getPosition());
			position.scale(0.5);
			output.add(new ContactPoint(penetration, position, normal, A, B));
		} 

		return output;
	}

	private Collection<ContactPoint> planeCircleCollision(RigidBody A, RigidBody B){
		PlaneShape plane = (PlaneShape) A.getNarrowShape();
		CircleShape circle = (CircleShape) B.getNarrowShape();

		double distance =  plane.getNormal().dot(circle.getPosition()) - plane.getNormal().dot(plane.getPosition());

		if(distance < circle.getRadius()) {
			double penetration = circle.getRadius() - distance;

			MutableVec2 circleToPlane = new MutableVec2(plane.getNormal());
			circleToPlane.scale(-distance - penetration / 2);
			circleToPlane.add(circle.getPosition());

			return Arrays.asList(new ContactPoint(penetration, circleToPlane, plane.getNormal(), A, B));
		}

		return Collections.emptyList();
	}

	private Collection<ContactPoint> rectangleCircleCollision(RigidBody A, RigidBody B){
		RectangleShape rectangle = (RectangleShape) A.getNarrowShape();
		CircleShape circle = (CircleShape) B.getNarrowShape();

		assert false;

		return null;
	}

	private Collection<ContactPoint> planeRectangleCollision(RigidBody A, RigidBody B){
		assert false;

		return null;
	}

	private Collection<ContactPoint> rectangleRectangleCollision(RigidBody A, RigidBody B){
		assert false;

		return null;
	}

	private Collection<ContactPoint> planeLineCollision(RigidBody A, RigidBody B){
		PlaneShape plane = (PlaneShape) A.getNarrowShape();
		LineShape line = (LineShape) B.getNarrowShape();
		Collection<ContactPoint> output = new ArrayList<>();
		MutableVec2 direction = new MutableVec2(plane.getNormal());
		direction.tangent();
		MutableVec2 distance = new MutableVec2(line.getPosition());
		distance.subtract(plane.getPosition());
		
		if(line.getDirection().x() != direction.x() && line.getDirection().y() != direction.y()){
			/*checks where the theoretical intersection point would be*/
			double secondScale = ((distance.x() * direction.y()) - (distance.y() * direction.x())) / 
					((direction.y() * line.getDirection().x()) - (line.getDirection().y() * direction.x())); 	

			double firstScale = ((distance.y() * line.getDirection().x()) - (distance.x() * line.getDirection().y())) / 
					((direction.y() * line.getDirection().x()) - (line.getDirection().y() * direction.x())); 	
			
			/*checks if that point of intersection is beyond the size of the lines*/
			if(Math.abs(secondScale) < line.getLength()){
				double penetration = line.getLength() - Math.abs(secondScale);
				MutableVec2 position = new MutableVec2(line.getDirection());
				position.scale(Math.abs(secondScale));
				position.add(line.getPosition());
				output.add(new ContactPoint(penetration, position, plane.getNormal(), A, B));
			}
		} else {
			
		}
		return output;
	}

	private Collection<ContactPoint> lineRectangleCollision(RigidBody A, RigidBody B){
		assert false;

		return null;
	}

	private Collection<ContactPoint> lineCircleCollision(RigidBody A, RigidBody B){
		LineShape line = (LineShape) A.getNarrowShape();
		CircleShape circle = (CircleShape) B.getNarrowShape();
		Collection<ContactPoint> output = new ArrayList<>();

		MutableVec2 normal = new MutableVec2(line.getDirection());
		normal.tangent();

		double distance = normal.dot(circle.getPosition()) - normal.dot(line.getPosition());

		if(Math.abs(distance) < circle.getRadius()){
			double penetration = circle.getRadius() - distance;

			MutableVec2 circleToLine = new MutableVec2(normal);
			circleToLine.scale(-distance - penetration / 2);

			if(line.getLine().length() > circleToLine.length()){
				circleToLine.add(circle.getPosition());
				output.add(new ContactPoint(penetration, circleToLine, normal, A, B));
			}
		}

		return output;

	}

	private Collection<ContactPoint> lineLineCollision(RigidBody A, RigidBody B){
		LineShape firstLine = (LineShape) A.getNarrowShape();
		LineShape secondLine = (LineShape) B.getNarrowShape();
		Collection<ContactPoint> output = new ArrayList<>();

		MutableVec2 distance = new MutableVec2(firstLine.getPosition());
		distance.subtract(secondLine.getPosition());
		
		double x1 = firstLine.getDirection().x();
		double x2 = secondLine.getDirection().x();
		double y1 = firstLine.getDirection().y();
		double y2 = secondLine.getDirection().y();
		double dx = distance.x();
		double dy = distance.y();

		/*if the two lines have an intersect that is not parallel*/
		if(Math.abs(firstLine.getDirection().x()) != Math.abs(secondLine.getDirection().x()) && Math.abs(firstLine.getDirection().y()) != Math.abs(secondLine.getDirection().y())){
			/*checks where the theoretical intersection point would be*/
			double secondScale = (dy * x1 - dx * y1) / (x2 * y1 - x1 * y2);

			double firstScale = (dy * x2 - dx * y2) / (x1 * y2 - x2 * y1);

			/*checks if that point of intersection is beyond the size of the lines*/
			if(Math.abs(firstScale) <= firstLine.getLength()){
				MutableVec2 normal = new MutableVec2(secondLine.getDirection());
				normal.tangent();
				normal.scale(-1);
				double penetration = firstLine.getLength()- Math.abs(firstScale);
				MutableVec2 position = new MutableVec2(firstLine.getDirection());
				position.scale(Math.abs(firstScale));
				position.add(firstLine.getPosition());
				output.add(new ContactPoint(penetration, position, normal, A, B));
			} 
			if(Math.abs(firstScale) > firstLine.getLength() && Math.abs(secondScale) <= secondLine.getLength()){
				MutableVec2 normal = new MutableVec2(firstLine.getDirection());
				normal.tangent();
				normal.scale(-1);
				double penetration = secondLine.getLength() - Math.abs(secondScale);
				MutableVec2 position = new MutableVec2(secondLine.getDirection());
				position.scale(Math.abs(secondScale));
				position.add(secondLine.getPosition());
				output.add(new ContactPoint(penetration, position, normal, A, B));
			}
		} 
		/*for parallel lines*/
		double xScale = firstLine.getPosition().x() / secondLine.getPosition().x();
		double yScale = firstLine.getPosition().y() / secondLine.getPosition().y();
		
		if(xScale - yScale <= 0.0001){
			
		}
		
		return output;
	}
}
