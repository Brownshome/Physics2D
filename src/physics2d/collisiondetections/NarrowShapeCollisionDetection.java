package physics2d.collisiondetections;

import java.util.*;

import physics2d.contactsolver.ContactPoint;
import physics2d.maths.Vec2;

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
	
	
	
	public Collection<ContactPoint> isColliding(NarrowShape A, NarrowShape B){
		return _HashMap.get(new CollisionType(A, B)).generateContactPoints(A, B);
	}
	
	private Collection<ContactPoint> circleCircleCollision(NarrowShape A, NarrowShape B){
		CircleShape circleShapeA = (CircleShape) A;
		Vec2 APosition = A.getPosition();
		CircleShape circleShapeB = (CircleShape) B;
		Vec2 BPosition = B.getPosition();
		Collection<ContactPoint> output = new ArrayList<ContactPoint>();
		
		if(APosition.distanceSq(BPosition) < (circleShapeB.getRadius() + circleShapeA.getRadius()) * (circleShapeB.getRadius() + circleShapeA.getRadius())){
			Vec2 normal = new Vec2(circleShapeB.getPosition());
			normal.subtract(circleShapeA.getPosition());
			normal.normalize();
			double penetration = circleShapeA.getRadius() + circleShapeB.getRadius() - circleShapeA.getPosition().distance(circleShapeB.getPosition());
			Vec2 position = new Vec2(circleShapeA.getPosition());
			position.add(circleShapeB.getPosition());
			position.scale(0.5);
			output.add(new ContactPoint(penetration, position, normal, A.getRigidBody(), B.getRigidBody()));
		} 
		
		return output;
	}
	
	private Collection<ContactPoint> planeCircleCollision(NarrowShape A, NarrowShape B){
		PlaneShape plane = (PlaneShape) A;
		CircleShape circle = (CircleShape) B;
		
		double distance =  plane.getNormal().dot(circle.getPosition()) - plane.getNormal().dot(plane.getPosition());
		
		if(distance < circle.getRadius()) {
			double penetration = circle.getRadius() - distance;
			
			Vec2 circleToPlane = new Vec2(plane.getNormal());
			circleToPlane.scale(-distance - penetration / 2);
			circleToPlane.add(circle.getPosition());
			
			return Arrays.asList(new ContactPoint(penetration, circleToPlane, plane.getNormal(), A.getRigidBody(), B.getRigidBody()));
		}
		
		return Collections.emptyList();
	}
	
	private Collection<ContactPoint> rectangleCircleCollision(NarrowShape A, NarrowShape B){
		RectangleShape rectangle = (RectangleShape) A;
		CircleShape circle = (CircleShape) B;
		
	}
	
	private Collection<ContactPoint> planeRectangleCollision(NarrowShape A, NarrowShape B){
		return null;
	}
	
	private Collection<ContactPoint> rectangleRectangleCollision(NarrowShape A, NarrowShape B){
		return null;
	}
	
	private Collection<ContactPoint> planeLineCollision(NarrowShape A, NarrowShape B){
		PlaneShape plane = (PlaneShape) A;
		LineShape line = (LineShape) B;
		Collection<ContactPoint> output = new ArrayList<ContactPoint>();
		
		
	}
	
	private Collection<ContactPoint> lineRectangleCollision(NarrowShape A, NarrowShape B){
		
	}
	
	private Collection<ContactPoint> lineCircleCollision(NarrowShape A, NarrowShape B){
		LineShape line = (LineShape) A;
		CircleShape circle = (CircleShape) B;
		Collection<ContactPoint> output = new ArrayList<>();
		
		Vec2 normal = new Vec2(line.getDirection());
		normal.tangent();
		
		double distance = normal.dot(circle.getPosition()) - normal.dot(line.getPosition());
		
		if(distance < circle.getRadius()){
			double penetration = circle.getRadius() - distance;
			
			Vec2 circleToLine = new Vec2(normal);
			circleToLine.scale(-distance - penetration / 2);
			circleToLine.add(circle.getPosition());
			
			if(line.getLine().length() > circleToLine.length()){
				output.add(new ContactPoint(penetration, circleToLine, normal, A.getRigidBody(), B.getRigidBody()));
			}
		}
		
		return output;
		
	}
	
	private Collection<ContactPoint> lineLineCollision(NarrowShape A, NarrowShape B){
		LineShape firstLine = (LineShape) A;
		LineShape secondLine = (LineShape) B;
		Collection<ContactPoint> output = new ArrayList<ContactPoint>();
		
		Vec2 distance = new Vec2(firstLine.getPosition());
		distance.subtract(secondLine.getPosition());
				
		double secondScale = ((distance.x() * firstLine.getDirection().y()) - (distance.y() * firstLine.getDirection().x())) / 
				((firstLine.getDirection().x() * secondLine.getDirection().y()) - (firstLine.getDirection().y() * secondLine.getDirection().x())); 	
				
		double firstScale = (distance.x() + secondScale * secondLine.getDirection().x()) / firstLine.getDirection().x();
		
		if(firstScale < firstLine.getLength()){
			Vec2 normal = new Vec2(secondLine.getDirection());
			normal.tangent();
			double penetration = firstLine.getLength() * firstLine.getDirection().x() - firstScale * firstLine.getDirection().x();
			Vec2 position = new Vec2(firstLine.getDirection());
			position.scale(firstScale);
			position.add(firstLine.getPosition());
			output.add(new ContactPoint(penetration, position, normal, firstLine.getRigidBody(), secondLine.getRigidBody()));
		}
		
		if(secondScale < secondLine.getLength()){
			Vec2 normal = new Vec2(firstLine.getDirection());
			normal.tangent();
			double penetration = secondLine.getLength() * secondLine.getDirection().x() - secondScale * secondLine.getDirection().x();
			Vec2 position = new Vec2(secondLine.getDirection());
			position.scale(secondScale);
			position.add(secondLine.getPosition());
			output.add(new ContactPoint(penetration, position, normal, secondLine.getRigidBody(), firstLine.getRigidBody()));
		}
		
		return output;
	}
	
	
}
