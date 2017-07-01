package physics2d.maths;

import java.util.function.*;

public class BoundVec2 implements Vec2 {
	private final Vec2 parent;
	private final ToDoubleFunction<Vec2> xFunc;
	private final ToDoubleFunction<Vec2> yFunc;
	
	public BoundVec2(Vec2 parent, ToDoubleFunction<Vec2> xFunc, ToDoubleFunction<Vec2> yFunc) {
		this.parent = parent;
		this.xFunc = xFunc;
		this.yFunc = yFunc;
	}

	@Override
	public double x() {
		return xFunc.applyAsDouble(parent);
	}

	@Override
	public double y() {
		return yFunc.applyAsDouble(parent);
	}
}
