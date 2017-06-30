package physics2d;

import physics2d.collisiondetections.*;
import physics2d.maths.*;

public class PlaneBody implements RigidBody {
	private final NarrowShape narrowShape;
	
	public PlaneBody(Vec2 position, Vec2 direction) {
		narrowShape = new PlaneShape(position, this, direction);
	}
	
	@Override
	public Vec2 position() {
		return new Vec2();
	}

	@Override
	public Rotation direction() {
		return new Rotation();
	}

	@Override
	public double angularVelocity() {
		return 0;
	}

	@Override
	public Vec2 velocity() {
		return new Vec2(0, 0);
	}

	@Override
	public double mass() {
		return 0;
	}

	@Override
	public double inertia() {
		return 0;
	}

	@Override
	public void applyImpulse(Vec2 impulse, double angularImpulse) {
		return;
	}

	@Override
	public boolean canMove() {
		return false;
	}

	@Override
	public BroadShape getBroadShape() {
		return BroadShape.getInfiniteBroadShape();
	}

	@Override
	public NarrowShape getNarrowShape() {
		return narrowShape;
	}

	@Override
	public double restitution() {
		return 1.0;
	}
}
