package com.pocolifo.robobase.motor;

import com.pocolifo.robobase.Robot;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Contains functions to move the robot very intuitively. Intended for use in Autonomous. The wheels must be organized
 * just like a car; this means all wheels are parallel, two on each side of the robot.
 *
 * @see Wheel
 * @author youngermax
 */
public class CarWheels implements AutoCloseable {
	/**
	 * The {@link Robot} that is associated with these {@link CarWheels}.
	 */
	public final Robot robot;

	/**
	 * The <strong>front left</strong> {@link Wheel}, if looking from the back of the robot and in the same direction
	 * of the robot.
	 */
	public final Wheel frontLeft;

	/**
	 * The <strong>front right</strong> {@link Wheel}, if looking from the back of the robot and in the same direction
	 * of the robot.
	 */
	public final Wheel frontRight;

	/**
	 * The <strong>back left</strong> {@link Wheel}, if looking from the back of the robot and in the same direction of
	 * the robot.
	 */
	public final Wheel backLeft;

	/**
	 * The <strong>back right</strong> {@link Wheel}, if looking from the back of the robot and in the same direction of
	 * the robot.
	 */
	public final Wheel backRight;

	/**
	 * The distance (in centimeters) to move the wheels to rotate one degree. This value is calculated upon
	 * instantiation of {@link CarWheels}, and it is not recalculated after that.
	 *
	 * @see CarWheels#rotate(double, double)
	 */
	private final double oneDegreeRotDistCm;

	/**
	 * Instantiate {@link CarWheels}.
	 *
	 * @param robot The associated {@link Robot} that has these {@link CarWheels}.
	 * @param frontLeft The {@link Wheel} associated with the front left motor, if looking from the back of the robot
	 *                  and in the same direction of the robot.
	 * @param frontRight The {@link Wheel} associated with the front right motor, if looking from the back of the robot
	 *                   and in the same direction of the robot.
	 * @param backLeft The {@link Wheel} associated with the back left motor, if looking from the back of the robot
	 *                 and in the same direction of the robot.
	 * @param backRight The {@link Wheel} associated with the back right motor, if looking from the back of the robot
	 *                  and in the same direction of the robot.
	 * @see CarWheels#CarWheels(HardwareMap, int, double, Robot, String, String, String, String)
	 * @author youngermax
	 */
	public CarWheels(Robot robot, Wheel frontLeft, Wheel frontRight, Wheel backLeft, Wheel backRight) {
		this.robot = robot;
		this.frontLeft = frontLeft;
		this.frontRight = frontRight;
		this.backLeft = backLeft;
		this.backRight = backRight;

		this.oneDegreeRotDistCm = (Math.PI * Math.sqrt(Math.pow(this.robot.lengthCm, 2)
								+ Math.pow(this.robot.widthCm, 2))) / 360;
	}

	/**
	 * Quickly instantiate {@link CarWheels}.
	 *
	 * @param hardwareMap The {@link HardwareMap} which contains the motors.
	 * @param motorTickCount The number of encoder ticks for a revolution <strong>for each motor</strong>.
	 * @param wheelDiameterCm The diameter of <strong>each wheel</strong> in centimeters.
	 * @param robot The {@link Robot} that is associated with the {@code hardwareMap}.
	 * @param frontLeft The name of the front left motor, if looking from the back of the robot and in the same
	 *                  direction of the robot.
	 * @param frontRight The name of the front right motor, if looking from the back of the robot and in the same
	 *                   direction of the robot.
	 * @param backLeft The name of the back left motor, if looking from the back of the robot and in the same direction
	 *                 of the robot.
	 * @param backRight The name of the back right motor, if looking from the back of the robot and in the same
	 *                  direction of the robot.
	 * @see CarWheels#CarWheels(Robot, Wheel, Wheel, Wheel, Wheel)
	 * @author youngermax
	 */
	public CarWheels(HardwareMap hardwareMap, int motorTickCount, double wheelDiameterCm, Robot robot, String frontLeft,
					 String frontRight, String backLeft, String backRight) {
		this(
				robot,
				new Wheel(hardwareMap.dcMotor.get(frontLeft), motorTickCount, wheelDiameterCm),
				new Wheel(hardwareMap.dcMotor.get(frontRight), motorTickCount, wheelDiameterCm),
				new Wheel(hardwareMap.dcMotor.get(backLeft), motorTickCount, wheelDiameterCm),
				new Wheel(hardwareMap.dcMotor.get(backRight), motorTickCount, wheelDiameterCm)
		);
	}

