package physics2d.integration;

import java.util.Collection;

import physics2d.RigidBody;
import physics2d.maths.Vec2;

public class EulerIntegrator extends Integrator{

	public EulerIntegrator(Collection<RigidBody> bodies, double singleStepSize, double maximumStepCount) {
		super(bodies, singleStepSize, maximumStepCount);
	}

	@Override
	protected void singleStep(double amount) {
		Collection<RigidBody> rigidBody = this.getCollection();
		for(RigidBody body : rigidBody){
			Vec2 velocity = new Vec2(body.velocity());
			velocity.scale(amount);
			body.position().add(velocity);
		}
		
	}

}
