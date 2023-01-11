package liquidoxygen.autonomous;

import com.pocolifo.robobase.bootstrap.AutonomousOpMode;
import com.pocolifo.robobase.motor.CarWheels;
import com.pocolifo.robobase.vision.Webcam;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import liquidoxygen.Shared;

@Autonomous(name = Shared.AUTONOMOUS_NAME, group = Shared.GROUP, preselectTeleOp = Shared.TELEOP_NAME)
public class AutonomousModeRight extends AutonomousOpMode {
	private CarWheels wheels;
	private Webcam webcam;

	@Override
	public void initialize() {
		this.wheels = Shared.createWheels(this.hardwareMap);

		System.out.println("Creating camera");
		this.webcam = new Webcam(this.hardwareMap, "Webcam");
		System.out.println("Opening camera");
		this.webcam.open(new ColorCapturePipeline());

		System.out.println("Initialized Autonomous! Ready for take off!");
	}

	@Override
	public void run() {
		DetectedColor color = (DetectedColor) webcam.getPipeline().getResult();

		System.out.printf("Detected color: %s%n", color.name());
	}

	@Override
	public void stop() {
		try {
			this.webcam.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
