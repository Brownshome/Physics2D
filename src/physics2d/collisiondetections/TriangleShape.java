package physics2d.collisiondetections;

import java.util.ArrayList;
import java.util.Collection;

import physics2d.body.RigidBody;
import physics2d.contactsolver.ContactPoint;
import physics2d.maths.MutableRotation;
import physics2d.maths.MutableVec2;
import physics2d.maths.Vec2;

public class TriangleShape extends ConvexHull {
	
	private TriangleShape(Vec2 position, ArrayList<MutableVec2> vertices) {
		super(position, vertices);
		
	}

	public ConvexHull instantiateConvexHull(Vec2 position, ArrayList<MutableVec2> vertices){
		MutableVec2 A;
		MutableVec2 B;
		MutableVec2 C;
		
		if(vertices.size() <= 3){
			return null;
		}
		
		for(int i = 0; i < vertices.size() - 2; i++){
			A = new MutableVec2(vertices.get(i));
			B = new MutableVec2(vertices.get(i + 1));
			C = new MutableVec2(vertices.get(i + 2));
			
			if((A.x() > B.x() && C.x() > B.x()) || (A.y() > B.y() && C.y() > B.y())){
				return null;
			}
		}
		
		A = new MutableVec2(vertices.get(vertices.size()));
		B = new MutableVec2(vertices.get(0));
		C = new MutableVec2(vertices.get(1));
		
		if((A.x() > B.x() && C.x() > B.x()) || (A.y() > B.y() && C.y() > B.y())){
			return null;
		}
		
		A = new MutableVec2(vertices.get(vertices.size() - 1));
		B = new MutableVec2(vertices.get(vertices.size()));
		C = new MutableVec2(vertices.get(0));
		
		if((A.x() > B.x() && C.x() > B.x()) || (A.y() > B.y() && C.y() > B.y())){
			return null;
		}
		
		return new TriangleShape(position, vertices);
	}
	@Override
	public BroadShape createBoundNarrowShape() {
		return BroadShape.getInfiniteBroadShape();
	}
	
	/**Find the angle between the two convex hull shapes and the midpoint between the two centres. Draw a line at the average angle between the two
	 * shapes. If this line intersects any of the shapes that are colliding then the shapes collide.
	 */
	
	public Collection<ContactPoint> ConvexHullConvexHullCollision(RigidBody B) {
		ConvexHull triangle = this;
		ConvexHull hull = (ConvexHull) B;
		
		MutableVec2 distance = new MutableVec2(this.getPosition());
		distance.subtract(hull.getPosition());
		distance.scale(0.5);
		
		MutableVec2 line = new MutableVec2(distance);
		ArrayList<MutableVec2> allVertices = new ArrayList<MutableVec2>(this.getVertices());
		allVertices.addAll(hull.getVertices());
		double maxLength = 0;
		for(int i = 0; i < allVertices.size(); i++) {
			if(maxLength < allVertices.get(i).length()) {
				maxLength = allVertices.get(i).length();
			}
		}
		
		MutableVec2 horizontal = new MutableVec2(1, 0);
		horizontal.scale(maxLength / 2);
		line.subtract(horizontal);
		horizontal.scale(2);
		line.add(horizontal);
		
		int vertexANumber = 0;
		int vertexBNumber = 0;
		double smallest = distance.length();
		MutableVec2 vertex;
		distance.add(this.getPosition());
		
		for(int i = 0; i < triangle.getVertices().size(); i++) {
			vertex = new MutableVec2(triangle.getVertices().get(i));
			vertex.add(triangle.getPosition());
			if(distance.length() - vertex.length() < smallest){
				smallest = distance.length() - vertex.length();
				vertexANumber = i;
			}
		}
		
		for(int i = 0; i < hull.getVertices().size(); i++) {
			vertex = new MutableVec2(hull.getVertices().get(i));
			vertex.add(hull.getPosition());
			if(distance.length() - vertex.length() < smallest){
				smallest = distance.length() - vertex.length();
				vertexBNumber = i;
			}
		}
		
		MutableVec2 edgeA = hull.getEdges().get(vertexANumber - 1);
		MutableVec2 edgeB = hull.getEdges().get(vertexBNumber);
		
		double angleA = edgeA.dot(line) / (edgeA.length() * line.length());
		double angleB = edgeB.dot(line) / (edgeB.length() * line.length());
		double angle = (angleA + angleB) / 2;
		
		MutableRotation rotation = new MutableRotation(angle);
		rotation.rotate(line);
		
		Collection<ContactPoint> output;
		NarrowShapeCollisionDetection detector = NarrowShapeCollisionDetection.getInstance();
		
		output.addAll(detector.isColliding(edgeA, line));
		
		
	}
	
}
