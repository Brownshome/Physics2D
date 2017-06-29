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
			if(Math.sqrt(Math.pow(Math.abs(shapePosition.x() - this.getPosition().x()), 2) 
				+ Math.pow(Math.abs(shapePosition.y() - this.getPosition().y()), 2)) < _radius + circleShape.getRadius()){
				return true;
			}
			return false;
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
			Vec2 distance = new Vec2(this.getPosition());
			distance.subtract(shape.getPosition());
			return _radius + circleShape.getRadius() - Math.sqrt(distance.distanceSquared(distance));
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
