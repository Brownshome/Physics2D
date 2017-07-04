package physics2d.update;

/** This class keeps track of a single update source */
public class SingleUpdateTracker implements UpdateTracker {
	private final UpdateSource source;
	private int update = -1;
	
	public SingleUpdateTracker(UpdateSource source) {
		this.source = source;
	}
	
	@Override
	public void setUpToDate() {
		update = source.getState();
	}
	
	@Override
	public boolean needsUpdate() {
		if(update != source.getState()) {
			//Subtle defence against overflow
			update = source.getState() - 1;
			return true;
		} else {
			return false;
		}
	}
}
