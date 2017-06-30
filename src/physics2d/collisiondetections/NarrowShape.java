package physics2d.collisiondetections;

import java.util.Collection;

import physics2d.RigidBody;
import physics2d.contactsolver.ContactPoint;
import physics2d.maths.Vec2;

public abstract class NarrowShape {
	private Vec2 _position;
	private Collection<NarrowShape> _shapes;
	private int _ID;
	private RigidBody _RigidBody;
	
	public NarrowShape(Vec2 position, int id, RigidBody rigidBody){
		_position = position;
		_ID = id;
		_RigidBody = rigidBody;
	}
	
	public int getID(){
		return _ID;
	}
	
	public Vec2 getPosition(){
		return _position;
	}
	
	public RigidBody getRigidBody(){
		return _RigidBody;
	}
}
