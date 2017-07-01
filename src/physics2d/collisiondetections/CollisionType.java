package physics2d.collisiondetections;

import physics2d.body.RigidBody;

public class CollisionType {
	private final int idA;
	private final int idB;
	
	public CollisionType(RigidBody a, RigidBody b){
		idA = a.getNarrowShape().getID();
		idB = b.getNarrowShape().getID();
	}
	
	public CollisionType(int A, int B){
		idA = A;
		idB = B;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idA;
		result = prime * result + idB;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof CollisionType){
			CollisionType a = (CollisionType) obj;
			return (idA == a.idA && idB == a.idB);
		}
		
		return false;
	}
	
	
}
