package liquidoxygen.autonomous;

import android.os.SystemClock;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import liquidoxygen.BuildProperties;
import liquidoxygen.Shared;

@Autonomous(name = "Autonomous (Plan A) " + BuildProperties.VERSION, group = Shared.GROUP, preselectTeleOp = Shared.TELEOP_NAME)
public class AutonomousModeLeft extends AbstractCameraWheelsAutoMode {
	public void update(float v, float h, float r) {
		// Drive the wheels to match the controller input
		float[] vals = new float[] {v - h - r, v + r + h, v - r + h, v + r - h};
		boolean norm = false;
		float tonorm = 0;

		for (int i = 0; i < 4; i++) {
			if ((vals[i] > 1 && vals[i] > tonorm) || (vals[i] < -1 && vals[i] < -tonorm)) {
				norm = true;
				tonorm = Math.abs(vals[i]);
			}
		}

		if (norm) {
			for (int i = 0; i < 4; i++) {
				vals[i] /= tonorm;
			}
		}

		this.wheels.driveIndividually(-vals[0], vals[1], -vals[2], vals[3]);

		// Omni-drive
	}

	@Override
	public void moveLeftSide() {
		this.update(-0.5f,0,0);
		SystemClock.sleep(1000);
		this.update(0,0.5f,0);
		SystemClock.sleep(1000);
		this.update(0,0,0);
	}

	@Override
	public void moveMiddle() {
		this.update(-0.5f,0,0);
		SystemClock.sleep(1000);
		this.update(0,0,0);
	}

	@Override
	public void moveRightSide() {
		this.update(-0.5f, 0, 0);
		SystemClock.sleep(1000);
		this.update(0, 0.5f, 0);
		SystemClock.sleep(1000);
		this.update(0, 0, 0);
	}
}

