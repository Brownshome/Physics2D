package physics2d.collisiondetections;

import java.util.Collection;

import physics2d.body.RigidBody;
import physics2d.contactsolver.ContactPoint;
import physics2d.maths.Vec2;
import physics2d.update.*;

/** Represents the narrow phase collision object. These objects can rotate and move but all other
 * properties are immutable. */
public abstract class NarrowShape {
	private Vec2 _position;
	private Collection<NarrowShape> _shapes;
	private int _ID;
	private RigidBody _rigidBody;
	
	public NarrowShape(Vec2 position, int id){
		_position = position;
		_ID = id;
	}
	
	public int getID(){
		return _ID;
	}
	
	public Vec2 getPosition(){
		return _position;
	}
	
	public void addShape(NarrowShape shape){
		_shapes.add(shape);
	}

	/** This method is a helper method that creates a BroadShape that surrounds
	 * this shape. */
	public abstract BroadShape createBoundBroadShape();
}
