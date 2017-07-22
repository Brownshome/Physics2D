package physics2d.body;

import physics2d.maths.*;
import physics2d.collisiondetections.BroadShape;
import physics2d.collisiondetections.NarrowShape;

/** 
 * Represents a physical object. 
 **/
public interface RigidBody {
	/** Gets the position of the objects centre of mass in world space */
	MutableVec2 position();
	/** Gets the direction of the object in world space */
	MutableRotation direction();
	/** Gets the counter-clockwise rotational speed of the object in radians per second */
	double angularVelocity();
	/** Sets the angular velocity of the object */
	void angularVelocity(double x);
	/** Gets the velocity of the object in meters per second */
	MutableVec2 velocity();
	/** Gets the mass of the object in kg */
	double mass();
	/** Gets the rotational inertia of the object in kg m^2 */
	double inertia();
	/** Returns true if this object can move */
	boolean canMove();
	
	/** Applies an impulse to the shape */
	default void applyImpulse(MutableVec2 impulse, double angularImpulse) {
		impulse.scale(inverseMass());
		velocity().add(impulse);
		
		angularVelocity(angularVelocity() + angularImpulse * inverseInertia());
	}
	
	/**
	 * Converts a point to the velocity at that point in world space
	 * @param point The point relative to world space. This will be edited to
	 * be the velocity
	 **/
	default void convertToVelocity(MutableVec2 point) {
		transformToBodyCoordinates(point);
		
		MutableVec2 spinVelocity = new MutableVec2();
		spinVelocity.tangent(direction());
		spinVelocity.scale(angularVelocity());
		
		point.set(velocity());
		point.add(spinVelocity);
	}
	
	/** 
	 * Converts a point to a point in body space
	 * @param point The point in world space to be converted to body space 
	 **/
	default void transformToBodyCoordinates(MutableVec2 point) {
		point.subtract(position());
		direction().rotate(point);
	}	

	BroadShape getBroadShape();

	NarrowShape getNarrowShape();

	double restitution();
	
	default double inverseMass() {
		return canMove() ? 1.0 / mass() : 0.0;
	}

	default double inverseInertia() {
		return canMove() ? 1.0 / inertia() : 0.0;
	}
}
