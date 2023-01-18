package liquidoxygen.teleop;

import com.pocolifo.robobase.bootstrap.TeleOpOpMode;
import com.pocolifo.robobase.control.GamepadCarWheels;
import com.pocolifo.robobase.motor.Motor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import liquidoxygen.Shared;

@TeleOp(name = "Tele Op (Plan B)", group = Shared.GROUP)
public class TeleOpPlanB extends TeleOpOpMode {
	private GamepadCarWheels wheels;
	private Motor rightLiftMotor;
	private Motor leftLiftMotor;
	private Servo rightArmServo;
	private Servo leftArmServo;

	@Override
	public void initialize() {
		System.out.println("Initializing the robot! Be ready in a second!");

		this.wheels = new GamepadCarWheels(Shared.createWheels(this.hardwareMap), this.gamepad1);

		this.rightLiftMotor = new Motor(this.hardwareMap.dcMotor.get("Right Lift"), Shared.MOTOR_TICK_COUNT);
		this.rightLiftMotor.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

		this.leftLiftMotor = new Motor(this.hardwareMap.dcMotor.get("Left Lift"), Shared.MOTOR_TICK_COUNT);
		this.leftLiftMotor.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

		this.rightArmServo = this.hardwareMap.servo.get("Right Arm");
		this.leftArmServo = this.hardwareMap.servo.get("Left Arm");

		System.out.println("All ready!");
	}

	@Override
	public void loop() {
		// Gamepad controls
		// Wheels
		this.wheels.update();

		//Button
		if (this.gamepad1.a) {
			this.rightLiftMotor.drive(0.8);
			this.leftLiftMotor.drive(-0.8);
			try {
				wait(250);
			} catch (InterruptedException e) {}
			try {
				wait(250);
			} catch (InterruptedException e) {}
			this.rightLiftMotor.drive(-0.8);
			this.leftLiftMotor.drive(0.8);
			try {
				wait(250);
			} catch (InterruptedException e) {}
			this.rightArmServo.setPosition(0);
			this.leftArmServo.setPosition(0);
			this.rightLiftMotor.drive(0.8);
			this.leftLiftMotor.drive(-0.8);
			this.rightArmServo.setPosition(0.1);
			this.leftArmServo.setPosition(0.1);
		}

		// Lift
		if (this.gamepad1.right_bumper) {
			this.rightLiftMotor.drive(0.8);
			this.leftLiftMotor.drive(-0.8);
		} else if (this.gamepad1.left_bumper) {
			this.rightLiftMotor.drive(-0.8);
			this.leftLiftMotor.drive(0.8);
		} else {
			this.rightLiftMotor.stopMoving();
			this.leftLiftMotor.stopMoving();
		}

		// Arm
		if (this.gamepad1.right_trigger > 0) {
			this.rightArmServo.setPosition(0);
			this.leftArmServo.setPosition(0);
		} else if (this.gamepad1.left_trigger > 0) {
			this.rightArmServo.setPosition(0.1);
			this.leftArmServo.setPosition(0.1);
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
