package physics2d.body;

import physics2d.collisiondetections.*;
import physics2d.maths.*;

public class PlaneBody implements RigidBody {
	private final NarrowShape narrowShape;
	
	public PlaneBody(Vec2 position, Vec2 direction) {
		narrowShape = new PlaneShape(position, direction);
	}
	
	@Override
	public MutableVec2 position() {
		assert false;
		return null;
	}

	@Override
	public MutableRotation direction() {
		assert false;
		return null;
	}

	@Override
	public double angularVelocity() {
		return 0;
	}

	@Override
	public MutableVec2 velocity() {
		assert false;
		return null;
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
	public void applyImpulse(MutableVec2 impulse, double angularImpulse) {
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

	@Override
	public void angularVelocity(double x) {
		return;
	}
}
