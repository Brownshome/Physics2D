package physics2d;

import physics2d.collisiondetections.*;
import physics2d.contactsolver.ContactPoint;
import physics2d.maths.*;

public class CircleBody implements RigidBody {
	private final double radius;
	
	private final Vec2 position, velocity;
	private final Rotation direction;
	private final double mass, inertia;
	private double angularVelocity;
	
	private final BroadShape broadShape;
	private final NarrowShape narrowShape;
	
	public CircleBody(Vec2 position, Vec2 velocity, double size) {
		radius = size / 2;
		
		Vec2 tmp = new Vec2(position);
		tmp.add(-size / 2, -size / 2);
		broadShape = new BroadShape(tmp, size, size);
		
		this.position = new Vec2(position) {
			@Override
			public void x(double x) {
				super.x(x);
				tmp.x(x - size / 2);
			}
			
			@Override
			public void y(double y) {
				super.y(y);
				tmp.y(y - size / 2);
			}
		};
		
		this.velocity = velocity;
		this.mass = 10;
		this.inertia = 10;
		this.angularVelocity = 0;
		this.direction = new Rotation();
		
		narrowShape = new CircleShape(this.position, size / 2, this);
	}
	
	@Override
	public Vec2 position() {
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
	public Vec2 velocity() {
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
	public void applyImpulse(Vec2 impulse, double angularImpulse) {
		impulse.scale(inverseMass());
		velocity.add(impulse);
		
		angularImpulse *= inverseInertia();
		angularVelocity += angularImpulse;
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
		return 0.95;
	}

	public double radius() {
		return radius;
	}
}
