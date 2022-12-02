package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.drive.BotMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Lifter;

import org.firstinspires.ftc.teamcode.subsystems.GrabberCRServo;
import org.firstinspires.ftc.teamcode.subsystems.GrabberServo;


@SuppressWarnings({ "unused", "FieldCanBeLocal" })

@TeleOp(group = "drive")
public class Teleop extends LinearOpMode {

    @Override
    public void runOpMode() {

        BotMecanumDrive drive = new BotMecanumDrive(hardwareMap);
        Lifter lifter = new Lifter(this);

        //GrabberCRServo grabber = new GrabberCRServo(this);
        GrabberServo grabber = new GrabberServo(this);

        lifter.init();
        grabber.init();

        waitForStart();

        while (!isStopRequested()) {

            drive.setWeightedDrivePower(
                    new Pose2d(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x,
                            -gamepad1.right_stick_x
                    )
            );

            drive.update();

            if (gamepad2.y) {
                lifter.high();
            }

            if (gamepad2.x) {
                lifter.mid();
            }

            if (gamepad2.a) {
                lifter.low();
            }

            if (gamepad2.b) {
                lifter.ground();
            }

            if (gamepad2.right_bumper) {
                grabber.drop();
            }

            if (gamepad2.left_bumper) {
                grabber.grab();
            }

            if (gamepad2.dpad_up) {
                lifter.upALittle();
            }

            if (gamepad2.dpad_down) {
                lifter.downALittle();
            }

            Pose2d poseEstimate = drive.getPoseEstimate();
            telemetry.addData("x", poseEstimate.getX());
            telemetry.addData("y", poseEstimate.getY());
            telemetry.addData("heading", poseEstimate.getHeading());
            telemetry.addData("lifter", lifter.getPosition());
            telemetry.addData("grabber", grabber.getPosition());
            telemetry.update();

        }
    }
}



