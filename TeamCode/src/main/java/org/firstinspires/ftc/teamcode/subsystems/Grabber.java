package org.firstinspires.ftc.teamcode.subsystems;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

@SuppressWarnings({ "unused", "FieldCanBeLocal" })

public class Grabber {

    private final LinearOpMode robotOpMode;
    private Servo grabber;

    private final double MAX_POS  = 0.0125;
    private final double MIN_POS  = 0.0005;

    public Grabber(LinearOpMode opMode) {
        robotOpMode = opMode;
    }

    public void init() {
        grabber = robotOpMode.hardwareMap.get(Servo.class, "grabber");
        grabber.setDirection(Servo.Direction.FORWARD);
        grabber.setPosition(MIN_POS);
    }

    public double getPosition() {
        return grabber.getPosition();
    }

    public void drop() {
        grabber.setPosition(MAX_POS);
    }

    public void grab() {
        grabber.setPosition(MIN_POS);
    }

    public void reset() {
        this.drop();
    }

}