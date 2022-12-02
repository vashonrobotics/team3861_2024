package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;

@SuppressWarnings({ "unused", "FieldCanBeLocal" })

public class GrabberCRServo {

    private final LinearOpMode robotOpMode;
    private CRServo grabber;

    private final double MAX_POW  = 0.90;
    private final double MIN_POW  = 0.05;

    public GrabberCRServo(LinearOpMode opMode) {
        robotOpMode = opMode;
    }

    public void init() {
        grabber = robotOpMode.hardwareMap.get(CRServo.class, "grabber");
        grabber.setDirection(CRServo.Direction.REVERSE);
        grabber.setPower(.7);
    }

    public void drop() {
        grabber.setPower(MAX_POW);
    }

    public void grab() {
        grabber.setPower(MIN_POW);
    }

}