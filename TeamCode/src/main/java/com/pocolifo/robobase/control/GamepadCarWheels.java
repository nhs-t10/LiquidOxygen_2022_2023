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

	public void update(float v, float h, float r) {
		// Drive the wheels to match the controller input
		float[] vals = new float[] {v - h - r, v + r + h, v - r + h, v + r - h};
		boolean norm = false;
		float tonorm = 0;

		for (int i = 0; i < 4; i++) {
			if ((vals[i] > 1 && vals[i] > tonorm) || (vals[i] < -1 && vals[i] < -tonorm)) {
				norm = true;
				tonorm = Math.abs(vals[i]);
			}
		}

		if (norm) {
			for (int i = 0; i < 4; i++) {
				vals[i] /= tonorm;
			}
		}

		this.wheels.driveIndividually(-vals[0], vals[1], -vals[2], vals[3]);

		// Omni-drive
	}

	public void close() {
		this.wheels.close();
	}
}
