package org.firstinspires.ftc.teamcode.opmodes.teleop;

import static org.firstinspires.ftc.teamcode.managers.manipulation.ManipulationManager.crservo;
import static org.firstinspires.ftc.teamcode.managers.manipulation.ManipulationManager.motor;
import static org.firstinspires.ftc.teamcode.managers.manipulation.ManipulationManager.servo;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.managers.input.InputManager;
import org.firstinspires.ftc.teamcode.managers.input.nodes.ButtonNode;
import org.firstinspires.ftc.teamcode.managers.input.nodes.JoystickNode;
import org.firstinspires.ftc.teamcode.managers.manipulation.ManipulationManager;
import org.firstinspires.ftc.teamcode.managers.movement.MovementManager;


@TeleOp
public class basicTeleop extends OpMode {
    public MovementManager driver;
    public InputManager input;
    public ManipulationManager hands;
    public double i;
    @Override
    public void init() {
        DcMotor fl = hardwareMap.get(DcMotor.class, "fl");
        DcMotor fr = hardwareMap.get(DcMotor.class, "fr");
        DcMotor br = hardwareMap.get(DcMotor.class, "br");
        DcMotor bl = hardwareMap.get(DcMotor.class, "bl");

        hands = new ManipulationManager(
                hardwareMap,
                crservo         (),
                servo           ("leftArm", "rightArm"),
                motor           ()
        );

        driver = new MovementManager(fl, fr, br, bl);

        input = new InputManager(gamepad1, gamepad2);

        input.registerInput("leftstickx", new JoystickNode("leftstickx"));
        input.registerInput("leftsticky", new JoystickNode("leftsticky"));
        input.registerInput("rightstickx", new JoystickNode("rightstickx"));
        input.registerInput("rightsticky", new JoystickNode("rightsticky"));
        input.registerInput("dpadup", new ButtonNode("dpadup"));
        input.registerInput("dpaddown", new ButtonNode("dpaddown"));
        input.registerInput("dpadleft", new ButtonNode("dpadleft"));
        input.registerInput("dpadright", new ButtonNode("dpadright"));
        input.registerInput("a", new ButtonNode("a"));
        input.registerInput("b", new ButtonNode("b"));
        input.registerInput("x", new ButtonNode("x"));
        input.registerInput("y", new ButtonNode("y"));
        i = 0.5;
    }
    public float squareWSign(float input) {
        if (input>=0) {
            return input * input;
        } else {
            return input * input * -1;
        }
    }
    @Override
    public void loop() {
        hands.setServoPosition("leftArm", (input.getFloat("rightsticky")+1)/2);
        hands.setServoPosition("rightArm", Math.abs((input.getFloat("rightsticky")+1)/2-1));
        driver.driveBlue(-(float)(i * squareWSign(input.getFloat("leftsticky")) + squareWSign(input.getFloat("leftstickx"))),
                (float)(i * squareWSign(input.getFloat("leftsticky")) - squareWSign(input.getFloat("leftstickx"))),
                (float)(i * squareWSign(input.getFloat("leftsticky")) - squareWSign(input.getFloat("leftstickx"))),
                -(float)(i * squareWSign(input.getFloat("leftsticky")) + squareWSign(input.getFloat("leftstickx"))));
        if (input.getBool("dpadup")) {
            driver.driveBlue(-(float)i,(float)i,(float)i,-(float)i);
        } else if (input.getBool("dpaddown")) {
            driver.driveBlue((float)i,-(float)i,-(float)i,(float)i);
        } else if (input.getBool("x")) {
            driver.driveBlue((float)i,(float)i,(float)i,(float)i);
        } else if (input.getBool("b")) {
            driver.driveBlue(-(float)i,-(float)i,-(float)i,-(float)i);
        } else if (input.getBool("dpadleft")) {
            driver.driveBlue((float)i, (float)i, -(float)i, -(float)i);
        } else if (input.getBool("dpadright"))  {
            driver.driveBlue(-(float)i, -(float)i, (float)i, (float)i);
        } else {
            driver.driveBlue(0,0,0,0);
    }

        if (input.getBool("y") & i<1) {
            i += 0.001;
            telemetry.addData("Speed", i);
            telemetry.update();
        } else if (input.getBool("a") & i>0) {
            i -= 0.001;
            telemetry.addData("Speed", i);
            telemetry.update();
        }

    }
}
