package physics2d.collisiondetections;

import java.util.*;

import physics2d.body.RigidBody;
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
		if(A.getBroadShape().doesCollide(B.getBroadShape())) {
			return properCollision(A, B);
		} else {
			return Collections.emptyList();
		}
	}
	
	private Collection<ContactPoint> properCollision(RigidBody A, RigidBody B){
		return NarrowShapeCollisionDetection.getInstance().isColliding(A, B);
	}
	
}
