package physics2d.body;

import physics2d.collisiondetections.*;
import physics2d.maths.*;

/**
 * A general purpose implementation of a rigid body that can move and rotate
 */
public class DynamicBody implements RigidBody {
	private final MutableVec2 position, velocity;
	private final Rotation direction;
	private final double mass, inertia, restitution;
	private double angularVelocity;
	
	private final BroadShape broadShape;
	private final NarrowShape narrowShape;
	
	public DynamicBody(MutableVec2 position, Rotation direction, MutableVec2 velocity, double angularVelocity,
			BroadShape broadShape, NarrowShape narrowShape, double mass, double inertia, double restitution) {
		
		this.position = position;
		this.velocity = velocity;
		this.direction = direction;
		this.angularVelocity = angularVelocity;
		this.mass = mass;
		this.inertia = inertia;
		this.restitution = restitution;
		this.broadShape = broadShape;
		this.narrowShape = narrowShape;
	}
	
	public DynamicBody(MutableVec2 position, Rotation direction, MutableVec2 velocity, double angularVelocity,
			NarrowShape narrowShape, double mass, double inertia, double restitution) {
		this(position, direction, velocity, angularVelocity, narrowShape.createBoundNarrowShape(), narrowShape,
				mass, inertia, restitution);
	}

	@Override
	public MutableVec2 position() {
		return position;
	}

	@Override
	public Rotation direction() {
		return direction;
	}

	@Override
	public double angularVelocity() {
		return angularVelocity;
	}

	@Override
	public void angularVelocity(double vel) {
		angularVelocity = vel;
	}

	@Override
	public MutableVec2 velocity() {
		return velocity;
	}

	@Override
	public double mass() {
		return mass;
	}

	@Override
	public double inertia() {
		return inertia;
	}

	@Override
	public boolean canMove() {
		return true;
	}

	@Override
	public BroadShape getBroadShape() {
		return broadShape;
	}

	@Override
	public NarrowShape getNarrowShape() {
		return narrowShape;
	}

	@Override
	public double restitution() {
		return restitution;
	}
}
