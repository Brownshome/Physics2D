package physics2d.collisiondetections;

import physics2d.maths.Vec2;

public class BasicRectangleShape extends NarrowShape{
	
	public BasicRectangleShape(Vec2 position){
		super(position, 2);
	}
	
	
	@Override
	public boolean isColliding(NarrowShape shape) {
		// TODO Auto-generated method stub
		return false;
	}

}
