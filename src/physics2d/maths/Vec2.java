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

	public double distanceSq(Vec2 vec) {
		double dx = x() - vec.x();
		double dy = y() - vec.y();
		return dx * dx + dy * dy;
	}

	public double dot(Vec2 vec) {
		return x() * vec.x() + y() * vec.y();
	}
	
	public void normalize() {
		double scale = 1 / length();
		scale(scale);
	}
	
	public double length() {
		return Math.sqrt(lengthSq());
	}
	
	public double lengthSq() {
		return x() * x() + y() * y();
	}

	public void add(double x, double y) {
		set(x() + x, y() + y);
	}
	
	@Override
	public String toString() {
		return String.format("(%.3f, %.3f)", x(), y());
	}

	public double distance(Vec2 position) {
		return Math.sqrt(distanceSq(position));
	}

	public void scaleAdd(Vec2 vec, double scale) {
		set(x() + vec.x() * scale, y() + vec.y() * scale);
	}
}
