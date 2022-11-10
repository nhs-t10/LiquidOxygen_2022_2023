package org.firstinspires.ftc.teamcode.loader;

import java.io.IOException;
import java.io.OutputStream;

public class VoidOutputStream extends OutputStream {
    public VoidOutputStream() {}

    @Override
    public void write(int i) throws IOException {}
}
