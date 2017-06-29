package physics2d.maths;

public class Rotation extends Vec2 {
	private double angle;
	
	public Rotation(Rotation copy) {
		super(copy.x(), copy.y());
	}
	
	public Rotation(double angle) {
		super(Math.sin(angle), Math.cos(angle));
	}
	
	public void rotate(Vec2 v) {
		assert false : "TODO";
	}
}
