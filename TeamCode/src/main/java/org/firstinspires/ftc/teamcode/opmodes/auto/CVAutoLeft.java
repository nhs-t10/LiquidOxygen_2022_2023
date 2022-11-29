

package org.firstinspires.ftc.teamcode.opmodes.auto;

import android.os.SystemClock;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.loader.RobotDebugPrintStream;
import org.firstinspires.ftc.teamcode.managers.CV.CVManager;
import org.firstinspires.ftc.teamcode.managers.movement.MovementManager;
import org.openftc.easyopencv.OpenCvWebcam;

@Autonomous
public class CVAutoLeft extends LinearOpMode {

    DcMotor fl;//foward left
    DcMotor fr;//foward right
    DcMotor bl;//back left
    DcMotor br;//back right
    OpenCvWebcam wc;

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
        System.setOut(new RobotDebugPrintStream(this.telemetry));
        System.setErr(new RobotDebugPrintStream(this.telemetry));

        fl = hardwareMap.get(DcMotor.class, "fl");
        fr = hardwareMap.get(DcMotor.class, "fr");
        bl = hardwareMap.get(DcMotor.class, "bl");
        br = hardwareMap.get(DcMotor.class, "br");

        CVManager cv = new CVManager((hardwareMap));

        MovementManager driver = new MovementManager(fl, fr, br, bl);

        super.waitForStart();

        switch(cv.getCVResult()) {

            case 0: { // RED
                // left region
                System.out.println("case 0, red, left region");
                driver.driveBlue(.25f, .25f, .25f, .25f);
                waitMs(600);
                driver.driveBlue(.5f, -.5f, -.5f, .5f);
                waitMs(1000);
                driver.driveBlue(0, 0, 0, 0);

                break;
            }
            case 1: {

                // GREEN
                //middle region
                System.out.println("case 1, green, middle region");
                driver.driveBlue(.5f, -.5f, -.5f, .5f);
                waitMs(1000L);
                driver.driveBlue(0, 0, 0, 0);
                break;
            }
            case 2: {
                System.out.println("case 2, blue, right region"); //BLUE
                //right region
                driver.driveBlue(-.25f, -.25f, -.25f, -.25f);
                waitMs(1400);
                driver.driveBlue(.5f, -.5f, -.5f, .5f);
                waitMs(800);
                driver.driveBlue(.25f, .25f, .25f, .25f);
                waitMs(1400);
                driver.driveBlue(.5f, -.5f, -.5f, .5f);
                waitMs(1000L);

                driver.driveBlue(0, 0, 0, 0);
                break;

            }
        }
    }
}

