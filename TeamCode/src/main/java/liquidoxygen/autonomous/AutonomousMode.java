package liquidoxygen.autonomous;

import com.pocolifo.robobase.bootstrap.AutonomousOpMode;
import com.pocolifo.robobase.motor.CarWheels;
import com.pocolifo.robobase.motor.Wheel;
import com.pocolifo.robobase.vision.Webcam;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import liquidoxygen.Shared;

@Autonomous(name = Shared.AUTONOMOUS_NAME, group = Shared.GROUP, preselectTeleOp = Shared.TELEOP_NAME)
public class AutonomousMode extends AutonomousOpMode {
	private CarWheels wheels;
	private Webcam webcam;
	private Wheel liftMotor;
	private Servo grabberServo;

	@Override
	public void initialize() {
		for (int i = 0; 50 > i; i++) {
			System.out.print("PLEASE WAIT! ");
		}
		//this.liftMotor = new Wheel(this.hardwareMap.dcMotor.get("Lift"), 288, 5.5);
		//this.liftMotor.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		//this.grabberServo = this.hardwareMap.servo.get("Grabber");

		System.out.println();

		this.wheels = Shared.createWheels(this.hardwareMap);
		this.webcam = new Webcam(this.hardwareMap, "Webcam");
		this.webcam.open(new ColorCapturePipeline());

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
		this.wheels.drive(-66.04f, false);
		//this.liftMotor.drive(0.5);
		sleep(3000);
		//this.liftMotor.drive(0);
		this.wheels.drive(-12.7f,true);
		this.wheels.drive(10.16f,false);
		//this.grabberServo.setPosition(1);
		this.wheels.drive(-10.16f,false);
		this.wheels.drive(-25.4f,true);
	}

	public void moveMiddle() {
		this.wheels.drive(-75,false);
	}

	public void moveRightSide() {
		this.wheels.drive(-66.04f, false);
		//this.liftMotor.drive(0.5);
		sleep(3000);
		//this.liftMotor.drive(0);
		this.wheels.drive(12.7f,true);
		this.wheels.drive(10.16f,false);
		//this.grabberServo.setPosition(1);
		this.wheels.drive(-10.16f,false);
		this.wheels.drive(25.4f,true);
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
