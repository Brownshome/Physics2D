package physics2d.collisiondetections;

import java.util.Collection;

import physics2d.contactsolver.ContactPoint;

@FunctionalInterface
public interface NSNSCollisionFunction {
	Collection<ContactPoint> generateContactPoints(NarrowShape A, NarrowShape B);
}
