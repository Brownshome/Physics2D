package physics2d.maths;

public class FinalRotation implements Rotation {
	private final double sin, cos;
	
	public FinalRotation(double x, double y) {
		sin = y;
		cos = x;
	}
	
	public FinalRotation(double angle) {
		this(Math.cos(angle), Math.sin(angle));
	}
	
	public FinalRotation() {
		this(1, 0);
	}
	
	public FinalRotation(Rotation r) {
		this(r.x(), r.y());
	}
	
	@Override
	public double sin() {
		return sin;
	}

	@Override
	public double cos() {
		return cos;
	}

	@Override
	public String toString() {
		return String.format("(%.3f, %.3f)", x(), y());
	}
}
