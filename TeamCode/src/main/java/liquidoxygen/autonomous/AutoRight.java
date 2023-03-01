package liquidoxygen.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import liquidoxygen.Shared;

@Autonomous(name = "[A] [Right] " + Shared.AUTONOMOUS_NAME, group = Shared.GROUP, preselectTeleOp = Shared.TELEOP_NAME)
public class AutoRight extends AutonomousMode {
	public AutoRight() {
		super(false);
	}
}
