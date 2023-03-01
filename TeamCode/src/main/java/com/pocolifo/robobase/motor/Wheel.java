package com.pocolifo.robobase.motor;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Represents a single wheel on the robot.
 *
 * @see CarWheels
 * @author youngermax
 */
public class Wheel extends Motor {
	/**
	 * The diameter of this wheel, in centimeters.
	 */
	public final double wheelDiameterCm;

	/**
	 * The circumference of this wheel, in centimeters.
	 */
	public final double circumferenceCm;

	/**
	 * The target encoder position
	 */
	public int targetPosition;

	/**
	 * Instantiate a {@link Wheel}.
	 *
	 * @param motor The {@link DcMotor} that is associated with this {@link Motor}.
	 * @param tickCount The number of ticks for a full revolution of this motor.
	 * @param wheelDiameterCm The diameter of the wheel <strong>in real life</strong>, in centimeters.
	 */
	public Wheel(DcMotor motor, int tickCount, double wheelDiameterCm) {
		super(motor, tickCount);

		this.wheelDiameterCm = wheelDiameterCm;
		this.circumferenceCm = Math.PI * wheelDiameterCm;
	}

	/**
	 * Sets how far the motor should move (in centimeters).
	 *
	 * @param centimeters How far the motor should move, in centimeters. Negative values move the wheel in reverse, and
	 *                    positive values move the wheel forward.
	 * @author youngermax
	 */
	public void setDriveTarget(double centimeters) {
		double rotations = centimeters / this.circumferenceCm;
		this.targetPosition = (int) rotations * this.tickCount + this.motor.getCurrentPosition();

		this.motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

	}
}
