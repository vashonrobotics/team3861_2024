package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.HardwareConstants;

public class ArticulatedArm {
    private DcMotor m1, m2; // starting from base
    private final double dpc1 = 2, dpc2 = 2; // encoder degrees per count
    public ArmPose zeroPose; // pose where encoders register zero; this should always be starting pose

    public ArticulatedArm(DcMotor m1, DcMotor m2) {
        this.m1 = m1;
        this.m2 = m2;
        zeroPose = HardwareConstants.startPose;
        m1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        m2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        m1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        m2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public ArmPose getPose() {
        return new ArmPose(m1.getCurrentPosition() * dpc1 + zeroPose.a1, m2.getCurrentPosition() * dpc2 + zeroPose.a2);
    }

    // this is not called setPose() because I was listening to Madonna when I wrote this
    public void strikePose(ArmPose pose) {
        m1.setTargetPosition((int)(pose.a1 / dpc1 - zeroPose.a1));
        m2.setTargetPosition((int)(pose.a2 / dpc2 - zeroPose.a2));
        m1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        m2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        m1.setPower(1);
        m2.setPower(1);
    }

    public void strikePose(double a1, double a2) {
        strikePose(new ArmPose(a1, a2));
    }
}
