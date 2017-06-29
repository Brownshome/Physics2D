package physics2d.collisiondetections;

import java.util.ArrayList;
import java.util.Collection;

import physics2d.RigidBody;
import physics2d.maths.Vec2;

public class BasicCollision implements CollisionDetector{
	private Collection<RigidBody> _rigidBodies = new ArrayList<RigidBody>(); 
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
		// TODO Auto-generated method stub
		return null;
	}
	
	//determines if the broad shapes of any two rigid bodies collide, returning the contact points of the collision. If
	//the two objects do not collide, returns the empty collection.
	private Collection<ContactPoint> generalCollision(RigidBody A, RigidBody B){
		ArrayList<Vec2> boundariesA = A.getBroadShape().getCorners();
		ArrayList<Vec2> boundariesB = B.getBroadShape().getCorners();
		//checks to see which value has the lowest x position, then tests if the other box's leftmost x is within the range
		//of the first. Then it does the same for the y values.
		if(boundariesA.get(0).x() < boundariesB.get(0).x() & boundariesB.get(0).x() < boundariesA.get(3).x()){
			if(boundariesA.get(0).y() < boundariesB.get(0).y() & boundariesB.get(0).y() < boundariesA.get(1).y()){
				return properCollision(A, B);
			} if(boundariesB.get(0).y() < boundariesA.get(0).y() & boundariesA.get(0).y() < boundariesB.get(1).y()){
				return properCollision(A, B);
			}
		} if(boundariesB.get(0).x() < boundariesA.get(0).x() & boundariesA.get(0).x() < boundariesB.get(3).x()){
			if(boundariesA.get(0).y() < boundariesB.get(0).y() & boundariesB.get(0).y() < boundariesA.get(1).y()){
				return properCollision(A, B);
			} if(boundariesB.get(0).y() < boundariesA.get(0).y() & boundariesA.get(0).y() < boundariesB.get(1).y()){
				return properCollision(A, B);
			}
		}
		return new ArrayList<ContactPoint>();
	}
	
	private Collection<ContactPoint> properCollision(RigidBody A, RigidBody B){
		
	}
	
}
