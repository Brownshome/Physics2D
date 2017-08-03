package physics2d.maths;

/** Represents a mutable Vec2 */
public class MutableVec2 implements Vec2 {
	private double x, y;
	
	public MutableVec2(Vec2 copy) {
		this(copy.x(), copy.y());
	}
	
	public MutableVec2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public MutableVec2() {
		this(0, 0);
	}
	
	public void set(Vec2 v) {
		set(v.x(), v.y());
	}
	
	public void set(double x, double y) {
		x(x);
		y(y);
	}
	
	@Override
	public double x() {
		return x;
	}
	
	@Override
	public double y() {
		return y;
	}
	
	public void x(double x) {
		this.x = x;
	}
	
	public void y(double y) {
		this.y = y;
	}
	
	public void add(Vec2 vec) {
		add(vec.x(), vec.y());
	}

	public void subtract(Vec2 vec) {
		add(-vec.x(), -vec.y());
	}

	public void scale(double scale) {
		set(x() * scale, y() * scale);
	}

	/**
	 * Sets this vector to have a length of one. This will set the vector to NaN if
	 * the length is zero.
	 */
	public void normalize() {
		double scale = 1 / length();
		scale(scale);
	}

	/**
	 * Adds an amount to this vector
	 * @param x The amount to the x value
	 * @param y The amount to add to the y value
	 */
	public void add(double x, double y) {
		set(x() + x, y() + y);
	}
	
	/**
	 * Sets this vector to be the tangent of the given vector. This function rotates the vector counterclockwise in the topleft coordiante
	 * system.
	 */
	public void tangent(Vec2 v) {
		set(v.y(), -v.x());
	}
	
	@Override
	public String toString() {
		return String.format("(%.3f, %.3f)", x(), y());
	}

	/**
	 * Sets this to be this + vec * scale
	 * @param vec The vector to add
	 * @param scale The number to scale it by
	 */
	public void scaleAdd(Vec2 vec, double scale) {
		set(x() + vec.x() * scale, y() + vec.y() * scale);
	}

	/** Sets this vector to be the tangent of itself */
	public void tangent() {
		tangent(this);
	}

	public void negate() {
		set(-x(), -y());
	}
	
	/** Gets an angle between this vector and the given vector in radians*/
	public double angle(Vec2 vec) {
		double angle = this.dot(vec) / (this.length() * vec.length());
		angle = Math.acos(angle);
		return angle;
	}
}
