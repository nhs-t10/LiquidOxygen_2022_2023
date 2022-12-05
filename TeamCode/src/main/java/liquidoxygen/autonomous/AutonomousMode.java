package liquidoxygen.autonomous;

import com.pocolifo.robobase.bootstrap.AutonomousOpMode;
import com.pocolifo.robobase.motor.CarWheels;
import com.pocolifo.robobase.vision.Webcam;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import liquidoxygen.Shared;

@Autonomous(name = Shared.AUTONOMOUS_NAME, group = Shared.GROUP, preselectTeleOp = Shared.TELEOP_NAME)
public class AutonomousMode extends AutonomousOpMode {
	private CarWheels wheels;

	@Override
	public void initialize() {
		System.out.println("Initializing everything!");

		this.wheels = Shared.createWheels(this.hardwareMap);

		System.out.println("Initialized Autonomous! Ready for take off!");
	}

	@Override
	public void run() {
		// MOVEMENT TEST
		System.out.println("Moving the robot...");

		this.wheels.drive(10.825, 1d);
		this.wheels.rotateClockwise(90d, 1d);
		this.wheels.drive(50d, 1d);

		System.out.println("Reached destination! Waiting 3 seconds...");

		sleep(3000);

		System.out.println("Done waiting! Slowly moving the robot back...");

		this.wheels.drive(-50d, 0.25d);
		this.wheels.rotateCounterclockwise(90d, 0.25d);
		this.wheels.drive(-10.825, 0.25d);

		System.out.println("Moved the robot back! Checking camera...");

		// COLOR CAPTURE TEST
		DetectedColor color;

		try (Webcam webcam = new Webcam(this.hardwareMap, "Webcam")) {
			ColorCapturePipeline pipeline = new ColorCapturePipeline();

			webcam.open();
			webcam.setPipeline(pipeline);

			sleep(250L); // Give the camera some time to figure out what color is detected

			color = (DetectedColor) webcam.getPipeline().getResult();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		System.out.printf("Detected color: %s%n", color.name());
	}

	@Override
	public void stop() {
		this.wheels.close();
	}
}
