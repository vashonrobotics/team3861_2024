package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;

public class HardwareConstants {
    public static final Direction[] driveDirs = {Direction.REVERSE, Direction.FORWARD, Direction.REVERSE, Direction.FORWARD};
    public static final double yawSens = 0.5;
    public static final double motionThreshold = 500; // millis
}
