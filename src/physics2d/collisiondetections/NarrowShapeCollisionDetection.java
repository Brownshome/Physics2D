package physics2d.collisiondetections;

import java.util.*;

import physics2d.body.RigidBody;
import physics2d.contactsolver.ContactPoint;
import physics2d.maths.*;

public class NarrowShapeCollisionDetection {
	private static NarrowShapeCollisionDetection _isInitialised;
	private Map<CollisionType, NSNSCollisionFunction> _HashMap; 


	//id 1 = Circle, id 3 = ConvexHull, id 4 = Plane, id 5 = Line
	private  NarrowShapeCollisionDetection(){
		_HashMap = new HashMap<>();
		_HashMap.put(new CollisionType(1,1), this::circleCircleCollision);
		_HashMap.put(new CollisionType(4, 1), this::planeCircleCollision);
		_HashMap.put(new CollisionType(1, 4), (a, b) -> planeCircleCollision(b, a));
		_HashMap.put(new CollisionType(4, 4), (a, b) -> Collections.emptyList());
		_HashMap.put(new CollisionType(2, 1), RectangleShape::rectangleCircleCollision);
		_HashMap.put(new CollisionType(1, 2), (a, b) -> RectangleShape.rectangleCircleCollision(b, a));
		_HashMap.put(new CollisionType(4, 2), RectangleShape::planeRectangleCollision);
		_HashMap.put(new CollisionType(2, 4), (a, b) -> RectangleShape.planeRectangleCollision(b, a));
		_HashMap.put(new CollisionType(2, 2), RectangleShape::rectangleRectangleCollision);
		_HashMap.put(new CollisionType(4, 5), this::planeLineCollision);
		_HashMap.put(new CollisionType(5, 4), (a, b) -> planeLineCollision(b, a));
		_HashMap.put(new CollisionType(5, 1), this::lineCircleCollision);
		_HashMap.put(new CollisionType(1, 5), (a, b) -> lineCircleCollision(b, a));
		_HashMap.put(new CollisionType(5, 5), this::lineLineCollision);
		_HashMap.put(new CollisionType(3, 1), this::convexHullCircleCollision);
		_HashMap.put(new CollisionType(3, 3), this::convexHullConvexHullCollision);
		_HashMap.put(new CollisionType(3, 4), (a, b) -> planeConvexHullCollision(b, a));
		_HashMap.put(new CollisionType(3, 5), this::convexHullLineCollision);
		_HashMap.put(new CollisionType(1, 3), (a, b) -> convexHullCircleCollision(b, a));
		_HashMap.put(new CollisionType(4, 3), this::planeConvexHullCollision);
		_HashMap.put(new CollisionType(5, 3), (a, b) -> convexHullLineCollision(b, a));
		
		
	}

	public static NarrowShapeCollisionDetection getInstance(){
		if(_isInitialised == null){
			_isInitialised = new NarrowShapeCollisionDetection();
		} return _isInitialised;
	}



	public Collection<ContactPoint> isColliding(RigidBody A, RigidBody B){
		return _HashMap.get(new CollisionType(A, B)).generateContactPoints(A, B);
	}
	
	private Collection<ContactPoint> convexHullCircleCollision(RigidBody A, RigidBody B){
		ConvexHull hull = (ConvexHull) A;
		CircleShape circle = (CircleShape) B;
		ArrayList<ContactPoint> output = new ArrayList<ContactPoint>();
		
		ArrayList<MutableVec2> edges = hull.getEdges();
		ArrayList<MutableVec2> vertices = hull.getVertices();
		for(int i = 0; i < edges.size(); i++){
			MutableVec2 normal = new MutableVec2(edges.get(i));
			normal.normalize();
			
			double length;
			if(normal.x() != 0){
				length = edges.get(i).x() / normal.x();
			}
			else {
				length = edges.get(i).y() / normal.y();
			}
			LineShape X = new LineShape(vertices.get(i), length , normal);
			output.addAll(lineCircleCollision((RigidBody) X , (RigidBody) circle));
		}
		return output;
	}
	
