package physics2d.collisiondetections;

import java.util.Collection;

import physics2d.RigidBody;

public interface CollisionDetector{
	 void addRigidBody(RigidBody rigidBody);
	 void removeRigidBody(RigidBody rigidBody);
	 Collection<ContactPoint> getContactPoints();

}
