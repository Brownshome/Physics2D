package physics2d.update;

import java.util.*;

/** This class keeps track of multiple update sources */
public class MultipleUpdateTracker implements UpdateTracker {
	private Collection<UpdateTracker> trackers;
	private int counter = -1;
	
	public MultipleUpdateTracker(UpdateSource... sources) {
		UpdateTracker[] trackerArray = new UpdateTracker[sources.length];

		for(int i = 0; i < sources.length; i++) {
			trackerArray[i] = new SingleUpdateTracker(sources[i]);
		}
		
		trackers = Arrays.asList(trackerArray);
	}

	public MultipleUpdateTracker(Collection<UpdateTracker> trackers) {
		this.trackers = trackers;
	}
	
	public MultipleUpdateTracker(UpdateTracker... trackers) {
		this(Arrays.asList(trackers));
	}
	
	@Override
	public boolean needsUpdate() {
		for(UpdateTracker tracker : trackers) {
			if(tracker.needsUpdate())
				return true;
		}
		
		return false;
	}
	
	@Override
	public void setUpToDate() {
		for(UpdateTracker tracker : trackers) {
			tracker.setUpToDate();
		}
	}
}
