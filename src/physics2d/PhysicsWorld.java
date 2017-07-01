package physics2d;

import java.util.Collection;

import physics2d.collisiondetections.CollisionDetector;
import physics2d.contactsolver.*;
import physics2d.integration.Integrator;

public class PhysicsWorld {
	private final CollisionDetector collisionDetector;
	private final ContactSolver contactSolver;
	private final Integrator integrator;
	
	private int internalIterations;
	
	public PhysicsWorld(CollisionDetector detector, ContactSolver solver, Integrator integrator) {
		this(detector, solver, integrator, 1);
	}
	
	public PhysicsWorld(CollisionDetector detector, ContactSolver solver, Integrator integrator, int internalIterations) {
		collisionDetector = detector;
		contactSolver = solver;
		this.integrator = integrator;
		this.internalIterations = internalIterations;
	}
	
	public boolean tickWorld(double timeStep) {
		boolean success = integrator.step(timeStep);
		
		for(int i = 0; i < internalIterations; i++) {
			contactSolver.solveContactPoints(collisionDetector.getContactPoints());
		}
		
		return success;
	}
}
