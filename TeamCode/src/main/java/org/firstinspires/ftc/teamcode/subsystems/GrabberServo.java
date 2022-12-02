package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

@SuppressWarnings({ "unused", "FieldCanBeLocal" })

public class GrabberServo {

    private final LinearOpMode robotOpMode;
    private Servo grabber;

   // private final double MAX_POS  = 0.0125;
   // private final double MIN_POS  = 0.0001;

    private final double MAX_POS  = 1.0;
    private final double MIN_POS  = 0.0;


    //max = 1.0
    // min = 0.0
    //private final double MAX_POS  = 700 - 501 / 2000;
    //private final double MIN_POS  = 700 - 500 / 2000;

    public GrabberServo(LinearOpMode opMode) {
        robotOpMode = opMode;
    }

    public void init() {
        grabber = robotOpMode.hardwareMap.get(Servo.class, "grabber");
        grabber.setDirection(Servo.Direction.FORWARD);
        grabber.scaleRange(0.0, 0.42);
        grabber.setPosition(0.0);
    }

    // 0.38

    public void drop() {
        grabber.setPosition(MAX_POS);
    }

    public double getPosition(){
        return grabber.getPosition();
    }

    public void grab() {
        grabber.setPosition(MIN_POS);
    }

}