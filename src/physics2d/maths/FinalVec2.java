package physics2d.maths;

public class FinalVec2 implements Vec2 {
	public static final Vec2 ZERO = new FinalVec2(0, 0);
	
	private final double x, y;
	
	public FinalVec2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public FinalVec2(Vec2 copy) {
		this.x = copy.x();
		this.y = copy.y();
	}
	
	@Override
	public double x() {
		return x;
	}

	@Override
	public double y() {
		return y;
	}
	
	@Override
	public String toString() {
		return String.format("(%.3f, %.3f)", x(), y());
	}
}
