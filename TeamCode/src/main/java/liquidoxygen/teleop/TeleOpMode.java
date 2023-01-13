package liquidoxygen.teleop;

import com.pocolifo.robobase.bootstrap.TeleOpOpMode;
import com.pocolifo.robobase.control.GamepadCarWheels;
import com.pocolifo.robobase.motor.Motor;
import com.pocolifo.robobase.motor.Wheel;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import liquidoxygen.Shared;

@TeleOp(name = Shared.TELEOP_NAME, group = Shared.GROUP)
public class TeleOpMode extends TeleOpOpMode {
	private GamepadCarWheels wheels;
	private Wheel liftMotor;

	@Override
	public void initialize() {
		this.wheels = new GamepadCarWheels(Shared.createWheels(this.hardwareMap), this.gamepad1);
		this.liftMotor = new Wheel(this.hardwareMap.dcMotor.get("Lift"), 288, 5.5);
		this.liftMotor.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

		System.out.println("Successfully initialized.");
	}

	@Override
	public void loop() {
		// Gamepad controls
		// Wheels
		this.wheels.update(this.gamepad1.left_stick_y, this.gamepad1.left_stick_x, this.gamepad1.right_stick_x);

		// Lift
		if (this.gamepad1.dpad_up) {
			this.liftMotor.drive(0.5);
		} else if (this.gamepad1.dpad_down) {
			this.liftMotor.drive(-0.5);
		} else {
			this.liftMotor.drive(0);
		}
	}

	@Override
	public void stop() {
		this.wheels.close();
		this.liftMotor.close();
	}
}
