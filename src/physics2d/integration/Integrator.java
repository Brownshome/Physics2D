package physics2d.integration;

import java.util.Collection;

import physics2d.RigidBody;

public abstract class Integrator {
	private final Collection<RigidBody> bodies;
	private final double singleStepSize;
	private final double maximumStepCount;
	
	public Integrator(Collection<RigidBody> bodies, double singleStepSize, double maximumStepCount) {
		this.bodies = bodies;
		this.singleStepSize = singleStepSize;
		this.maximumStepCount = maximumStepCount;
	}
	
	public boolean step(double timeStep) {
		int count = 0;
		
		while(timeStep > 0) {
			if(count++ > maximumStepCount) {
				return false;
			}
			
			double step = Math.max(singleStepSize, timeStep);
			timeStep -= step;
			singleStep(step);
		}
		
		return true;
	}
	
	/** Steps the simulation by a single step.
	 * 
	 * @param amount The amount of seconds to tick, this is guaranteed to be in the range
	 * (0, singleStepSize]
	 **/
	protected abstract void singleStep(double amount);
	
	protected Collection<RigidBody> getCollection(){
		return bodies;
	}
}
