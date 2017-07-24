package physics2d.maths;

public class FinalRotation extends FinalVec2 implements Rotation {
	public FinalRotation(double x, double y) {
		super(x, y);
	}
	
	public FinalRotation(double angle) {
		this(Math.cos(angle), -Math.sin(angle));
	}
	
	public FinalRotation() {
		this(1, 0);
	}
	
	public FinalRotation(Rotation r) {
		this(r.x(), r.y());
	}
	
	@Override
	public double sin() {
		return y();
	}

	@Override
	public double cos() {
		return x();
	}

	@Override
	public String toString() {
		return String.format("(%.3f, %.3f)", x(), y());
	}
}
