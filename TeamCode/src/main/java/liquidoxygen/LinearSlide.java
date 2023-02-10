package liquidoxygen;

import com.pocolifo.robobase.motor.Wheel;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Linear slide implementation. <strong>REQUIRES</strong> that the linear slide must start at its
 * <strong>BOTTOM POSITION</strong>!
 */
public class LinearSlide implements AutoCloseable {
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

	private static final float SPEED = 1;
	private final DcMotor liftMotor;
	private Position currentPosition;

	public LinearSlide(DcMotor dcMotor) {
		this.liftMotor = dcMotor;
		this.liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		this.liftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
		this.liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

		this.currentPosition = Position.BOTTOM; // Must be physically moved prior to each round
	}

	public void calibrate() {
		this.liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		this.liftMotor.setPower(1);
		this.liftMotor.setDirection(DcMotorSimple.Direction.FORWARD);

		int lastEncoderPos = 0;

		while (lastEncoderPos != this.liftMotor.getTargetPosition()) {
			lastEncoderPos = this.liftMotor.getTargetPosition();
		}

		// At top
		this.currentPosition = Position.LARGE_POLE;
	}

	public void setPosition(Position position) {
		this.liftMotor.setTargetPosition((int) Math.ceil(position.cmFromBottom - this.currentPosition.cmFromBottom));
		this.liftMotor.setPower(1);
		this.liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		this.currentPosition = position;

		System.out.printf("Target: %d%n", this.liftMotor.getTargetPosition());
		System.out.printf("Pos: %s%n", position.name());
	}

	public void update() {
		if (!this.liftMotor.isBusy()) {
			this.liftMotor.setPower(0);
		}
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

	public void stopDriving() {
		this.liftMotor.setPower(0);
	}

	public void driveDown(boolean micromovement) {
		this.liftMotor.setPower(-SPEED / (micromovement?1:2));
	}

	public void driveUp(boolean micromovement) {
		this.liftMotor.setPower(SPEED / (micromovement?1:2));
	}

	@Override
	public void close() {
		this.liftMotor.close();
	}
}
