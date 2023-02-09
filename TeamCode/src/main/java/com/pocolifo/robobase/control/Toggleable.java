package com.pocolifo.robobase.control;


public class Toggleable {
	private final BoolSupplier currentState;
	private boolean lastState;
	private boolean thisToggledState;

	/**
	 * Creates a {@link Toggleable}.
	 *
	 * @param currentState A method to get the current state of something (like whether a gamepad button is pressed)
	 */
	public Toggleable(BoolSupplier currentState) {
		this.currentState = currentState;
	}

	/**
	 * Get the current toggled state.
	 *
	 * @return The toggled state of this {@link Toggleable}
	 */
	public boolean get() {
		boolean current = this.currentState.get();

		if (current && !this.lastState) {
			this.thisToggledState = !this.thisToggledState;
		}

		this.lastState = current;

		return this.thisToggledState;
	}
}