	private Collection<ContactPoint> triangleRectangleCollision(RigidBody A, RigidBody B){
		assert false;
		return null;
	}
	
	private Collection<ContactPoint> convexHullConvexHullCollision(RigidBody A, RigidBody B){
		assert false;
		return null;
	}
	
	private Collection<ContactPoint> planeConvexHullCollision(RigidBody A, RigidBody B){
		assert false;
		return null;
	}
	
	private Collection<ContactPoint> convexHullLineCollision(RigidBody A, RigidBody B){
		assert false;
		return null;
	}

	private Collection<ContactPoint> circleCircleCollision(RigidBody A, RigidBody B){
		CircleShape circleShapeA = (CircleShape) A.getNarrowShape();
		Vec2 APosition = circleShapeA.getPosition();
		CircleShape circleShapeB = (CircleShape) B.getNarrowShape();
		Vec2 BPosition = circleShapeB.getPosition();
		Collection<ContactPoint> output = new ArrayList<ContactPoint>();

		if(APosition.distanceSq(BPosition) < (circleShapeB.getRadius() + circleShapeA.getRadius()) * (circleShapeB.getRadius() + circleShapeA.getRadius())){
			MutableVec2 normal = new MutableVec2(circleShapeB.getPosition());
			normal.subtract(circleShapeA.getPosition());
			normal.normalize();
			double penetration = circleShapeA.getRadius() + circleShapeB.getRadius() - circleShapeA.getPosition().distance(circleShapeB.getPosition());
			MutableVec2 position = new MutableVec2(circleShapeA.getPosition());
			position.add(circleShapeB.getPosition());
			position.scale(0.5);
			output.add(new ContactPoint(penetration, position, normal, A, B));
		} 

		return output;
	}

	private Collection<ContactPoint> planeCircleCollision(RigidBody A, RigidBody B){
		PlaneShape plane = (PlaneShape) A.getNarrowShape();
		CircleShape circle = (CircleShape) B.getNarrowShape();

		double distance =  plane.getNormal().dot(circle.getPosition()) - plane.getNormal().dot(plane.getPosition());

		if(distance < circle.getRadius()) {
			double penetration = circle.getRadius() - distance;

			MutableVec2 circleToPlane = new MutableVec2(plane.getNormal());
			circleToPlane.scale(-distance - penetration / 2);
			circleToPlane.add(circle.getPosition());

			return Arrays.asList(new ContactPoint(penetration, circleToPlane, plane.getNormal(), A, B));
		}

		return Collections.emptyList();
	}

	private Collection<ContactPoint> planeLineCollision(RigidBody A, RigidBody B){
		PlaneShape plane = (PlaneShape) A.getNarrowShape();
		LineShape line = (LineShape) B.getNarrowShape();
		Collection<ContactPoint> output = new ArrayList<>();
		MutableVec2 direction = new MutableVec2(plane.getNormal());
		direction.tangent();
		MutableVec2 distance = new MutableVec2(line.getPosition());
		distance.subtract(plane.getPosition());
		
		if(line.getDirection().x() != direction.x() && line.getDirection().y() != direction.y()){
			/*checks where the theoretical intersection point would be*/
			double secondScale = ((distance.x() * direction.y()) - (distance.y() * direction.x())) / 
					((direction.y() * line.getDirection().x()) - (line.getDirection().y() * direction.x())); 	

			double firstScale = ((distance.y() * line.getDirection().x()) - (distance.x() * line.getDirection().y())) / 
					((direction.y() * line.getDirection().x()) - (line.getDirection().y() * direction.x())); 	
			
			/*checks if that point of intersection is beyond the size of the lines*/
			if(Math.abs(secondScale) < line.getLength()){
				double penetration = line.getLength() - Math.abs(secondScale);
				MutableVec2 position = new MutableVec2(line.getDirection());
				position.scale(Math.abs(secondScale));
				position.add(line.getPosition());
				output.add(new ContactPoint(penetration, position, plane.getNormal(), A, B));
			}
		} else {
			
		}
		return output;
	}

	private Collection<ContactPoint> lineRectangleCollision(RigidBody A, RigidBody B){
		assert false;

		return null;
	}

