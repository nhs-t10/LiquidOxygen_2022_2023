

package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.teamcode.managers.CV.CVManager;
import org.firstinspires.ftc.teamcode.managers.movement.MovementManager;
import org.openftc.easyopencv.OpenCvWebcam;

@Autonomous
public class CVAuto extends LinearOpMode {

    DcMotor fl;//foward left
    DcMotor fr;//foward right
    DcMotor bl;//back left
    DcMotor br;//back right
    OpenCvWebcam wc;

    @Override
    public void runOpMode() throws InterruptedException {
        fl = hardwareMap.get(DcMotor.class, "fl");
        fr = hardwareMap.get(DcMotor.class, "fr");
        bl = hardwareMap.get(DcMotor.class, "bl");
        br = hardwareMap.get(DcMotor.class, "br");


        CVManager cv = new CVManager((hardwareMap));
        //cv.stream();

        MovementManager driver = new MovementManager(fl, fr, br, bl);

        super.waitForStart();

        if (cv.getCVResult()==0) {
            //middle region
            driver.driveBlue(.25f,.25f,.25f,.25f);
            sleep(1000);
            driver.driveBlue(0,0,0,0);
        } else if (cv.getCVResult()==1) {
            //left region
            driver.driveBlue(.25f,.25f,.25f,.25f);
            sleep(1000);
            driver.driveBlue(.25f,0,0,.25f);
            sleep(1000);
            driver.driveBlue(.25f,.25f,.25f,.25f);
            sleep(1000);
            driver.driveBlue(0,0,0,0);
        } else {
            //right region
            driver.driveBlue(.25f,.25f,.25f,.25f);
            sleep(1000);
            driver.driveBlue(0,.25f,.25f,0);
            sleep(1000);
            driver.driveBlue(.25f,.25f,.25f,.25f);
            sleep(1000);
            driver.driveBlue(0,0,0,0);
        }
    }


}

