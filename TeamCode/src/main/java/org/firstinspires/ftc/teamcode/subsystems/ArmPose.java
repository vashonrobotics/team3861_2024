package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.teamcode.HardwareConstants;

public class ArmPose {
    public double a1, a2; // a1 = seg 1 angle from base, a2 = seg 2 angle from seg 1

    public ArmPose(double a1, double a2) {
        this.a1 = a1;
        this.a2 = a2;
    }

    public ArmPose() {
        this(0, 0);
    }

    // assuming resting orientation of seg 1 is +
    public double getLateralExtension() {
        return Math.cos(Math.toRadians(a1)) * HardwareConstants.len1 + Math.cos(Math.toRadians(a1 + a2)) * HardwareConstants.len2;
    }
}