	private Collection<ContactPoint> lineCircleCollision(RigidBody A, RigidBody B){
		LineShape line = (LineShape) A.getNarrowShape();
		CircleShape circle = (CircleShape) B.getNarrowShape();
		Collection<ContactPoint> output = new ArrayList<>();
		
		MutableVec2 normal = new MutableVec2(line.getDirection());
		normal.tangent();
		normal.scale(-1);
		
		//check the distance from the centre of the circle to the "plane" of the line"
		double distance =  normal.dot(circle.getPosition()) - normal.dot(line.getPosition());
		
		//check if the line is where the circle should intersect.
		MutableVec2 position = new MutableVec2(normal);
		position.scale(distance);
		position.add(circle.getPosition());
		MutableVec2 difference = new MutableVec2(position);
		difference.subtract(line.getPosition());
		
		double scale;
		if(line.getDirection().x() != 0){
			scale = difference.x() / line.getDirection().x();
		} else{
			scale = difference.y() / line.getDirection().y();
		}
		
		if(distance < circle.getRadius() && line.getLength() > scale){
			double penetration = circle.getRadius() - distance;
			
			MutableVec2 circleToLine = new MutableVec2(normal);
			circleToLine.scale(-distance - penetration/2);
			circleToLine.add(circle.getPosition());
			output.add(new ContactPoint(penetration, circleToLine, normal, A, B));
		}

		return output;
		
	}

	private Collection<ContactPoint> lineLineCollision(RigidBody A, RigidBody B){
		LineShape firstLine = (LineShape) A.getNarrowShape();
		LineShape secondLine = (LineShape) B.getNarrowShape();
		Collection<ContactPoint> output = new ArrayList<>();

		MutableVec2 distance = new MutableVec2(firstLine.getPosition());
		distance.subtract(secondLine.getPosition());
		
		double x1 = firstLine.getDirection().x();
		double x2 = secondLine.getDirection().x();
		double y1 = firstLine.getDirection().y();
		double y2 = secondLine.getDirection().y();
		double dx = distance.x();
		double dy = distance.y();

		/*if the two lines have an intersect that is not parallel*/
		if(1 - Math.abs(firstLine.getDirection().dot(secondLine.getDirection())) > 0.0001){
			/*checks where the theoretical intersection point would be*/
			double secondScale = (dy * x1 - dx * y1) / (x2 * y1 - x1 * y2);

			double firstScale = (dy * x2 - dx * y2) / (x1 * y2 - x2 * y1);

			/*checks if that point of intersection is beyond the size of the lines*/
			if(Math.abs(firstScale) <= firstLine.getLength() && firstLine.getLength() - Math.abs(firstScale) <= secondLine.getLength() - Math.abs(secondScale)){
				MutableVec2 normal = new MutableVec2(secondLine.getDirection());
				normal.tangent();
				double penetration = firstLine.getLength()- Math.abs(firstScale);
				MutableVec2 position = new MutableVec2(firstLine.getDirection());
				position.scale(Math.abs(firstScale));
				position.add(firstLine.getPosition());
				output.add(new ContactPoint(penetration, position, normal, A, B));
			} 
			if(Math.abs(firstScale) > firstLine.getLength() && Math.abs(secondScale) <= secondLine.getLength() && firstLine.getLength() - Math.abs(firstScale) >= secondLine.getLength() - Math.abs(secondScale)){
				MutableVec2 normal = new MutableVec2(firstLine.getDirection());
				normal.tangent();
				normal.scale(-1);
				double penetration = secondLine.getLength() - Math.abs(secondScale);
				MutableVec2 position = new MutableVec2(secondLine.getDirection());
				position.scale(Math.abs(secondScale));
				position.add(secondLine.getPosition());
				output.add(new ContactPoint(penetration, position, normal, A, B));
			}
		} 
		/*for parallel lines*/
		double xScale = firstLine.getPosition().x() / secondLine.getPosition().x();
		double yScale = firstLine.getPosition().y() / secondLine.getPosition().y();
		
		if(xScale - yScale <= 0.0001){
			
		}
		
		return output;
	}
}
