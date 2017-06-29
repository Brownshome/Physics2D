package physics2d.collisiondetections;

import java.util.Collection;

import physics2d.maths.Vec2;

public abstract class NarrowShape {
	private Vec2 _position;
	private Collection<NarrowShape> _shapes;
	private int _ID;
	
	public abstract boolean isColliding(NarrowShape shape);
}
