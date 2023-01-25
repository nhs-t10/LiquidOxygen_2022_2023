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
	private long lastPressed;
	private long xtime;
	private boolean x;

	@Override
	public void initialize() {
		this.wheels = new GamepadCarWheels(Shared.createWheels(this.hardwareMap), this.gamepad1);
		this.liftMotor = new Wheel(this.hardwareMap.dcMotor.get("Lift"), 288, 5.5);
		this.liftMotor.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		this.grabberServo = this.hardwareMap.servo.get("Grabber");
		lastPressed = System.currentTimeMillis();
		xtime = System.currentTimeMillis();

		System.out.println("Successfully initialized.");
	}

	@Override
	public void loop() {
		// Gamepad controls
		// Wheels
		x = x ^ (this.gamepad1.x && System.currentTimeMillis()-xtime>=1000);
		xtime=(this.gamepad1.x && System.currentTimeMillis()-xtime>=1000)?System.currentTimeMillis():xtime;

		//x=this.gamepad1.x

		this.wheels.update(x);

		// Lift
		if (this.gamepad1.dpad_up) {
			this.liftMotor.drive(1);
		} else if (this.gamepad1.dpad_down) {
			this.liftMotor.drive(-1);
		} else {
			this.liftMotor.drive(0);
		}

		// Grabber
		// Allow control of the grabber once every 500ms so the servo doesn't get overloaded
		if (System.currentTimeMillis() - this.lastPressed > 500) {
			if (this.gamepad1.a) {
				// Open the grabber up
				// Turn the power back on (see below if case)
				this.grabberServo.getController().pwmEnable();
				this.grabberServo.setPosition(1);

				// Reset the pressed time
				this.lastPressed = System.currentTimeMillis();
			} else if (this.gamepad1.b) {
				// Close it up
				this.grabberServo.setPosition(0);

				// Turn the power of the grabber OFF.
				// This allows the servo to claw to fully retract.
				this.grabberServo.getController().pwmDisable();

				// Reset the pressed time
				this.lastPressed = System.currentTimeMillis();
			}
		}
	}

	@Override
	public void stop() {
		this.wheels.close();
		this.liftMotor.close();
	}
}
