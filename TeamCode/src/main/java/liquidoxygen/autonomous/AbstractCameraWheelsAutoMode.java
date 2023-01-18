package liquidoxygen.autonomous;

import com.pocolifo.robobase.bootstrap.AutonomousOpMode;
import com.pocolifo.robobase.motor.CarWheels;
import com.pocolifo.robobase.vision.Webcam;
import liquidoxygen.Shared;
import lombok.SneakyThrows;

public abstract class AbstractCameraWheelsAutoMode extends AutonomousOpMode {
	protected CarWheels wheels;
	protected Webcam webcam;

	@Override
	public void initialize() {
		for (int i = 0; 50 > i; i++) {
			System.out.print("PLEASE WAIT! ");
		}

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

	@Override
	public void stop() {
		try {
			this.wheels.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public abstract void moveLeftSide();
	public abstract void moveMiddle();
	public abstract void moveRightSide();
}
