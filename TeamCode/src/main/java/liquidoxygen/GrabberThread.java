package liquidoxygen;

import com.qualcomm.robotcore.hardware.Servo;

public class GrabberThread extends Thread implements AutoCloseable {
	public enum GrabberAction {
		NOT_ACTIVE,
		OPENING_CLAW,
		CLOSING_CLAW
	}

	private final Servo servo;
	private GrabberAction currentAction;
	private boolean running;

	public GrabberThread(Servo servo) {
		this.servo = servo;
		this.currentAction = GrabberAction.NOT_ACTIVE;

		this.start();
	}

	@Override
	public void run() {
		while (this.running) {
			switch (this.currentAction) {
				case OPENING_CLAW:
					// Open the grabber up
					// Turn the power back on (see below if case)
					this.servo.getController().pwmEnable();
					this.servo.setPosition(1);
					this.currentAction = GrabberAction.NOT_ACTIVE;
					break;

				case CLOSING_CLAW:
					// Close it up
					this.servo.setPosition(0);

					// Turn the power of the grabber OFF.
					// This allows the servo to claw to fully retract.
					this.servo.getController().pwmDisable();
					this.currentAction = GrabberAction.NOT_ACTIVE;
					break;
			}
		}
	}

	public void openClaw() {
		if (this.currentAction == GrabberAction.NOT_ACTIVE) {
			this.currentAction = GrabberAction.OPENING_CLAW;
		}
	}

	public void closeClaw() {
		if (this.currentAction == GrabberAction.NOT_ACTIVE) {
			this.currentAction = GrabberAction.CLOSING_CLAW;
		}
	}

	@Override
	public void close() {
		this.running = false;
	}
}
