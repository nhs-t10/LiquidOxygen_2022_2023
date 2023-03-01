package liquidoxygen.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import liquidoxygen.Shared;

@Autonomous(name = "[A] [Left] " + Shared.AUTONOMOUS_NAME, group = Shared.GROUP, preselectTeleOp = Shared.TELEOP_NAME)
public class AutoLeft extends AutonomousMode{
	public AutoLeft() {
		super(true);
	}
}
