package physics2d.collisiondetections;

import physics2d.body.RigidBody;
import physics2d.maths.Vec2;

public class TriangleShape extends NarrowShape {
	
	public TriangleShape(Vec2 position, Vec2 VertexA, Vec2 VertexB, Vec2 VertexC) {
		super(position, 3);
		LineShape edge1 = new LineShape(VertexA, VertexB);
		LineShape edge2 = new LineShape(VertexB, VertexC);
		LineShape edge3 = new LineShape(VertexC, VertexA);
		
		addShape(edge1);
		addShape(edge2);
		addShape(edge3);
	}

	@Override
	public BroadShape createBoundNarrowShape() {
		return BroadShape.getInfiniteBroadShape();
	}
	
}
