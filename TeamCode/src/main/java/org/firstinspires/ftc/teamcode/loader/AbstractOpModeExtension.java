package org.firstinspires.ftc.teamcode.loader;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;

public abstract class AbstractOpModeExtension extends OpMode {
    protected AbstractOpModeExtension() {}

    @Override
    public void init() {
        System.setOut(new RobotDebugPrintStream(this.telemetry));
        System.setErr(new RobotDebugPrintStream(this.telemetry));

        this.dumpEnvironment();
    }

    private void dumpEnvironment() {
        System.out.println("========== Environment: detected devices ==========");

        for (HardwareMap.DeviceMapping<? extends HardwareDevice> deviceMapping : this.hardwareMap.allDeviceMappings) {
            System.out.printf("+ %s devices%n", deviceMapping.getDeviceTypeClass().getName());

            for (HardwareDevice hardwareDevice : deviceMapping) {
                System.out.printf("  - %s%n", hardwareDevice.getDeviceName());
                System.out.printf("     - Manufacturer: %s%n", hardwareDevice.getManufacturer());
                System.out.printf("     - Version: %s%n", hardwareDevice.getVersion());
                System.out.printf("     - Connection info: %s%n", hardwareDevice.getConnectionInfo());
            }
        }
    }
}
