package physics2d;

import java.util.Collection;

import physics2d.collisiondetections.CollisionDetector;
import physics2d.contactsolver.*;
import physics2d.integration.Integrator;

public class PhysicsWorld {
	private final CollisionDetector collisionDetector;
	private final ContactSolver contactSolver;
	private final Integrator integrator;
	
	public PhysicsWorld(CollisionDetector detector, ContactSolver solver, Integrator integrator) {
		collisionDetector = detector;
		contactSolver = solver;
		this.integrator = integrator;
	}
	
	public boolean tickWorld(double timeStep) {
		integrator.step(timeStep);
		Collection<ContactPoint> contacts = collisionDetector.getContactPoints();
		contactSolver.solveContactPoints(contacts);
	}
}
