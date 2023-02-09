package liquidoxygen.teleop;

import com.pocolifo.robobase.bootstrap.TeleOpOpMode;
import com.pocolifo.robobase.control.GamepadCarWheels;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import liquidoxygen.GrabberThread;
import liquidoxygen.LinearSlide;
import liquidoxygen.Shared;

@TeleOp(name = Shared.TELEOP_NAME, group = Shared.GROUP)
public class TeleOpMode extends TeleOpOpMode {

	//Create variables
	private GamepadCarWheels wheels;
	private long lastSlidePressed;
	private long lastClawPressed;
	private long xtime;
	private boolean x = true;
	private LinearSlide linearSlide;
	private GrabberThread grabberThread;

	@Override
	public void initialize() {
		System.out.println("[!!!] The linear slide must be at its bottom position!");

		//Initialize variables
		this.wheels = new GamepadCarWheels(Shared.createWheels(this.hardwareMap), this.gamepad1);
		this.linearSlide = new LinearSlide(this.hardwareMap.dcMotor.get("Lift"));
		this.grabberThread = new GrabberThread(this.hardwareMap.servo.get("Grabber"));
		lastSlidePressed = System.currentTimeMillis();
		xtime = System.currentTimeMillis();

		System.out.println("Successfully initialized.");
	}

	@Override
	public void loop() {
		// Gamepad controls
		// Wheels
		x = x ^ (this.gamepad1.x && System.currentTimeMillis()-xtime>=1000);
		xtime=(this.gamepad1.x && System.currentTimeMillis()-xtime>=1000)?System.currentTimeMillis():xtime;

		this.wheels.update(x);
		this.linearSlide.update();

		// Lift
		// Allow state change once every 100ms
		if (System.currentTimeMillis() - this.lastSlidePressed > 100) {
			if (this.gamepad1.dpad_up) {
				this.lastSlidePressed = System.currentTimeMillis();
				this.linearSlide.up();
			} else if (this.gamepad1.dpad_down) {
				this.lastSlidePressed = System.currentTimeMillis();
				this.linearSlide.down();
			}
		}

		// Grabber
		// Allow control of the grabber once every 500ms so the servo doesn't get overloaded
		if (System.currentTimeMillis() - this.lastClawPressed > 500) {
			if (this.gamepad1.a) {
				this.lastClawPressed = System.currentTimeMillis();
				this.grabberThread.openClaw();
			} else if (this.gamepad1.b) {
				this.lastClawPressed = System.currentTimeMillis();
				this.grabberThread.closeClaw();
			}
		}
	}

	@Override
	public void stop() {
		//Closes all threads
		this.wheels.close();
		this.linearSlide.close();
		this.grabberThread.close();
	}
}
