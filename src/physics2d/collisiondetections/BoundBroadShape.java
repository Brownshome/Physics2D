package physics2d.collisiondetections;

import java.util.ArrayList;

import physics2d.maths.*;
import physics2d.update.*;

/** A subclass of BroadShape that responds to an UpdateTracker */
public abstract class BoundBroadShape extends BroadShape {
	private final UpdateTracker tracker;
	
	public BoundBroadShape(UpdateTracker tracker) {
		super(new MutableVec2(), 0, 0);
		
		this.tracker = tracker;
	}

	@Override
	protected MutableVec2 getPosition() {
		if(tracker.needsUpdate()) {
			tracker.setUpToDate(); //Tracker is updated first to avoid recursion
			update();
		}
		
		return (MutableVec2) super.getPosition();
	}

	protected abstract void update();
}
