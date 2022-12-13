package liquidoxygen;

import com.pocolifo.robobase.Robot;
import com.pocolifo.robobase.motor.CarWheels;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Shared {
	public static final String TELEOP_NAME = "Example TeleOp Mode";
	public static final String AUTONOMOUS_NAME = "Example Autonomous Mode";
	public static final String GROUP = "lo2";
	public static final Robot ROBOT = new Robot(43, 29, 27, 9326, "Robot", true);
	public static final int MOTOR_TICK_COUNT = 1120;

	public static CarWheels createWheels(HardwareMap hardwareMap) {
		return new CarWheels(
			hardwareMap,
			MOTOR_TICK_COUNT,
			10,
			ROBOT,
			"Front Left Motor",
			"Front Right Motor",
			"Back Left Motor",
			"Back Right Motor"
		);
	}
}
