package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;

import org.firstinspires.ftc.teamcode.subsystems.ArmPose;

public class HardwareConstants {
    public static final Direction[] driveDirs = {Direction.REVERSE, Direction.FORWARD, Direction.REVERSE, Direction.FORWARD};
    public static final double yawSens = 0.5;
    public static final double motionThreshold = 500; // millis

    // arm segment lengths
    public static final double len1 = 18, len2 = 18;

    // TODO fill out
    public static final ArmPose
            startPose = new ArmPose(30, 180),
            hookPose = new ArmPose(),
            highChamber = new ArmPose(),
            lowChamber = new ArmPose(),
            highBasket = new ArmPose(),
            lowBasket = new ArmPose();
}
