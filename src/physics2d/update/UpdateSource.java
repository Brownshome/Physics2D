package physics2d.update;

public class UpdateSource {
	private int counter = 0;
	
	/**
	 * Call this after the internal state has been updated
	 */
	public void update() {
		counter++;
	}
	
	int getState() {
		return counter;
	}
}
