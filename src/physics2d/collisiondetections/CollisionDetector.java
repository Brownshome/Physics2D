package physics2d.collisiondetections;

import java.util.Collection;

import physics2d.RigidBody;
import physics2d.contactsolver.ContactPoint;

public interface CollisionDetector{
	 void addRigidBody(RigidBody rigidBody);
	 void removeRigidBody(RigidBody rigidBody);
	 Collection<ContactPoint> getContactPoints();

}
