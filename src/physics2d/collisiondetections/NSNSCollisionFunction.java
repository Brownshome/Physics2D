package physics2d.collisiondetections;

import java.util.Collection;

import physics2d.body.RigidBody;
import physics2d.contactsolver.ContactPoint;

@FunctionalInterface
public interface NSNSCollisionFunction {
	Collection<ContactPoint> generateContactPoints(RigidBody A, RigidBody B);
}
