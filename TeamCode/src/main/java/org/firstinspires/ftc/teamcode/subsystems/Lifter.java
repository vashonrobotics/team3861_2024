package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@SuppressWarnings({ "unused", "FieldCanBeLocal"})

public class Lifter {

    private final LinearOpMode robotOpMode;
    private DcMotor lifter;

    //cone is 5 cones stacked tall

    private final int   CONE  = 205;
    private final int   HIGH  = 1570;
    private final int    MID  = 1140;
    private final int    LOW  = 670;
    private final int GROUND  = 0;

    // mid 1245
    // cone 145


    public Lifter(LinearOpMode opMode) {
        robotOpMode = opMode;
    }

    public void init() {
        lifter = robotOpMode.hardwareMap.get(DcMotorEx.class, "lifter");
        lifter.setDirection(DcMotor.Direction.REVERSE);
        lifter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lifter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public int getPosition() {
        return lifter.getCurrentPosition();
    }

    private void setPosition(int position){
        lifter.setTargetPosition(position);
        lifter.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lifter.setPower(1);
    }

    public void high() {
        this.setPosition(HIGH);
    }

    public void mid() {
        this.setPosition(MID);
    }

    public void low() {
        this.setPosition(LOW);
    }

    public void ground() {
        this.setPosition(GROUND);
    }

    public void cone() {
        this.setPosition(CONE);
    }

}