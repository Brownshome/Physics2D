package physics2d.contactsolver;

import java.util.Collection;

import physics2d.collisiondetections.ContactPoint;

public interface ContactSolver {
	public void solveContactPoints(Collection<ContactPoint> contactPoints);
}
