package com.pocolifo.robobase.control;

import com.pocolifo.robobase.motor.CarWheels;
import com.qualcomm.robotcore.hardware.Gamepad;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GamepadCarWheels {
	private final CarWheels wheels;
	private final Gamepad gamepad;

	/**
	 * Calculate the power to set the right set of wheel motors
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
	 * Calculate the power to set the left set of wheel motors
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

	public void update() {
		// Drive the wheels to match the controller input
		this.wheels.driveIndividually(
			-calculateLeftWheelPower(this.gamepad.left_stick_x, this.gamepad.left_stick_y),
			-calculateRightWheelPower(this.gamepad.left_stick_x, this.gamepad.left_stick_y),
			-calculateLeftWheelPower(this.gamepad.left_stick_x, this.gamepad.left_stick_y),
			-calculateRightWheelPower(this.gamepad.left_stick_x, this.gamepad.left_stick_y)
		);

		// Omni-drive
	}
}
