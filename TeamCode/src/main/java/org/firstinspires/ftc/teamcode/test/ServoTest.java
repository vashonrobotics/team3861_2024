package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@SuppressWarnings({ "unused", "FieldCanBeLocal" })
@Disabled

@TeleOp(group = "drive")
public class ServoTest extends LinearOpMode {

    CRServo grabber;

    @Override
    public void runOpMode() {

        grabber = hardwareMap.get(CRServo.class, "grabber");
        grabber.setDirection(DcMotorSimple.Direction.FORWARD);

        grabber.setPower(0);

        waitForStart();

        int loopCount = 0;

        while (!isStopRequested()) {

            if (gamepad1.x){
                grabber.setPower(0.045);
            }

            if (gamepad1.y) {
                grabber.setPower(0.5);
            }

            telemetry.addData("loopCount", loopCount);

            telemetry.addData("power", grabber.getPower());

            telemetry.update();

            loopCount++;

        }
    }
}

