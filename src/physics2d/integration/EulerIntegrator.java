package physics2d.integration;

import java.util.Collection;

import physics2d.body.RigidBody;
import physics2d.maths.*;

public class EulerIntegrator extends Integrator{

	public EulerIntegrator(Collection<RigidBody> bodies, double singleStepSize, double maximumStepCount) {
		super(bodies, singleStepSize, maximumStepCount);
	}

	@Override
	protected void singleStep(double amount) {
		Collection<RigidBody> rigidBody = this.getCollection();
		for(RigidBody body : rigidBody){
			if(!body.canMove())
				continue;
			
			MutableVec2 velocity = new MutableVec2(body.velocity());
			velocity.scale(amount);
			body.position().add(velocity);
			
			body.velocity().add(0, amount * 10);
		}
	}
}
