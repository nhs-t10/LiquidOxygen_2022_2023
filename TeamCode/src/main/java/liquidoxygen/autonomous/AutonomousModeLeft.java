package liquidoxygen.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import liquidoxygen.BuildProperties;
import liquidoxygen.Shared;

@Autonomous(name = "Autonomous (Plan A) " + BuildProperties.VERSION, group = Shared.GROUP, preselectTeleOp = Shared.TELEOP_NAME)
public class AutonomousModeLeft extends AbstractCameraWheelsAutoMode {
	@Override
	public void moveLeftSide() {
		this.wheels.drive(30, -0.5);
		this.wheels.rotateCounterclockwise(90, 0.5);
		this.wheels.drive(25, -0.5);
	}

	@Override
	public void moveMiddle() {
		this.wheels.drive(30, -0.5);
	}

	@Override
	public void moveRightSide() {
		this.wheels.drive(30, -0.5);
		this.wheels.rotateClockwise(90, 0.5);
		this.wheels.drive(25, -0.5);
	}
}
