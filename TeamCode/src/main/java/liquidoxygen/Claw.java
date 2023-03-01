package liquidoxygen;

import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
	private final Servo servo;

	public Claw(Servo servo) {
		this.servo = servo;
	}

	//opens claw of robot
	public void openClaw() {
		// Open the grabber up
		// Turn the power back on (see below if case)
		this.servo.getController().pwmEnable();
		this.servo.setPosition(1);
	}

	//closes claw of robot
	public void closeClaw() {
		// Close it up
		this.servo.setPosition(0);

		// Turn the power of the grabber OFF.
		// This allows the servo to claw to fully retract.
		this.servo.getController().pwmDisable();
	}
}
