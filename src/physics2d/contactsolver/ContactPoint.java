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
	/** The current impulse that has been assigned to this point */
	private double impulse;
	/** The objects that are colliding */
	final RigidBody objectA, objectB;
	private final double desiredRelativeVelocity;
	
	public ContactPoint(double penetration, Vec2 position, Vec2 normal, RigidBody a, RigidBody b) {
		this.position = position;
		this.normal = normal;
		this.impulse = 0;
		this.objectA = a;
		this.objectB = b;
		this.penetration = penetration;
		
		this.desiredRelativeVelocity = calculateDesiredVelocity();
	}

	private double calculateDesiredVelocity() {
		return 0;
	}
	
	void applyImpulse() {
		assert false : "TODO";
	}
	
	/** Returns a double in the range [0, 1] that
	 * represents how closely the velocity constraint is met.
	 * 
	 * A value of 0 means that the constraint is not met and a value of 1 means
	 * the constraint is met perfectly */
	double accuracy() {
		Vec2 velocityA = new Vec2(position);
		Vec2 velocityB = new Vec2(position);
		
		objectA.convertToVelocity(velocityA);
		objectB.convertToVelocity(velocityB);
		
		double relativeVelocity = velocityA.dot(normal) - velocityB.dot(normal);
		
		double rawAccuracy = Math.abs(relativeVelocity - desiredRelativeVelocity);
		
		return Math.min(Math.max(rawAccuracy / desiredRelativeVelocity, 1.0), 0.0);
	}
}
