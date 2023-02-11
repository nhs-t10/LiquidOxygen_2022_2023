package liquidoxygen.teleop;

import com.pocolifo.robobase.bootstrap.TeleOpOpMode;
import com.pocolifo.robobase.control.GamepadCarWheels;
import com.pocolifo.robobase.control.Pressable;
import com.pocolifo.robobase.control.Toggleable;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import liquidoxygen.GrabberThread;
import liquidoxygen.LinearSlide;
import liquidoxygen.Shared;

@TeleOp(name = "[A] " + Shared.TELEOP_NAME, group = Shared.GROUP)
public class TeleOpMode extends TeleOpOpMode {

	//Create variables
	private GamepadCarWheels wheels;
	private LinearSlide linearSlide;
	private Toggleable microMovementState;
	private Toggleable clawState;
	private Servo claw;

	@Override
	public void initialize() {
		System.out.println("[!!!] The linear slide must be at its bottom position!");

		//Initialize variables
		this.wheels = new GamepadCarWheels(Shared.createWheels(this.hardwareMap), this.gamepad1);
		this.linearSlide = new LinearSlide(this.hardwareMap.dcMotor.get("Lift"));
		this.claw = this.hardwareMap.servo.get("Claw");

		this.microMovementState = new Toggleable(() -> this.gamepad1.x);
		this.clawState = new Toggleable(() -> this.gamepad1.a);

		System.out.println("Successfully initialized.");
	}

	@Override
	public void loop() {
		// Gamepad controls
		// Wheels
		this.wheels.update(this.microMovementState.get());
		this.linearSlide.update();

		// Lift
		// Allow state change once every 100ms
		if (this.gamepad1.dpad_up) {
			this.linearSlide.driveUp(this.microMovementState.get());
		} else if (this.gamepad1.dpad_down) {
			this.linearSlide.driveDown(this.microMovementState.get());
		} else {
			this.linearSlide.stopDriving();
		}

		// Claw
		this.clawState.processUpdates()
		.onToggleOn(() -> {
			this.claw.getController().pwmEnable();
			this.claw.setPosition(1);
		}).onToggleOff(() -> {
			this.claw.setPosition(0);
			this.claw.getController().pwmDisable();
		});
	}

	@Override
	public void stop() {
		//Closes all threads
		this.wheels.close();
		this.linearSlide.close();
	}
}
