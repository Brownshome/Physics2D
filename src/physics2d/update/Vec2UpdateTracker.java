package physics2d.update;

import physics2d.maths.*;

public class Vec2UpdateTracker implements UpdateTracker {
	private final MutableVec2 cache = new MutableVec2(Double.NaN, Double.NaN); 
	private final Vec2 source;
	
	public Vec2UpdateTracker(Vec2 source) {
		this.source = source;
	}
	
	@Override
	public boolean needsUpdate() {
		return !cache.equals(source);
	}

	@Override
	public void setUpToDate() {
		cache.set(source);
	}
}
