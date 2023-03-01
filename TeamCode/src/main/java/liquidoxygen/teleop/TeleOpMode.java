package liquidoxygen.teleop;

import com.pocolifo.robobase.bootstrap.TeleOpOpMode;
import com.pocolifo.robobase.control.GamepadCarWheels;
import com.pocolifo.robobase.control.Toggleable;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import liquidoxygen.Claw;
import liquidoxygen.LinearSlide;
import liquidoxygen.Shared;

@TeleOp(name = "[A] " + Shared.TELEOP_NAME, group = Shared.GROUP)
public class TeleOpMode extends TeleOpOpMode {

	//Create variables
	private GamepadCarWheels wheels;
	private LinearSlide linearSlide;
	private Toggleable microMovementState;
	private Claw claw;

	@Override
	public void initialize() {
		System.out.println("[!!!] The linear slide must be at its bottom position!");

		//Initialize variables
		this.wheels = new GamepadCarWheels(Shared.createWheels(this.hardwareMap), this.gamepad1);

		this.linearSlide = new LinearSlide(this.hardwareMap.dcMotor.get("Linear Slide"));
		this.claw = new Claw(this.hardwareMap.servo.get("Claw"));

		this.microMovementState = new Toggleable(() -> this.gamepad1.x);

		System.out.println("Successfully initialized.");
	}

	@Override
	public void loop() {
		// Gamepad controls
		// Wheels
		this.wheels.update(this.microMovementState.processUpdates().get());
		this.linearSlide.update();

		// Lift
		// Allow state change once every 100ms
		if (this.gamepad1.dpad_up) {
			this.linearSlide.driveUp();
		} else if (this.gamepad1.dpad_down) {
			this.linearSlide.driveDown();
		} else {
			this.linearSlide.stopDriving();
		}

		// Claw
		if (this.gamepad1.left_bumper) {
			this.claw.openClaw();
		} else if (this.gamepad1.right_bumper) {
			this.claw.closeClaw();
		}
	}

	@Override
	public void stop() {
		//Closes all threads
		this.wheels.close();
		this.linearSlide.close();
	}
}
