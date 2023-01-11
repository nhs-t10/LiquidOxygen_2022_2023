

package liquidoxygen.autonomous;

import android.os.SystemClock;

import com.pocolifo.robobase.motor.CarWheels;
import com.pocolifo.robobase.vision.Webcam;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.openftc.easyopencv.OpenCvWebcam;

@Autonomous
public class PlanBAutoLeft extends LinearOpMode {
	private CarWheels wheels;
	private Webcam webcam;

	/**
	 * Waits a desired number of milliseconds
	 * Thread.sleep does not work, use this instead
	 * @param milliseconds Number of milliseconds to sleep
	 */
	public static void waitMs(long milliseconds) {
		SystemClock.sleep(milliseconds);
	}

	@Override
	public void runOpMode() throws InterruptedException {


		super.waitForStart();

		switch (webcam.getPipeline().getResult()) {

			case 0: { // RED
				// left region
				System.out.println("case 0, red, left region");
				wheels.driveIndividually(.25f, .25f, .25f, .25f);
				waitMs(700);
				wheels.driveIndividually(.25f, -.25f, -.25f, .25f);
				waitMs(2800);
				wheels.driveIndividually(0, 0, 0, 0);

				break;
			}


			case 1: {
				// GREEN
				//middle region
				System.out.println("case 1, green, middle region");
				wheels.driveIndividually(.25f, -.25f, -.25f, .25f);
				waitMs(2000L);
				wheels.driveIndividually(0, 0, 0, 0);
				break;
			}
			case 2: {
				System.out.println("case 2, blue, right region"); //BLUE
				//right region
				wheels.driveIndividually(.25f, -.25f, -.25f, .25f);
				waitMs(1800);
				wheels.driveIndividually(-.25f, -.25f, -.25f, -.25f);
				waitMs(1300);
				wheels.driveIndividually(.25f, -.25f, -.25f, .25f);
				waitMs(1800);

				wheels.driveIndividually(0, 0, 0, 0);
				break;

			}
		}

		try {
			webcam.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
