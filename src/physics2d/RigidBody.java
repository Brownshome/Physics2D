package physics2d;

import physics2d.maths.*;
import physics2d.collisiondetections.BroadShape;
import physics2d.collisiondetections.NarrowShape;

/** 
 * Represents a physical object. 
 **/
public interface RigidBody {
	/** Gets the position of the objects centre of mass in world space */
	Vec2 position();
	/** Gets the direction of the object in world space */
	Rotation direction();
	/** Gets the counter-clockwise rotational speed of the object in radians per second */
	double angularVelocity();
	/** Gets the velocity of the object in meters per second */
	Vec2 velocity();
	/** Gets the mass of the object in kg */
	double mass();
	/** Gets the rotational inertia of the object in kg m^2 */
	double inertia();
	/** Applies an impulse to the shape */
	void applyImpulse(Vec2 impulse, double angularImpulse);
	/** Returns true if this object can move */
	boolean canMove();
	
	
	/**
	 * Converts a point to the velocity at that point in world space
	 * @param point The point relative to world space. This will be edited to
	 * be the velocity
	 **/
	default void convertToVelocity(Vec2 point) {
		transformToBodyCoordinates(point);
		
		Vec2 spinVelocity = new Vec2();
		direction().tangent(spinVelocity);
		spinVelocity.scale(angularVelocity());
		
		point.add(velocity());
	}
	
	/** 
	 * Converts a point to a point in body space
	 * @param point The point in world space to be converted to body space 
	 **/
	default void transformToBodyCoordinates(Vec2 point) {
		point.subtract(position());
		direction().rotate(point);
	}	

	BroadShape getBroadShape();

	NarrowShape getNarrowShape();

	double restitution();
	
	default double inverseMass() {
		return canMove() ? 1.0 / mass() : 0.0;
	}

}
