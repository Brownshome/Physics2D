package physics2d.maths;

public class MutableRotation implements Rotation {
	private double sin, cos;
	
	public MutableRotation(Rotation copy) {
		this(copy.sin(), copy.cos());
	}
	
	public MutableRotation(double angle) {
		this(Math.cos(angle), Math.sin(angle));
	}
	
	public MutableRotation(double x, double y) {
		this.sin = y;
		this.cos = x;
	}
	
	/** The identity rotation */
	public MutableRotation() {
		this(1, 0);
	}

	@Override
	public double sin() {
		return sin;
	}

	@Override
	public double cos() {
		return cos;
	}
	
	public void sin(double sin) {
		this.sin = sin;
	}
	
	public void cos(double cos) {
		this.cos = cos;
	}
	
	public void normalize() {
		double r = 1.0 / length();
		sin(sin() * r);
		cos(cos() * r);
	}
	
	@Override
	public String toString() {
		return String.format("(%.3f, %.3f)", x(), y());
	}
}
