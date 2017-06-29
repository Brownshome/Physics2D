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
		x += vec.x();
		y += vec.y();
	}
}
