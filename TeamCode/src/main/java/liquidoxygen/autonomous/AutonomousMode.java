package liquidoxygen.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import liquidoxygen.Shared;

@Autonomous(name = Shared.AUTONOMOUS_NAME, group = Shared.GROUP, preselectTeleOp = Shared.TELEOP_NAME)
public class AutonomousMode extends AbstractCameraWheelsAutoMode {
	@Override
	public void moveLeftSide() {
		this.wheels.driveOmni(0.5f, 0, 0);
		sleep(2000);
		this.wheels.driveOmni(0, 0.5f, 0);
		sleep(2100);
		this.wheels.driveOmni(0, 0, 0);
	}

	@Override
	public void moveMiddle() {
		this.wheels.driveOmni(0.5f, 0, 0);
		sleep(2000);
		this.wheels.driveOmni(0, 0, 0);
	}

	@Override
	public void moveRightSide() {
		this.wheels.driveOmni(0.5f, 0, 0);
		sleep(2000);
		this.wheels.driveOmni(0, -0.5f, 0);
		sleep(2100);
		this.wheels.driveOmni(0, 0, 0);
	}
}

