package physics2d.contactsolver;

import java.util.Collection;

import physics2d.body.RigidBody;
import physics2d.maths.*;

/** 
 * Represents a single point of contact between two objects. 
 **/
public final class ContactPoint {
	private static double penetrationRelaxation = 0.75;
	
	/** The position of the contact in world space */
	private final Vec2 position;
	/** The normal of the contact going from bodyA to bodyB */
	private final Vec2 normal;
	/** The penetration of one object into another */
	private final double penetration;
	/** The current impulse that has been assigned to this point. Positive impulses are attractive */
	private double impulse;
	/** The last delta impulse that was applied */
	private double lastDelta;
	/** A variable to keep track of the abs impulses applied to detect convergance */
	private double absAccumulator;
	/** The objects that are colliding */
	private final RigidBody objectA, objectB;
	private final double restitution;
	/** The desired closing velocity */
	private final double desiredRelativeVelocity;
	private final double startingVelocityA, startingVelocityB;
	
	/** All inputs are safe and do not need to be copied */
	public ContactPoint(double penetration, Vec2 position, Vec2 normal, RigidBody a, RigidBody b) {
		this.position = position;
		this.normal = normal;
		this.impulse = 0;
		this.objectA = a;
		this.objectB = b;
		this.penetration = penetration * penetrationRelaxation;
		
		restitution = Math.min(objectA.restitution(), objectB.restitution());
		desiredRelativeVelocity = calculateDesiredVelocity();
		
		startingVelocityA = velocity(objectA);
		startingVelocityB = velocity(objectB);
	}

	private double velocity(RigidBody body) {
		MutableVec2 velocityA = new MutableVec2(position);
		body.convertToVelocity(velocityA);
		return velocityA.dot(normal);
	}

	private double relativeVelocity() {
		return velocity(objectA) - velocity(objectB);
	}

	private double calculateDesiredVelocity() {
		return -restitution * relativeVelocity();
	}
	
	void solveImpulse() {
		double velocityDelta = desiredRelativeVelocity - relativeVelocity();
		
		MutableVec2 tmp = new MutableVec2(position);
		tmp.subtract(objectA.position());
		tmp.tangent();
		double torqueA = tmp.dot(normal);
		
		tmp.set(position);
		tmp.subtract(objectB.position());
		tmp.tangent();
		double torqueB = tmp.dot(normal);
		
		//apply impulse
		double easeOfImpulse = objectA.inverseMass() + objectB.inverseMass()
				+ torqueA * torqueA * objectA.inverseInertia() + torqueB * torqueB * objectB.inverseInertia();
		
		lastDelta = velocityDelta / easeOfImpulse;
		
		//apply impulse to objects
		applyImpulse(lastDelta);
		
		impulse += lastDelta;
		absAccumulator += Math.abs(lastDelta);
	}

	private void applyImpulse(double deltaImpulse) {
		MutableVec2 impulseA = new MutableVec2(normal);
		impulseA.scale(deltaImpulse);
		objectA.applyImpulse(impulseA, 0);
		
		MutableVec2 impulseB = new MutableVec2(normal);
		impulseB.scale(-deltaImpulse);
		objectB.applyImpulse(impulseB, 0);
	}
	
	/** Returns a double in the range [0, 1] that
	 * represents how close the contact is to converging compared to the total impulse
	 * applied.
	 * 
	 * A value of 0 means that the constraint is not met and a value of 1 means
	 * the constraint is met perfectly */
	double convergance() {
		if(absAccumulator < Double.MIN_NORMAL)
			return 1.0;
		
		return 1.0 - Math.abs(lastDelta) / absAccumulator;
	}

	void clampImpulse() {
		if(impulse > 0) {
			applyImpulse(-impulse);
		}
	}

	void removePenetration() {
		/*
		 * Scale the penetration relaxation by the relative velocities
		 * Overall we move back by penetration units
		 * 
		 * TODO handle rotational penetration relaxation
		 */
		
		//Static object can't be moved :(
		if(!objectA.canMove() && !objectB.canMove()) {
			return;
		}
		
		if(!objectA.canMove()) {
			objectB.position().scaleAdd(normal, penetration);
			return;
		}
		
		if(!objectB.canMove()) {
			objectA.position().scaleAdd(normal, -penetration);
			return;
		}
		
		double sum = (Math.abs(startingVelocityA) + Math.abs(startingVelocityB));
		double moveA, moveB;
		if(sum < 1e-6) { //Objects are not moving much at all
			moveA = -penetration * 0.5;
			moveB = penetration * 0.5;
		} else {
			moveA = -penetration * Math.abs(startingVelocityA) / sum;
			moveB = penetration * Math.abs(startingVelocityB) / sum;
		}
		
		objectA.position().scaleAdd(normal, moveA);
		objectB.position().scaleAdd(normal, moveB);
	}
}
