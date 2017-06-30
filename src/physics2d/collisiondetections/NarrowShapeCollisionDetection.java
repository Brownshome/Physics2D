package physics2d.collisiondetections;

import java.util.*;

import physics2d.contactsolver.ContactPoint;
import physics2d.maths.Vec2;

public class NarrowShapeCollisionDetection {
	private static NarrowShapeCollisionDetection _isInitialised;
	private Map<CollisionType, NSNSCollisionFunction> _HashMap; 
	
	private  NarrowShapeCollisionDetection(){
		_HashMap = new HashMap<>();
		_HashMap.put(new CollisionType(1,1), this::circleCircleCollision);
		_HashMap.put(new CollisionType(4, 1), this::lineCircleCollision);
		_HashMap.put(new CollisionType(1, 4), (a, b) -> lineCircleCollision(b, a));
		_HashMap.put(new CollisionType(4, 4), (a, b) -> Collections.emptyList());
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
	
	private Collection<ContactPoint> lineCircleCollision(NarrowShape A, NarrowShape B){
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
}
