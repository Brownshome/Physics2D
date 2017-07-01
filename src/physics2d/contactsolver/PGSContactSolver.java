package physics2d.contactsolver;

import java.util.Collection;

/**
 * Implements a projected Gauss-Seidel iterative method of solving the contact points.
 * @author James
 *
 */
public class PGSContactSolver implements ContactSolver {
	private final int maximumIterationCount;
	private final double accuracyTarget;
	
	public PGSContactSolver(int maximumIterationCount, double accuracyTarget) {
		this.maximumIterationCount = maximumIterationCount;
		this.accuracyTarget = accuracyTarget;
	}
	
	@Override
	public void solveContactPoints(Collection<ContactPoint> contactPoints) {
		/* Iterate though the list on contact points until an accuracy requirement is met or
		 * the maximum number of iterations is taken.
		 * 
		 * We may need to partition the contact points set into unrelated sets if needed.
		 */
		
		for(int i = 0; i < maximumIterationCount; i++) {
			if(solveSingleIteration(contactPoints))
				break;
		}
		
		//finalise points
		for(ContactPoint point : contactPoints) {
			point.clampImpulse();
			point.removePenetration();
		}
	}

	/** Returns true if the collision constraint has been met */
	private boolean solveSingleIteration(Collection<ContactPoint> contactPoints) {
		double convergance = 0;
		
		for(ContactPoint point : contactPoints) {
			point.solveImpulse();
		}
		
		for(ContactPoint point : contactPoints) {
			convergance += point.convergance();
		}
		
		return convergance / contactPoints.size() >= accuracyTarget;
	}
}
