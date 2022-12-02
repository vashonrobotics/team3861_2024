package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.GrabberServo;


@SuppressWarnings({ "unused", "FieldCanBeLocal" })
@Disabled

@TeleOp(group = "drive")
public class TeleopGrabber extends LinearOpMode {

    @Override
    public void runOpMode() {

        GrabberServo grabber = new GrabberServo(this);

        grabber.init();

        waitForStart();

        while (!isStopRequested()) {

            if (gamepad2.right_bumper) {
                grabber.drop();
            }

            if (gamepad2.left_bumper) {
                grabber.grab();
            }

            telemetry.addData("grabber", grabber.getPosition());
            telemetry.update();

        }
    }
}



