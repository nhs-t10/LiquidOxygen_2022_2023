package com.pocolifo.robobase.control;

import com.pocolifo.robobase.motor.CarWheels;
import com.qualcomm.robotcore.hardware.Gamepad;
import lombok.RequiredArgsConstructor;

/**
 * Utility class which implements Omni-Drive.
 */
@RequiredArgsConstructor
public class GamepadCarWheels implements AutoCloseable {
	private final CarWheels wheels;
	private final Gamepad gamepad;

	/**
	 * Calculate the power to set the right set of wheel motors.
	 *
	 * @param x The joystick's x value
	 * @param y The joystick's y value
	 * @return The motor power for the right set of wheels
	 * @author Eli
	 */
	private double calculateRightWheelPower(double x, double y) {
		if (x == 0 && y == 0) {
			return 0;
		}

		double r = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		double t = Math.toDegrees(Math.asin(y / r));

		if (x >= 0 && y >= 0) {
			return r;
		} else if (x <= 0 && y <= 0) {
			return -r;
		} else if (x < 0) {
			return r * (t - 45) / 45;
		} else {
			return r * (t + 45) / 45;
		}
	}

	/**
	 * Calculate the power to set the left set of wheel motors.
	 *
	 * @param x The joystick's x value
	 * @param y The joystick's y value
	 * @return The motor power for the left set of wheels
	 * @author Eli
	 */
	private double calculateLeftWheelPower(double x, double y) {
		if (x == 0 && y == 0) {
			return 0;
		}

		double r = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		double t = Math.toDegrees(Math.asin(y / r));

		if (x <= 0 && y >= 0) {
			return r;
		} else if (x >= 0 && y <= 0) {
			return -r;
		} else if (x > 0) {
			return r * (t - 45) / 45;
		} else {
			return r * (t + 45) / 45;
		}
	}

	/**
	 * <p>Must be called every loop iteration to update the movement of the wheels.
	 * Movement is based upon gamepad input.</p>
	 *
	 * <p><strong>Controls</strong>
	 * Left stick - movement: forward, backward, left and right without turning
	 * Right tick - rotation: clockwise and counterclockwise</p>
	 */
	public void update() {
		this.wheels.driveOmni(this.gamepad.left_stick_y, this.gamepad.left_stick_x, this.gamepad.right_stick_x);
	}

	/**
	 * Cleans up this {@link GamepadCarWheels} instance. <strong>This should be called when this instance is no longer
	 * in use!</strong>
	 */
	public void close() {
		this.wheels.close();
	}
}
