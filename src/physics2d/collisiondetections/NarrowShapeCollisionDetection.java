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
	}

	public static NarrowShapeCollisionDetection getInstance(){
		if(_isInitialised == null){
			_isInitialised = new NarrowShapeCollisionDetection();
		} return _isInitialised;
	}
	
	
	
	public Collection<ContactPoint> isColliding(RigidBody A, RigidBody B){
		return _HashMap.get(new CollisionType(A, B)).generateContactPoints(A, B);
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
		
		assert false;
		
		return null;
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
		
		if(distance < circle.getRadius()){
			double penetration = circle.getRadius() - distance;
			
			MutableVec2 circleToLine = new MutableVec2(normal);
			circleToLine.scale(-distance - penetration / 2);
			circleToLine.add(circle.getPosition());
			
			if(line.getLine().length() > circleToLine.length()){
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
				
		double secondScale = ((distance.x() * firstLine.getDirection().y()) - (distance.y() * firstLine.getDirection().x())) / 
				((firstLine.getDirection().x() * secondLine.getDirection().y()) - (firstLine.getDirection().y() * secondLine.getDirection().x())); 	
				
		double firstScale = (distance.x() + secondScale * secondLine.getDirection().x()) / firstLine.getDirection().x();
		
		if(firstScale < firstLine.getLength()){
			MutableVec2 normal = new MutableVec2(secondLine.getDirection());
			normal.tangent();
			double penetration = firstLine.getLength() * firstLine.getDirection().x() - firstScale * firstLine.getDirection().x();
			MutableVec2 position = new MutableVec2(firstLine.getDirection());
			position.scale(firstScale);
			position.add(firstLine.getPosition());
			output.add(new ContactPoint(penetration, position, normal, A, B));
		}
		
		if(secondScale < secondLine.getLength()){
			MutableVec2 normal = new MutableVec2(firstLine.getDirection());
			normal.tangent();
			double penetration = secondLine.getLength() * secondLine.getDirection().x() - secondScale * secondLine.getDirection().x();
			MutableVec2 position = new MutableVec2(secondLine.getDirection());
			position.scale(secondScale);
			position.add(secondLine.getPosition());
			output.add(new ContactPoint(penetration, position, normal, A, B));
		}
		
		return output;
	}
	
	
}
