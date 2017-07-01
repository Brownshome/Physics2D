package physics2d.maths;

public class FinalVec2 implements Vec2 {
	private final double x, y;
	
	public FinalVec2() {
		x = 0;
		y = 0;
	}
	
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

}
