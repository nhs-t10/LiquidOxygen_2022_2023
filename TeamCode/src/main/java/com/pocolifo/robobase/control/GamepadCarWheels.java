package com.pocolifo.robobase.control;

import com.pocolifo.robobase.motor.CarWheels;
import com.qualcomm.robotcore.hardware.Gamepad;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GamepadCarWheels {
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
	 * Drive the robot with omni-drive. Omni-drive allows the robot to move horizontally without rotating, drive
	 * diagonally, and drive and rotate the robot normally.
	 *
	 * @param verticalPower The power at which to move the robot forward and backward. Inclusive from -1.0 to 1.0.
	 * @param horizontalPower The power at which to move the robot left and right. Inclusive from -1.0 to 1.0.
	 * @param rotationalPower The power at which to rotate the robot. Inclusive from -1.0 (rotate counterclockwise) to 1.0
	 *                        (clockwise).
	 */
	public void driveOmni(float verticalPower, float horizontalPower, float rotationalPower) {
		// Drive the wheels to match the controller input
		// TODO: Add explanation here -- even I don't know how this works
		float[] vals = new float[] {
			verticalPower - horizontalPower - rotationalPower,
			verticalPower + rotationalPower + horizontalPower,
			verticalPower - rotationalPower + horizontalPower,
			verticalPower + rotationalPower - horizontalPower
		};

		// Normalize values if needed
		float max = 0;

		for (float value : vals) {
			max = Math.max(Math.abs(value), max);
		}

		// Normalize only if the max value in the array has a greater distance from zero than 1
		if (max > 1) {
			for (int i = 0; vals.length > i; i++) {
				vals[i] /= max;
			}
		}

		this.wheels.driveIndividually(-vals[0], -vals[1], -vals[2], -vals[3]);
	}

	public void close() {
		this.wheels.close();
	}
}
