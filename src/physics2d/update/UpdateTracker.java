package physics2d.update;

public interface UpdateTracker {
	/** An update tracker that never trigers an update */
	public static final UpdateTracker NULL_UPDATE_TRACKER = new UpdateTracker() {
		@Override public boolean needsUpdate() { return false; }
		@Override public void setUpToDate() {}
	};
	
	/** Returns true if this object needs to be updated */
	boolean needsUpdate();
	/** Signals that this object is up to date */
	void setUpToDate();
}
