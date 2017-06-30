package physics2d.maths;

public class Rotation extends Vec2 {
	private double angle;
	
	public Rotation(Rotation copy) {
		super(copy.x(), copy.y());
	}
	
	public Rotation(double angle) {
		super(Math.sin(angle), Math.cos(angle));
	}
	
	/** The identity rotation */
	public Rotation() {
		super(1, 0);
	}

	public Vec2 rotate(Vec2 v) {
		double x = v.x() * x() - v.y() * y();
		double y = v.x() * y() + v.y() * x();
		
		v.x(x);
		v.y(y);
		
		return v;
	}
	
}
