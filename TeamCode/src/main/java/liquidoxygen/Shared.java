package liquidoxygen;

import com.pocolifo.robobase.Robot;
import com.pocolifo.robobase.motor.CarWheels;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Shared {
	public static final String TELEOP_NAME = "Tele Op v" + BuildProperties.VERSION;
	public static final String AUTONOMOUS_NAME = "Autonomous v" + BuildProperties.VERSION;
	public static final String GROUP = "lo2";
	public static final Robot ROBOT = new Robot(43, 29, 27, 9326, "Robot", true);
	public static final int MOTOR_TICK_COUNT = 1120;

	/**
	 * Distance in centimeters from the distance sensor to the center of the robot.
	 */
	public static final float DISTANCE_TO_CENTER_CM = 8.89f;

	/*
	Cycles per revolution (motor shaft): 7
	Cycles per revolution (output shaft): 280
	Countable events per revolution (motor shaft): 28
	Countable events per revolution (output shaft): 1,120
	 */

	public static CarWheels createWheels(HardwareMap hardwareMap) {
		return new CarWheels(
			hardwareMap,
			MOTOR_TICK_COUNT,
			10,
			ROBOT,
			"Front Left Motor",
			"Front Right Motor",
			"Back Left Motor",
			"Back Right Motor",
			"Front Left Motor" // this is the motor that has the encoder connector
		);
	}
}
