package physics2d.maths;

public class Rotation extends Vec2 {
	private double angle;
	
	public Rotation(Rotation copy) {
		super(copy.x(), copy.y());
	}
	
	public Rotation(double angle) {
		super(Math.sin(angle), Math.cos(angle));
	}
	
	public Vec2 rotate(Vec2 v) {
		double x = v.x() * x() - v.y() * y();
		double y = v.x() * y() + v.y() * x();
		
		v.x(x);
		v.y(y);
		
		return v;
	}
	
	public void tangent(Vec2 v) {
		v.set(-y(), x());
	}
	
	public Vec2 tangent() {
		Vec2 v = new Vec2();
		tangent(v);
		return v;
	}
}
