package physics2d.collisiondetections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import physics2d.contactsolver.ContactPoint;
import physics2d.maths.Vec2;

public class NarrowShapeCollisionDetection {
	private static NarrowShapeCollisionDetection _isInitialised;
	private Map<CollisionType, NSNSCollisionFunction> _HashMap; 
	
	private  NarrowShapeCollisionDetection(){
		_HashMap = new HashMap<>();
		_HashMap.put(new CollisionType(1,1), this::circleCircleCollision);
		_HashMap.put(new CollisionType(4, 1), this::lineCircleCollision);
		_HashMap.put(new CollisionType(1, 4), this::circleLineCollision);
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
			Vec2 normal = new Vec2(circleShapeA.getPosition());
			normal.subtract(circleShapeB.getPosition());
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
		PlaneShape psA = (PlaneShape) A;
		CircleShape csB = (CircleShape) B;
		Collection<ContactPoint> output = new ArrayList<ContactPoint>();
		Vec2 constant = new Vec2(psA.getPosition());
		constant.subtract(csB.getPosition());
		double distance = psA.getDirection().dot(constant);
		if(distance < csB.getRadius()){
			Vec2 normal = new Vec2(psA.getDirection());
			normal.tangent(normal);
			double penetration = csB.getRadius() - distance;
			double normalMultiple = csB.getRadius() - penetration;
			Vec2 circleToPlane = new Vec2(normal);
			circleToPlane.scale(normalMultiple);
			Vec2 position = csB.getPosition();
			position.add(circleToPlane);
			output.add(new ContactPoint(penetration, position, normal, A.getRigidBody(), B.getRigidBody()));
		}
		
		return output;
	}
	
	private Collection<ContactPoint> circleLineCollision(NarrowShape A, NarrowShape B){
		return lineCircleCollision(B, A);
	}
}
