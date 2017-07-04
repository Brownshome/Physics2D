package physics2d.update;

public interface UpdateTracker {
	/** Returns true if this object needs to be updated */
	boolean needsUpdate();
	/** Signals that this object is up to date */
	void setUpToDate();
}
