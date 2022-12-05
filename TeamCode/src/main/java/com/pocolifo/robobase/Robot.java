package com.pocolifo.robobase;

import lombok.RequiredArgsConstructor;

/**
 * Data and other information about the robot. This should be kept as accurate and up-to-date as possible!
 *
 * @author youngermax
 */
@RequiredArgsConstructor
public class Robot {
	/**
	 * The width of the robot in real life, in centimeters.
	 */
	public final double widthCm;

	/**
	 * The length of the robot in real life, in centimeters.
	 */
	public final double lengthCm;

	/**
	 * The height of the robot in real life, in centimeters.
	 */
	public final double heightCm;

	/**
	 * The number of the team who built the robot.
	 */
	public final int teamNumber;

	/**
	 * The name of the robot.
	 */
	public final String robotName;

	/**
	 * If the robot has the "Warning: Robot Moves on Initialization" sticker on it.
	 */
	public final boolean hasWarningSticker;

	// TODO: Add more metrics about the robot in real life.

	/**
	 * Determine if the robot meets dimension requirements.
	 *
	 * @return True if the robot meets the dimension requirements, false if it doesn't.
	 * @author youngermax
	 */
	public boolean doesPassDimensionInspection() {
		// 45.72 is equivalent to 18 inches, the requirements for a robot
		return 45.72 >= widthCm && 45.72 >= lengthCm && 45.72 >= heightCm;
	}
}
