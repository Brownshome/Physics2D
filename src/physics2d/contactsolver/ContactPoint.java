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
	
	public ContactPoint(double penetration, Vec2 position, Vec2 normal, RigidBody a, RigidBody b) {
		this.position = position;
		this.normal = normal;
		this.impulse = 0;
		this.objectA = a;
		this.objectB = b;
		this.penetration = penetration;
		
		desiredRelativeVelocity = calculateDesiredVelocity();
		restitution = Math.min(objectA.restitution(), objectB.restitution());
	}

	private double relativeVelocity() {
		Vec2 velocityA = new Vec2(position);
		Vec2 velocityB = new Vec2(position);
		
		objectA.convertToVelocity(velocityA);
		objectB.convertToVelocity(velocityB);
		
		return velocityA.dot(normal) - velocityB.dot(normal);
	}

	private double calculateDesiredVelocity() {
		return 0;
	}
	
	void applyImpulse() {
		//apply impulse
		double deltaImpulse = -(1 + restitution) * relativeVelocity() / (objectA.inverseMass() + objectB.inverseMass());
		
		//apply impulse to objects
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
		
		return Math.min(Math.max(rawAccuracy / desiredRelativeVelocity, 1.0), 0.0);
	}
}
