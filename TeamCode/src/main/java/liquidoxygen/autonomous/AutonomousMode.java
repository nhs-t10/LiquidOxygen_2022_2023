package liquidoxygen.autonomous;

import static liquidoxygen.Shared.DISTANCE_TO_CENTER_CM;
import com.pocolifo.robobase.bootstrap.AutonomousOpMode;
import com.pocolifo.robobase.control.EncoderTracker;
import com.pocolifo.robobase.motor.CarWheels;
import com.pocolifo.robobase.vision.Webcam;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import liquidoxygen.Claw;
import liquidoxygen.LinearSlide;
import liquidoxygen.Shared;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class AutonomousMode extends AutonomousOpMode {
	private CarWheels wheels;
	private Webcam webcam;
	private LinearSlide linearSlide;
	private DistanceSensor distance;
	private boolean isLeft;
	private Claw claw;

	public AutonomousMode(boolean isLeft) {
		this.isLeft = isLeft;
	}

	@Override
	public void initialize() {
		System.out.print("PLEASE WAIT! ");

		this.wheels = Shared.createWheels(this.hardwareMap);
		this.webcam = new Webcam(this.hardwareMap, "Webcam");
		this.webcam.open(new ColorCapturePipeline());
		this.claw = new Claw(this.hardwareMap.servo.get("Claw"));
		this.linearSlide = new LinearSlide(this.hardwareMap.dcMotor.get("Linear Slide"));
		this.distance = this.hardwareMap.get(DistanceSensor.class, "Distance Sensor");

		System.out.println("Initialized Autonomous! Ready for take off!");
	}

	@Override
	public void run() {
		DetectedColor color = (DetectedColor) webcam.getPipeline().getResult();

		try {
			this.webcam.close();
		} catch (Exception e) {
			System.out.println("[!!!!!] WEBCAM DID NOT CLOSE PROPERLY! Still running anyway...");
		}

		System.out.printf("Detected color: %s%n", color.name());

		switch (color) {
			case RED:
				// LEFT side
				System.out.println("[!] Moving [Red] -> [Left]");
				this.moveLeftSide();
				break;

			case GREEN:
				// MIDDLE
				System.out.println("[!] Moving [Green] -> [Middle]");
				this.moveMiddle();
				break;

			case BLUE:
				// RIGHT
				System.out.println("[!] Moving [Blue] -> [Right]");
				this.moveRightSide();
				break;
		}

		this.wheels.drive(-60.96, false);
	}

	public void moveLeftSide() {
		this.wheels.drive(-60.96,true);
	}

	public void moveMiddle() {

	}

	public void moveRightSide() {
		this.wheels.drive(60.96,true);
	}

	@Override
	public void stop() {
		try {
			this.wheels.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
