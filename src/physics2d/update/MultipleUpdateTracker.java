package physics2d.update;

import java.util.Collection;

/** This class keeps track of multiple update sources */
public class MultipleUpdateTracker implements UpdateTracker {
	private Collection<UpdateSource> sources;
	private int counter = -1;
	
	public MultipleUpdateTracker(Collection<UpdateSource> sources) {
		this.sources = sources;
	}

	@Override
	public boolean needsUpdate() {
		int sum = sum();
		
		if(sum != counter) {
			counter = sum - 1;
			return true;
		} else {
			return false;
		}
	}

	private int sum() {
		int sum = 0;
		
		for(UpdateSource source : sources) {
			sum += source.getState();
		}
		
		return sum;
	}
	
	@Override
	public void setUpToDate() {
		counter = sum();
	}
}
