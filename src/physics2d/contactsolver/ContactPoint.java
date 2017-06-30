package physics2d.contactsolver;

import java.util.Collection;

import physics2d.RigidBody;
import physics2d.maths.Vec2;

/** 
 * Represents a single point of contact between two objects. 
 **/
public final class ContactPoint {
	/** The position of the contact in world space */
	final Vec2 position;
	/** The normal of the contact going from bodyA to bodyB */
	final Vec2 normal;
	/** The penetration of one object into another */
	final double penetration;
	/** The current impulse that has been assigned to this point. Positive impulses are attractive */
	private double impulse;
	/** The objects that are colliding */
	final RigidBody objectA, objectB;
	final double restitution;
	/** The desired closing velocity */
	private final double desiredRelativeVelocity;
	private final double startingVelocityA, startingVelocityB;
	
	public ContactPoint(double penetration, Vec2 position, Vec2 normal, RigidBody a, RigidBody b) {
		this.position = position;
		this.normal = normal;
		this.impulse = 0;
		this.objectA = a;
		this.objectB = b;
		this.penetration = penetration;
		
		desiredRelativeVelocity = calculateDesiredVelocity();
		restitution = Math.min(objectA.restitution(), objectB.restitution());
		
		startingVelocityA = velocity(objectA);
		startingVelocityB = velocity(objectB);
	}

	private double velocity(RigidBody body) {
		Vec2 velocityA = new Vec2(position);
		body.convertToVelocity(velocityA);
		return velocityA.dot(normal);
	}

	private double relativeVelocity() {
		return velocity(objectA) - velocity(objectB);
	}

	private double calculateDesiredVelocity() {
		return 0;
	}
	
	void solveImpulse() {
		//apply impulse
		double deltaImpulse = -(1 + restitution) * relativeVelocity() / (objectA.inverseMass() + objectB.inverseMass());
		
		//apply impulse to objects
		applyImpulse(deltaImpulse);
		
		impulse += deltaImpulse;
	}

	private void applyImpulse(double deltaImpulse) {
		Vec2 impulseA = new Vec2(normal);
		impulseA.scale(deltaImpulse);
		objectA.applyImpulse(impulseA, 0);
		
		Vec2 impulseB = new Vec2(normal);
		impulseB.scale(-deltaImpulse);
		objectB.applyImpulse(impulseB, 0);
	}
	
	/** Returns a double in the range [0, 1] that
	 * represents how closely the velocity constraint is met.
	 * 
	 * A value of 0 means that the constraint is not met and a value of 1 means
	 * the constraint is met perfectly */
	double accuracy() {
		double rawAccuracy = Math.abs(relativeVelocity() - desiredRelativeVelocity);
		
		return 1.0 - Math.min(Math.max(rawAccuracy / Math.max(Math.abs(desiredRelativeVelocity), 1e-6), 1.0), 0.0);
	}

	void clampImpulse() {
		if(impulse < 0) {
			applyImpulse(-impulse);
		}
	}

	void removePenetration() {
		/*
		 * Scale the penetration relaxation by the relative velocities
		 * Overall we move back by penetration units
		 */
		
		//Static object can't be moved :(
		if(!objectA.canMove() && !objectB.canMove()) {
			return;
		}
		
		if(!objectA.canMove()) {
			objectB.position().scaleAdd(normal, -penetration);
			return;
		}
		
		if(!objectB.canMove()) {
			objectA.position().scaleAdd(normal, penetration);
			return;
		}
		
		double sum = (Math.abs(startingVelocityA) + Math.abs(startingVelocityB)) * 1.2;
		double moveA, moveB;
		if(sum < 1e-6) { //Objects are not moving much at all
			moveA = penetration * 0.5;
			moveB = -penetration * 0.5;
		} else {
			moveA = penetration * Math.abs(startingVelocityA) / sum;
			moveB = -penetration * Math.abs(startingVelocityB) / sum;
		}
		
		objectA.position().scaleAdd(normal, moveA);
		objectB.position().scaleAdd(normal, moveB);
	}
}
