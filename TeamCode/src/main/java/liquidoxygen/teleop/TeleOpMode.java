package liquidoxygen.teleop;

import com.pocolifo.robobase.bootstrap.TeleOpOpMode;
import com.pocolifo.robobase.motor.CarWheels;
import com.pocolifo.robobase.motor.Motor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import liquidoxygen.Shared;

@TeleOp(name = Shared.TELEOP_NAME, group = Shared.GROUP)
public class TeleOpMode extends TeleOpOpMode {
	private CarWheels wheels;
	private Motor rightLiftMotor;
	private Motor leftLiftMotor;
	private Servo rightArmServo;
	private Servo leftArmServo;

	@Override
	public void initialize() {
		System.out.println("Initializing the robot! Be ready in a second!");

		this.wheels = Shared.createWheels(this.hardwareMap);
		this.rightLiftMotor = new Motor(this.hardwareMap.dcMotor.get("Right Lift"), Shared.MOTOR_TICK_COUNT);
		this.leftLiftMotor = new Motor(this.hardwareMap.dcMotor.get("Left Lift"), Shared.MOTOR_TICK_COUNT);
		this.rightArmServo = this.hardwareMap.servo.get("Right Arm");
		this.leftArmServo = this.hardwareMap.servo.get("Left Arm");

		System.out.println("All ready!");
	}

	private float squareWSign(float input) {
		return input >= 0 ? input * input : -1 * input * input;
	}

	@Override
	public void loop() {
		// Gamepad controls
		// Wheels
		float squaredLeftStickX = squareWSign(this.gamepad1.left_stick_x);
		float squaredLeftStickY = squareWSign(this.gamepad1.left_stick_y);

		//		this.wheels.driveIndividually(0.15d, 0.15d, 0.15d, 0.15d);
		//		this.wheels.setDriveTargetIndividually(100, 100, 100, 100);

		// front left wheel
		this.wheels.driveIndividually(
							-(squaredLeftStickY + squaredLeftStickX),
							 (squaredLeftStickY - squaredLeftStickX),
							 (squaredLeftStickY - squaredLeftStickX),
							-(squaredLeftStickY + squaredLeftStickX)
		);

		/*
		driver.driveBlue(-(float)(i * squareWSign(input.getFloat("leftsticky")) +
		 squareWSign(input.getFloat("leftstickx"))),

			(float)(i * squareWSign(input.getFloat("leftsticky")) - squareWSign(input.getFloat("leftstickx"))),
			(float)(i * squareWSign(input.getFloat("leftsticky")) - squareWSign(input.getFloat("leftstickx"))),
			-(float)(i * squareWSign(input.getFloat("leftsticky")) + squareWSign(input.getFloat("leftstickx"))));

		this.wheels.driveIndividually(
				-1 * this.gamepad1.left_stick_y + this.gamepad1.left_stick_x,
				this.gamepad1.left_stick_y - this.gamepad1.left_stick_x,
				-1 * this.gamepad1.left_stick_y + this.gamepad1.left_stick_x,
				this.gamepad1.left_stick_y - this.gamepad1.left_stick_x
		);
		*/

		// Lift
		if (this.gamepad1.right_bumper) {
			this.rightLiftMotor.motor.setPower(0.4);
			this.leftLiftMotor.motor.setPower(-0.4);
		} else if (this.gamepad1.left_bumper) {
			this.rightLiftMotor.motor.setPower(-0.4);
			this.leftLiftMotor.motor.setPower(0.4);
		} else {
			this.rightLiftMotor.motor.setPower(0);
			this.leftLiftMotor.motor.setPower(0);
		}

		// Arm
		if (this.gamepad1.right_trigger > 0.25) {
			this.rightArmServo.setPosition(1);
			this.leftArmServo.setPosition(1);
		} else if (this.gamepad1.left_trigger > 0.25) {
			this.rightArmServo.setPosition(0);
			this.leftArmServo.setPosition(0);
		}
	}

	@Override
	public void stop() {
		this.wheels.close();
		this.rightArmServo.close();
		this.leftLiftMotor.close();
		this.rightLiftMotor.close();
	}
}
