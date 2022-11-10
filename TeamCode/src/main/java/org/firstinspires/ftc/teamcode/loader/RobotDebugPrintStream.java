package org.firstinspires.ftc.teamcode.loader;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.Arrays;


/**
 * An implementation of PrintStream which is used as the implementation of System.out and System.err.
 * The purpose of this is so that the use of System.out#println and other methods work properly. This
 * is <strong>much</strong> more friendly for developers who already know Java.
 *
 * @author youngermax
 * @see PrintStream
 * @version 1.0.0
 * @since 1.0.0
 */
public class RobotDebugPrintStream extends PrintStream {
    private final Telemetry telemetry;

    public RobotDebugPrintStream(Telemetry telemetry) {
        super(new VoidOutputStream());

        // Set up Telemetry instance to be more like standard Java
        this.telemetry = telemetry;

        this.telemetry.setCaptionValueSeparator("");
        this.telemetry.setItemSeparator("");
        this.telemetry.setMsTransmissionInterval(10);
        this.telemetry.setAutoClear(false);
        this.telemetry.update();
    }

    @Override
    public final void print(boolean b) {
        this.telemetry.addData("", b);
        this.telemetry.update();
    }

    @Override
    public final void print(char c) {
        this.telemetry.addData("", c);
        this.telemetry.update();
    }

    @Override
    public final void print(int i) {
        this.telemetry.addData("", i);
        this.telemetry.update();
    }

    @Override
    public final void print(long l) {
        this.telemetry.addData("", l);
        this.telemetry.update();
    }

    @Override
    public final void print(float f) {
        this.telemetry.addData("", f);
        this.telemetry.update();
    }

    @Override
    public final void print(double d) {
        this.telemetry.addData("", d);
        this.telemetry.update();
    }

    @Override
    public final void print(char[] s) {
        this.telemetry.addData("", s);
        this.telemetry.update();
    }

    @Override
    public final void print(String s) {
        this.telemetry.addData("", s);
        this.telemetry.update();
    }

    @Override
    public final void print(Object obj) {
        super.print(obj);
        this.telemetry.update();
    }

    /**
     * println Implementations
      */
    @Override
    public final void println() {
        this.telemetry.addLine();
        this.telemetry.update();
    }

    @Override
    public final void println(boolean x) {
        this.println(Boolean.toString(x));
    }

    @Override
    public final void println(char x) {
        this.println(Character.toString(x));
    }

    @Override
    public final void println(int x) {
        this.println(Integer.toString(x));
    }

    @Override
    public final void println(long x) {
        this.println(Long.toString(x));
    }

    @Override
    public final void println(float x) {
        this.println(Float.toString(x));
    }

    @Override
    public final void println(double x) {
        this.println(Double.toString(x));
    }

    @Override
    public final void println(char[] x) {
        this.println(Arrays.toString(x));
    }

    @Override
    public final void println(String x) {
        this.telemetry.addLine(x);
        this.telemetry.update();
    }

    @Override
    public final void println(Object x) {
        this.println(x.toString());
    }

    /**
     * flush Implementation
     */
    @Override
    public final void flush() {
        this.telemetry.update();
    }

    @Override
    public final PrintStream printf(String format, Object... args) {
        this.print(String.format(format, args)); // IntelliJ shows String.format as a redundant call, this is not true

        return this;
    }
}
