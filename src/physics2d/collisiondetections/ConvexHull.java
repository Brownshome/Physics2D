package physics2d.collisiondetections;

import java.util.ArrayList;

import physics2d.body.RigidBody;
import physics2d.maths.MutableVec2;
import physics2d.maths.Vec2;

public abstract class ConvexHull extends NarrowShape{
	private ArrayList<MutableVec2> _edges;
	private ArrayList<MutableVec2> _vertices;
	private ArrayList<MutableVec2> _normals;
	
	public ConvexHull(Vec2 position, ArrayList<MutableVec2> vertices) {
		super(position, 3);
		_vertices = vertices;
		for(int i = 0; i < vertices.size() - 1; i++){
			MutableVec2 A = new MutableVec2(vertices.get(i));
			MutableVec2 B = new MutableVec2(vertices.get(i + 1));
			
			A.subtract(B);
			_edges.add(A);
			A.normalize();
			A.tangent();
			_normals.add(A);
		}
		MutableVec2 C = new MutableVec2(vertices.get(vertices.size()));
		MutableVec2 D = new MutableVec2(vertices.get(0));
		
		C.subtract(D);
		_edges.add(C);
		C.normalize();
		C.tangent();
		_normals.add(C);
		
	}
	
	public abstract ConvexHull instantiateConvexHull(Vec2 position, ArrayList<MutableVec2> vertices);
	
	public ArrayList<MutableVec2> getEdges(){
		return _edges;
	}
	
	public ArrayList<MutableVec2> getVertices(){
		return _vertices;
	}
	
	public ArrayList<MutableVec2> getNormals(){
		return _normals;
	}

	@Override
	public BroadShape createBoundBroadShape() {
		// TODO Auto-generated method stub
		return null;
	}

	public BroadShape createBoundNarrowShape() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
