package com.pocolifo.robobase.motor;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Represents a motor.
 * This class is needed because {@link DcMotor} misses some metrics, like the tick count of the motor.
 * The tick count is necessary to use motor encoders (i.e. moving the wheels exactly 1 meter forward).
 *
 * @author youngermax
 */
public class Motor implements AutoCloseable {
	/**
	 * The {@link DcMotor} associated with this motor.
	 */
	public final DcMotor motor;

	/**
	 * The amount of motor ticks that make one full revolution of the motor. <strong> It's different for every motor,
	 * you may need to search up what the tick count is for your motor.</strong>
	 */
	public final int tickCount;

	/**
	 * Instantiate a {@link Motor}.
	 *
	 * @param motor The {@link DcMotor} that is associated with this {@link Motor}.
	 * @param tickCount The number of ticks for a full revolution of this motor.
	 */
	public Motor(DcMotor motor, int tickCount) {
		this.motor = motor;
		this.tickCount = tickCount;

		this.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
		this.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		this.motor.setDirection(DcMotorSimple.Direction.FORWARD);
	}

	/**
	 * Stop the movement of the motor.
	 * This may be removed in a future release if {@link DcMotor#setPower(double)} has the same effect with encoders.
	 *
	 * @deprecated
	 * @author youngermax
	 */
	@Deprecated
	public void stopMoving() {
		this.motor.setPower(0);
		this.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // TODO: test if this is necessary
	}

	/**
	 * Closes the internal {@link DcMotor} device. <strong>THIS SHOULD BE CALLED WHEN MOTORS ARE DONE BEING
	 * USED!</strong>
	 *
	 * @author youngermax
	 */
	@Override
	public void close() {
		this.motor.close();
	}
}