	/**
	 * Drive the motors forward or backward.
	 *
	 * @param centimeters The number of centimeters to move. This should be negative to move backwards, and positive to
	 *                    move forwards. If {@code power} is negative, this should be too.
	 * @param power The power to drive the motors at. Valid values are from {@code -1.0} to {@code 1.0}, inclusive.
	 *              This should be negative to move backwards, and positive to move forwards. If {@code centimeters} is
	 *              negative, this should be too.
	 * @author youngermax
	 */
	public void drive(double centimeters, double power) {
		assert (centimeters > 0 && power > 0) || (0 > centimeters && 0 > power);

		this.setDriveTargetIndividually(centimeters, centimeters, centimeters, centimeters);
		this.driveIndividually(power, power, power, power);
		this.waitForWheelsThenStop();
	}

	/**
	 * Sets the motor speed of each wheel individually. Setting the motor speed actually moves it in real life.
	 *
	 * @param frontLeft The speed to set the front left wheel to.
	 * @param frontRight The speed to set the front right wheel to.
	 * @param backLeft The speed to set the back left wheel to.
	 * @param backRight The speed to set the back right wheel to.
	 * @author youngermax
	 */
	public void driveIndividually(double frontLeft, double frontRight, double backLeft, double backRight) {
		this.frontLeft.drive(frontLeft);
		this.frontRight.drive(-frontRight);
		this.backLeft.drive(-backLeft);
		this.backRight.drive(-backRight);
	}

	/**
	 * Sets the motor target of each wheel individually.
	 *
	 * @param frontLeftCm The target to set the front left wheel to, in centimeters.
	 * @param frontRightCm The target to set the front right wheel to, in centimeters.
	 * @param backLeftCm The target to set the back left wheel to, in centimeters.
	 * @param backRightCm The target to set the back right wheel to, in centimeters.
	 * @author youngermax
	 */
	public void setDriveTargetIndividually(double frontLeftCm, double frontRightCm, double backLeftCm,
										   double backRightCm) {
		this.frontLeft.setDriveTarget(frontLeftCm);
		this.frontRight.setDriveTarget(frontRightCm);
		this.backLeft.setDriveTarget(backLeftCm);
		this.backRight.setDriveTarget(backRightCm);
	}

	/**
	 * Rotates the robot.
	 *
	 * @param degrees Degrees to rotate. Positive for right (clockwise). Negative for left (counterclockwise).
	 * @param power The power to drive the motors at. Valid values are from {@code -1.0} to {@code 1.0}, inclusive.
  	 *              This should be negative to rotate counterclockwise, and positive to rotate clockwise.
	 * @author youngermax
	 */
	public void rotate(double degrees, double power) {
		double rotDistCm = this.oneDegreeRotDistCm * degrees;

		this.setDriveTargetIndividually(rotDistCm, -rotDistCm, rotDistCm, -rotDistCm);
		this.driveIndividually(power, -power, power, -power);
		this.waitForWheelsThenStop();
	}

	/**
	 * Rotates the robot clockwise. This is just for readability purposes.
	 *
	 * @param degrees Degrees to rotate clockwise.
	 * @param power The power to drive the motors at. Valid values are from {@code -1.0} to {@code 1.0}, inclusive.
	 *              This should be negative to rotate counterclockwise, and positive to rotate clockwise.
	 * @see CarWheels#rotate(double, double)
	 * @author youngermax
	 */
	public void rotateClockwise(double degrees, double power) {
		this.rotate(Math.abs(degrees), power);
	}

	/**
	 * Rotates the robot counterclockwise. This is just for readability purposes.
	 *
	 * @param degrees Degrees to rotate counterclockwise.
	 * @param power The power to drive the motors at. Valid values are from {@code -1.0} to {@code 1.0}, inclusive.
	 *              This should be negative to rotate counterclockwise, and positive to rotate clockwise.
	 * @see CarWheels#rotate(double, double)
	 * @author youngermax
	 */
	public void rotateCounterclockwise(double degrees, double power) {
		this.rotate(-Math.abs(degrees), power);
	}

	/**
	 * Waits for all the wheels to meet the drive target, then stops them.
	 *
	 * @see Wheel#setDriveTarget(double)
	 * @author youngermax
	 */
	private void waitForWheelsThenStop() {
		// Wait for motors to stop moving
		while (this.frontLeft.motor.isBusy() && this.frontRight.motor.isBusy() && this.backLeft.motor.isBusy()
			&& this.backRight.motor.isBusy()) { }

		// Stop driving
		// TODO: fix these deprecations by checking if `this.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);` is
		//  necessary in `stopMoving`
		this.frontLeft.stopMoving();
		this.frontRight.stopMoving();
		this.backLeft.stopMoving();
		this.backRight.stopMoving();
	}

	/**
	 * Closes the internal motors. <strong>THIS SHOULD BE CALLED WHEN THE MOTORS ARE DONE BEING USED!</strong>
	 *
	 * @author youngermax
	 */
	@Override
	public void close() {
		this.frontLeft.close();
		this.frontRight.close();
		this.backLeft.close();
		this.backRight.close();
	}
}
