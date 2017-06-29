package physics2d.collisiondetections;

import java.util.Collection;

public interface CollisionDetector{
	 void addRigidBody(RigidBody rigidBody);
	 void removeRigidBody(RigidBody rigidBody);
	 Collection<ContactPoint> getContactPoints();
}
