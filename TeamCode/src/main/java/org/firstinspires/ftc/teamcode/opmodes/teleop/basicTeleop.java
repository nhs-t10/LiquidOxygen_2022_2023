package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.auxilary.FileSaver;
import org.firstinspires.ftc.teamcode.managers.feature.FeatureManager;
import org.firstinspires.ftc.teamcode.managers.feature.robotconfiguration.RobotConfiguration;
import org.firstinspires.ftc.teamcode.managers.input.InputManager;
import org.firstinspires.ftc.teamcode.managers.input.nodes.ButtonNode;
import org.firstinspires.ftc.teamcode.managers.input.nodes.IfNode;
import org.firstinspires.ftc.teamcode.managers.input.nodes.JoystickNode;
import org.firstinspires.ftc.teamcode.managers.input.nodes.MultiInputNode;
import org.firstinspires.ftc.teamcode.managers.input.nodes.MultiplyNode;
import org.firstinspires.ftc.teamcode.managers.input.nodes.StaticValueNode;
import org.firstinspires.ftc.teamcode.managers.input.nodes.ToggleNode;
import org.firstinspires.ftc.teamcode.managers.movement.MovementManager;


@TeleOp
public class basicTeleop extends OpMode {
    public MovementManager driver;
    public InputManager input;
    public int i;
    @Override
    public void init() {
        DcMotor fl = hardwareMap.get(DcMotor.class, "fl");
        DcMotor fr = hardwareMap.get(DcMotor.class, "fr");
        DcMotor br = hardwareMap.get(DcMotor.class, "br");
        DcMotor bl = hardwareMap.get(DcMotor.class, "bl");
        driver = new MovementManager(fl, fr, br, bl);

        input = new InputManager(gamepad1, gamepad2);

        input.registerInput("leftstickx", new JoystickNode("leftstickx"));
        input.registerInput("leftsticky", new JoystickNode("leftsticky"));
        input.registerInput("rightstickx", new JoystickNode("rightstickx"));
        input.registerInput("dpadup", new ButtonNode("dpadup"));
        input.registerInput("dpaddown", new ButtonNode("dpaddown"));
        input.registerInput("dpadleft", new ButtonNode("dpadleft"));
        input.registerInput("dpadright", new ButtonNode("dpadright"));
        int i = 1;
    }

    @Override
    public void loop() {
        if (input.getBool("dpadup")) {
            driver.driveOmni(1,0,0);
        } else if (input.getBool("dpaddown")) {
            driver.driveOmni(-1,0,0);
        } else if (input.getBool("dpadleft")) {
            driver.driveOmni(0,-1,0);
        } else if (input.getBool("dpaddown")) {
            driver.driveOmni(0,1,0);
        }
    }
}
