package liquidoxygen.autonomous;

import com.pocolifo.robobase.bootstrap.AutonomousOpMode;
import com.pocolifo.robobase.motor.CarWheels;
import com.pocolifo.robobase.motor.Wheel;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import liquidoxygen.Shared;

@Autonomous
public class TestAutoMode extends AutonomousOpMode {
	private Wheel frontRightMotor;
	private Wheel frontLeftMotor;
	private Wheel backRightMotor;
	private Wheel backLeftMotor;
	private CarWheels wheels;

	@Override
	public void initialize() {
		frontRightMotor = new Wheel(this.hardwareMap.dcMotor.get("Front Right Motor"), 1120, 10d);
		frontLeftMotor = new Wheel(this.hardwareMap.dcMotor.get("Front Left Motor"), 1120, 10d);
		backRightMotor = new Wheel(this.hardwareMap.dcMotor.get("Back Right Motor"), 1120, 10d);
		backLeftMotor = new Wheel(this.hardwareMap.dcMotor.get("Back Left Motor"), 1120, 10d);

		wheels = new CarWheels(Shared.ROBOT, frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor);
	}

	@Override
	public void run() {
		System.out.println("Starting!");

		System.out.println("3");
		sleep(1000L);
		System.out.println("2");
		sleep(1000L);
		System.out.println("1");
		sleep(1000L);
		System.out.println("DRIVE!!");

		// wheels.drive(100d, 0.2d);

		wheels.driveIndividually(0.3d, 0.3d, 0.3d, 0.3d);

		sleep(2500L);

		wheels.driveIndividually(0, 0, 0, 0);

		System.out.println("All done!");
	}
}
