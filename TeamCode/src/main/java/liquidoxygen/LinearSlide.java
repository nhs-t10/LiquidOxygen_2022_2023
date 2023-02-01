package liquidoxygen;

import com.pocolifo.robobase.motor.Wheel;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Linear slide implementation. <strong>REQUIRES</strong> that the linear slide must start at its
 * <strong>BOTTOM POSITION</strong>!
 */
public class LinearSlide extends Thread implements AutoCloseable {
	public enum Position {
		BOTTOM(0),
		GROUND_JUNCTION(5),
		SMALL_POLE(35),
		MEDIUM_POLE(65),
		LARGE_POLE(95);

		public final float cmFromBottom;

		Position(float cmFromBottom) {
			this.cmFromBottom = cmFromBottom;
		}
	}

	private final Wheel liftMotor;
	private Position currentPosition;

	public LinearSlide(DcMotor dcMotor) {
		this.liftMotor = new Wheel(dcMotor, 288, 5.5);
		this.liftMotor.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

		this.currentPosition = Position.BOTTOM; // Must be physically moved prior to each round
	}

	public void setPosition(Position position) {
		this.liftMotor.setDriveTarget(position.cmFromBottom - this.currentPosition.cmFromBottom);
		this.liftMotor.drive(1);
		this.currentPosition = position;
	}

	public void update() {
		if (!this.liftMotor.motor.isBusy()) this.liftMotor.stopMoving();
	}

	public void down() {
		// TODO: clean up
		switch (this.currentPosition) {
			case LARGE_POLE:
				this.setPosition(Position.MEDIUM_POLE);
				break;

			case MEDIUM_POLE:
				this.setPosition(Position.SMALL_POLE);
				break;

			case SMALL_POLE:
				this.setPosition(Position.GROUND_JUNCTION);
				break;

			case GROUND_JUNCTION:
				this.setPosition(Position.BOTTOM);
				break;
		}
	}

	public void up() {
		// TODO: clean up
		switch (this.currentPosition) {
			case MEDIUM_POLE:
				this.setPosition(Position.LARGE_POLE);
				break;

			case SMALL_POLE:
				this.setPosition(Position.MEDIUM_POLE);
				break;

			case GROUND_JUNCTION:
				this.setPosition(Position.SMALL_POLE);
				break;

			case BOTTOM:
				this.setPosition(Position.GROUND_JUNCTION);
				break;

		}
	}

	@Override
	public void close() {
		this.liftMotor.close();
	}
}
