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
		x(x() + vec.x());
		y(y() + vec.y());
	}

	public void subtract(Vec2 vec) {
		x(x() - vec.x());
		y(y() - vec.y());
	}

	public void scale(double scale) {
		x(x() * scale);
		y(y() * scale);
	}

	public double distanceSquared(Vec2 vec) {
		double dx = x() - vec.x();
		double dy = y() - vec.y();
		return dx * dx + dy * dy;
	}

	public double dot(Vec2 vec) {
		return x() * vec.x() + y() * vec.y();
	}
}
