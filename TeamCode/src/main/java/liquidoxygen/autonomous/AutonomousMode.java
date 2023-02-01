package liquidoxygen.autonomous;

import com.pocolifo.robobase.bootstrap.AutonomousOpMode;
import com.pocolifo.robobase.motor.CarWheels;
import com.pocolifo.robobase.motor.Wheel;
import com.pocolifo.robobase.vision.Webcam;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import liquidoxygen.GrabberThread;
import liquidoxygen.LinearSlide;
import liquidoxygen.Shared;

@Autonomous(name = Shared.AUTONOMOUS_NAME, group = Shared.GROUP, preselectTeleOp = Shared.TELEOP_NAME)
public class AutonomousMode extends AutonomousOpMode {
	private CarWheels wheels;
	private Webcam webcam;
	private GrabberThread grabberThread;
	private LinearSlide linearSlide;

	@Override
	public void initialize() {
		System.out.println("PLEASE WAIT!");
		System.out.println("[!!!] The linear slide must be at its bottom position!");

		this.wheels = Shared.createWheels(this.hardwareMap);
		this.webcam = new Webcam(this.hardwareMap, "Webcam");
		this.webcam.open(new ColorCapturePipeline());
		this.grabberThread = new GrabberThread(this.hardwareMap.servo.get("Grabber"));
		this.linearSlide = new LinearSlide(this.hardwareMap.dcMotor.get("Lift"));

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
	}

	public void moveLeftSide() {
		this.wheels.driveOmni(0.5f, 0, 0);
		sleep(2000);
		this.linearSlide.setPosition(LinearSlide.Position.SMALL_POLE);
		this.wheels.driveOmni(0, 0.5f, 0);
		sleep(3200);
		this.grabberThread.openClaw();
		sleep(1000);
		this.wheels.driveOmni(0, 0, 0);
	}

	public void moveMiddle() {
		this.wheels.driveOmni(0.5f, 0, 0);
		sleep(2000);
		this.wheels.driveOmni(0, 0, 0);
	}

	public void moveRightSide() {
		this.wheels.driveOmni(0.5f, 0, 0);
		sleep(2000);
		this.linearSlide.setPosition(LinearSlide.Position.SMALL_POLE);
		this.wheels.driveOmni(0, -0.5f, 0);
		sleep(1100);
		this.grabberThread.openClaw();
		sleep(1000);
		this.wheels.driveOmni(0, 0, 0);
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
