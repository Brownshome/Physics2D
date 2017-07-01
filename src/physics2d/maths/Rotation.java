package physics2d.maths;

public class Rotation extends FinalVec2 {
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

	public void rotate(MutableVec2 v) {
		double x = v.x() * x() - v.y() * y();
		double y = v.x() * y() + v.y() * x();
		
		v.set(x, y);
	}
	
}
