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

	@Override
	public void initialize() {
		System.out.println("Initializing the robot! Be ready in a second!");

		this.wheels = Shared.createWheels(this.hardwareMap);
		this.rightLiftMotor = new Motor(this.hardwareMap.dcMotor.get("Right Lift"), 1120);
		this.leftLiftMotor = new Motor(this.hardwareMap.dcMotor.get("Left Lift"), 1120);
		this.rightArmServo = this.hardwareMap.servo.get("Right Arm");

		System.out.println("All ready!");
	}

	@Override
	public void loop() {
		// Gamepad controls
		// Wheels
		this.wheels.driveIndividually(
				-1 * this.gamepad1.left_stick_y + this.gamepad1.left_stick_x,
				this.gamepad1.left_stick_y - this.gamepad1.left_stick_x,
				-1 * this.gamepad1.left_stick_y + this.gamepad1.left_stick_x,
				this.gamepad1.left_stick_y - this.gamepad1.left_stick_x
		);

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
		} else if (this.gamepad1.left_trigger > 0.25) {
			this.rightArmServo.setPosition(0);
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
