package physics2d.collisiondetections;

import physics2d.RigidBody;
import physics2d.contactsolver.ContactPoint;
import physics2d.maths.Vec2;

public class CircleShape extends NarrowShape{
	private double _radius;
	
	public CircleShape(Vec2 position, double radius, RigidBody rigidBody){
		super(position, 1, rigidBody);
		_radius = radius;
	}
	
	@Override
	public boolean isColliding(NarrowShape shape) {
		if(shape.getID() == 1){
			CircleShape circleShape = (CircleShape) shape;
			Vec2 shapePosition = shape.getPosition();
			
			return circleShape.getPosition().distanceSq(getPosition()) < (_radius + circleShape._radius) * (_radius + circleShape._radius);
		}
		
		return false;
	}
	
	public double getRadius(){
		return _radius;
	}
	
	public ContactPoint generateContactPoint(NarrowShape shape){
		if(shape.getID() == 1){
			Vec2 normal = getNormal(shape);
			double penetration = getPenetration(shape);
			Vec2 pos = getPos(shape);
			return new ContactPoint(penetration, pos, normal, this.getRigidBody(), shape.getRigidBody());
		}
		
		return null;
	}
	
	private Vec2 getNormal(NarrowShape shape){
		if(shape.getID() == 1){
			Vec2 normal = new Vec2(this.getPosition());
			normal.subtract(shape.getPosition());
			normal.normalize();
			return normal;
		}
		return this.getPosition();
	}
	
	private double getPenetration(NarrowShape shape){
		if(shape.getID() == 1){
			CircleShape circleShape = (CircleShape) shape;
			return _radius + circleShape.getRadius() - getPosition().distance(shape.getPosition());
		}
		
		return 0;
	}
	
	private Vec2 getPos(NarrowShape shape){
		if(shape.getID() == 1){
			Vec2 distance = new Vec2(this.getPosition());
			distance.add(shape.getPosition());
			distance.scale(0.5);
			return distance;
		}
		
		return this.getPosition();
	}

}
