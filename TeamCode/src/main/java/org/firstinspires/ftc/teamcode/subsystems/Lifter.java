package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
//import com.qualcomm.robotcore.hardware.DcMotorControllerEx;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
//import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@SuppressWarnings("unused")
public class Lifter {

    //public double Power = 1.0;

    private final DcMotor lifterMotor;
    private final DcMotor intakeMotor;

    // private int motorPosition = 500;

    private final int BOTTOM_LAYER = 200;
    private final int MIDDLE_LAYER = 480;
    private final int TOP_LAYER = 800;

    private final double SLAP_POSITION = 1.0;
    private final double HOME_POSITION = 0.0;
    double currentPosition;

    // long timeSinceCalled;

    public Lifter(HardwareMap hardwareMap) {
        lifterMotor = hardwareMap.get(DcMotorEx.class, "lifterMotor");

        lifterMotor.setDirection(DcMotor.Direction.FORWARD);
        // lifterMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lifterMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        intakeMotor.setDirection(DcMotor.Direction.REVERSE);
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    public void barf(double power) {
        intakeMotor.setPower(-power);
    }

    public void intakeStop() {
        intakeMotor.setPower(0);
    }

    public void eat() {
        intakeMotor.setPower(1);
    }

    /*
    public void incrementLiftUp() {
        motorPosition += 20;
    }
    public void incrementLiftDown() {
        motorPosition -= 20;
    }

    public void armUp() {
        lifterMotor.setTargetPosition(motorPosition);
        lifterMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lifterMotor.setPower(1);
    } */

    public void armTopLayer() {
        lifterMotor.setTargetPosition(TOP_LAYER);
        lifterMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lifterMotor.setPower(1);
    }

    public void armMiddleLayer() {
        lifterMotor.setTargetPosition(MIDDLE_LAYER);
        lifterMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lifterMotor.setPower(1);
    }

    public void armBottomLayer() {
        lifterMotor.setTargetPosition(BOTTOM_LAYER);
        lifterMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lifterMotor.setPower(1);
    }

    public void armDown() {
        lifterMotor.setTargetPosition(0);
        lifterMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lifterMotor.setPower(1);
    }
}

