package physics2d.maths;

public class MutableRotation extends MutableVec2 implements Rotation {
	public MutableRotation(Rotation copy) {
		super(copy);
	}
	
	public MutableRotation(double angle) {
		super(Math.cos(angle), -Math.sin(angle));
	}
	
	public MutableRotation(double x, double y) {
		super(x, y);
	}
	
	/** The identity rotation */
	public MutableRotation() {
		this(1, 0);
	}

	@Override
	public double sin() {
		return y();
	}

	@Override
	public double cos() {
		return x();
	}
	
	public void invert() {
		sin(-sin());
	}
	
	public void sin(double sin) {
		y(sin);
	}
	
	public void cos(double cos) {
		x(cos);
	}
	
	@Override
	public String toString() {
		return String.format("(%.3f, %.3f)", x(), y());
	}
}
