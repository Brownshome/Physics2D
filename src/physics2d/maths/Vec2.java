package physics2d.maths;

public class Vec2 {
	private double x, y;
	
	public Vec2(Vec2 copy) {
		this(copy.x(), copy.y());
	}
	
	public Vec2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vec2() {
		this(0, 0);
	}
	
	public void set(Vec2 v) {
		set(v.x(), v.y());
	}
	
	public void set(double x, double y) {
		x(x);
		y(y);
	}
	
	public double x() {
		return x;
	}
	
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
	 * Calculates the distance squared from a this vector to another one
	 * @param position The position to calculate the distance to to
	 */
	public double distanceSq(Vec2 vec) {
		double dx = x() - vec.x();
		double dy = y() - vec.y();
		return dx * dx + dy * dy;
	}

	/**
	 * Calculates this . vec
	 * @param vec The vector to calculate the dot product with
	 */
	public double dot(Vec2 vec) {
		return x() * vec.x() + y() * vec.y();
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
	 * Calculates the length of this vector
	 */
	public double length() {
		return Math.sqrt(lengthSq());
	}
	
	/**
	 * Calculates the length of this vector squared
	 */
	public double lengthSq() {
		return x() * x() + y() * y();
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
	 * Sets v to be the tangent of this vector
	 * @param v The vector to store the result in
	 */
	public void tangent(Vec2 v) {
		v.set(-y(), x());
	}
	
	@Override
	public String toString() {
		return String.format("(%.3f, %.3f)", x(), y());
	}

	/**
	 * Calculates the distance from a this vector to another one
	 * @param position The position to calculate to
	 */
	public double distance(Vec2 position) {
		return Math.sqrt(distanceSq(position));
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
}
