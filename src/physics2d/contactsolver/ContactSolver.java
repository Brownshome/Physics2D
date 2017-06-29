package physics2d.contactsolver;

import java.util.Collection;

public interface ContactSolver {
	public void solveContactPoints(Collection<ContactPoint> contactPoints);
}
