package liquidoxygen.teleop;

import com.pocolifo.robobase.bootstrap.TeleOpOpMode;
import com.pocolifo.robobase.control.GamepadCarWheels;
import com.pocolifo.robobase.motor.Wheel;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import liquidoxygen.Shared;

@TeleOp(name = Shared.TELEOP_NAME, group = Shared.GROUP)
public class TeleOpMode extends TeleOpOpMode {
	private GamepadCarWheels wheels;
	private Wheel liftMotor;
	private Servo grabberServo;

	@Override
	public void initialize() {
		this.wheels = new GamepadCarWheels(Shared.createWheels(this.hardwareMap), this.gamepad1);
		this.liftMotor = new Wheel(this.hardwareMap.dcMotor.get("Lift"), 288, 5.5);
		this.liftMotor.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		this.grabberServo = this.hardwareMap.servo.get("Grabber");

		System.out.println("Successfully initialized.");
	}

	@Override
	public void loop() {
		// Gamepad controls
		// Wheels
		this.wheels.update();

		// Lift
		if (this.gamepad1.dpad_up) {
			this.liftMotor.drive(0.5);
		} else if (this.gamepad1.dpad_down) {
			this.liftMotor.drive(-0.5);
		} else {
			this.liftMotor.drive(0);
		}

		// Grabber
		if (this.gamepad1.dpad_left) {
			this.grabberServo.getController().pwmEnable();
			this.grabberServo.setPosition(1);
		} else if (this.gamepad1.dpad_right) {
			this.grabberServo.setPosition(0);
			this.grabberServo.getController().pwmDisable();
		}
	}

	@Override
	public void stop() {
		this.wheels.close();
		this.liftMotor.close();
	}
}
