package physics2d.collisiondetections;

import java.util.*;

import physics2d.RigidBody;
import physics2d.contactsolver.ContactPoint;
import physics2d.maths.Vec2;

public class BasicCollision implements CollisionDetector{
	private List<RigidBody> _rigidBodies = new ArrayList<>(); 
	@Override
	public void addRigidBody(RigidBody rigidBody) {
		_rigidBodies.add(rigidBody);
	}

	@Override
	public void removeRigidBody (RigidBody rigidBody){
			_rigidBodies.remove(rigidBody);
		} 

	@Override
	public Collection<ContactPoint> getContactPoints() {
		List<ContactPoint> list = new ArrayList<>();
		
		for(int a = 0; a < _rigidBodies.size(); a++) {
			for(int b = a + 1; b < _rigidBodies.size(); b++) {
				list.addAll(generalCollision(_rigidBodies.get(a), _rigidBodies.get(b)));
			}
		}
		
		return list;
	}
	
	//determines if the broad shapes of any two rigid bodies collide, returning the contact points of the collision. If
	//the two objects do not collide, returns the empty collection.
	private Collection<ContactPoint> generalCollision(RigidBody A, RigidBody B){
		ArrayList<Vec2> boundariesA = A.getBroadShape().getCorners();
		ArrayList<Vec2> boundariesB = B.getBroadShape().getCorners();
		//checks to see which value has the lowest x position, then tests if the other box's leftmost x is within the range
		//of the first. Then it does the same for the y values.
		if((boundariesA.get(0).x() <= boundariesB.get(0).x()) && (boundariesB.get(0).x() <= boundariesA.get(3).x())){
			if((boundariesA.get(0).y() <= boundariesB.get(0).y()) && (boundariesB.get(0).y() <= boundariesA.get(1).y())){
				return properCollision(A, B);
			} if((boundariesB.get(0).y() <= boundariesA.get(0).y()) && (boundariesA.get(0).y() <= boundariesB.get(1).y())){
				return properCollision(A, B);
			}
		} if((boundariesB.get(0).x() <= boundariesA.get(0).x()) && (boundariesA.get(0).x() <= boundariesB.get(3).x())){
			if((boundariesA.get(0).y() <= boundariesB.get(0).y()) && (boundariesB.get(0).y() <= boundariesA.get(1).y())){
				return properCollision(A, B);
			} if((boundariesB.get(0).y() <= boundariesA.get(0).y()) && (boundariesA.get(0).y() <= boundariesB.get(1).y())){
				return properCollision(A, B);
			}
		}
		
		return Collections.emptyList();
	}
	
	private Collection<ContactPoint> properCollision(RigidBody A, RigidBody B){
		NarrowShape narrowShapeA = A.getNarrowShape();
		NarrowShape narrowShapeB = B.getNarrowShape();
		Collection<ContactPoint> output = new ArrayList<>();
		if(narrowShapeA.isColliding(narrowShapeB) || narrowShapeB.isColliding(narrowShapeA)){
			output.add(narrowShapeA.generateContactPoint(narrowShapeB));
		}
		return output;
	}
	
}
