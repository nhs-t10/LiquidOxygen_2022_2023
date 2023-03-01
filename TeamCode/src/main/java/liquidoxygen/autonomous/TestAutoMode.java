package liquidoxygen.autonomous;

import com.pocolifo.robobase.bootstrap.AutonomousOpMode;
import com.pocolifo.robobase.motor.CarWheels;
import com.pocolifo.robobase.motor.Wheel;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import liquidoxygen.Shared;

@Autonomous
public class TestAutoMode extends AutonomousOpMode {
	private CarWheels wheels;
	//Used for the testing of encoders.
	@Override
	public void initialize() {
		this.wheels = Shared.createWheels(this.hardwareMap);
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

		wheels.drive(100d, false);
		wheels.drive(-100d, false);
		wheels.drive(100d, true);
		wheels.drive(-100d, true);

		/*this.wheels.driveIndividually(0.3d, 0.3d, 0.3d, 0.3d);

		sleep(2500L);*/

		this.wheels.driveIndividually(0, 0, 0, 0);

		System.out.println("All done!");
	}
}
