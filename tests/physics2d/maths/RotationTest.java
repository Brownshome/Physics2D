package physics2d.maths;

import static org.junit.Assert.*;

import org.junit.Test;

public class RotationTest {
	@Test
	public void testRotateIdentity() {
		MutableVec2 x = new MutableVec2(1, 0);
		FinalRotation id = new FinalRotation();
		id.rotate(x);
		assertTrue(fuzzyEqual(x, new FinalVec2(1, 0)));
	}
	
	@Test
	public void testRotate() {
		MutableVec2 x = new MutableVec2(1, 0);
		FinalRotation theta = new FinalRotation(-Math.PI * 0.5);
		theta.rotate(x);
		assertTrue(fuzzyEqual(x, new FinalVec2(0, -1)));
	}

	private boolean fuzzyEqual(Vec2 a, Vec2 b) {
		double dif = Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
		return dif < 1e-10;
	}

	@Test
	public void testAngleToSelf() {
		assertTrue(Math.abs(new FinalRotation().angleTo(new FinalRotation())) < 1e-10);
	}
	
	@Test
	public void testAngleTo() {
		assertTrue(Math.abs(new FinalRotation(Math.PI * 0.3).angleTo(new FinalRotation(Math.PI * 0.7)) - Math.PI * 0.4) < 1e-10);
	}
}
