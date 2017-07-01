package physics2d.maths;

/** An interface representing a 2D vector. This can be used as a type representing a Vec2 that
 * will not be edited */
public interface Vec2 {
	double x();
	double y();
	
	/**
	 * Calculates the distance squared from a this vector to another one
	 * @param position The position to calculate the distance to to
	 */
	default double distanceSq(Vec2 position) {
		double dx = position.x() - x();
		double dy = position.y() - y();
		return dx * dx + dy * dy;
	}
	
	/**
	 * Calculates the dot product between this and vec
	 */
	default double dot(Vec2 vec) {
		return vec.x() * x() + vec.y() * y();
	}
	
	/**
	 * Calculates the length of this vector
	 */
	default double length() {
		return Math.sqrt(lengthSq());
	}
	
	/**
	 * Calculates the length of this vector squared
	 */
	default double lengthSq() {
		return x() * x() + y() * y();
	}
	
	/**
	 * Calculates the distance from a this vector to another one
	 * @param position The position to calculate to
	 */
	default double distance(Vec2 position) {
		return Math.sqrt(position.distanceSq(this));
	}
}
