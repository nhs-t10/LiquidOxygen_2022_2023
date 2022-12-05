package liquidoxygen;

import com.pocolifo.robobase.Robot;
import com.pocolifo.robobase.motor.CarWheels;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Shared {
	public static final String TELEOP_NAME = "Example TeleOp Mode";
	public static final String AUTONOMOUS_NAME = "Example Autonomous Mode";
	public static final String GROUP = "lo2";
	public static final Robot ROBOT = new Robot(40d, 60d, 50d, 9326, "Robot", true);

	public static CarWheels createWheels(HardwareMap hardwareMap) {
		return new CarWheels(
			hardwareMap,
			1120,
			1000, // TODO: measure the actual diameter (in cm) of the wheels
			ROBOT,
			"Front Left Motor",
			"Front Right Motor",
			"Back Left Motor",
			"Back Right Motor"
		);
	}
}
