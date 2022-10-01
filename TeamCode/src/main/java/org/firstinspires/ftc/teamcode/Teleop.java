package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


import org.firstinspires.ftc.teamcode.drive.BaseBotMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.DriveFactory;
import org.firstinspires.ftc.teamcode.subsystems.Carosel;
import org.firstinspires.ftc.teamcode.subsystems.Lifter;

// import com.qualcomm.robotcore.hardware.DcMotor;
//import org.firstinspires.ftc.teamcode.subsystems.Vision;


@TeleOp(name = "Teleop")
public class Teleop extends LinearOpMode {


    BaseBotMecanumDrive drive = null;

    boolean was_dpad_left = false;
    boolean was_dpad_right = false;

    private boolean was_leftTrigger;
    private boolean was_rightTrigger;

    @Override
    public void runOpMode() throws InterruptedException {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

       // Vision vision = new Vision(this, hardwareMap, telemetry);
       // vision.init();

        drive = DriveFactory.getDrive(hardwareMap);
        Carosel carousel = new Carosel(hardwareMap);
        Lifter lifter = new Lifter(hardwareMap);


        waitForStart();

        while (!isStopRequested()) {

            if (gamepad2.dpad_up) {
                carousel.PowerOn();
            }
            if (gamepad2.dpad_down) {
                carousel.PowerOff();
            }

            if (gamepad2.dpad_left && !was_dpad_left) {
                carousel.setDirection(DcMotorSimple.Direction.REVERSE);
            }
            was_dpad_left = gamepad1.dpad_left;

            if (gamepad2.dpad_right && !was_dpad_right) {
                carousel.setDirection(DcMotorSimple.Direction.FORWARD);
            }
            was_dpad_right = gamepad1.dpad_right;

            if (gamepad2.left_trigger > 0.1) {
                lifter.eat();
            } else if (gamepad2.right_trigger > 0.1) {
                lifter.barf(1);
            } else if (gamepad2.right_bumper) {
                lifter.barf(0.5);
            } else {
                lifter.intakeStop();
            }

            if (gamepad2.y) {
                lifter.armTopLayer();
            }
            if (gamepad2.x) {
                lifter.armMiddleLayer();
            }

            if (gamepad2.a){
                lifter.armBottomLayer();
            }

            if (gamepad2.b){
                lifter.armDown();
            }

            drive.setWeightedDrivePower(//turning and moving and strafing
                    new Pose2d(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x,
                            -gamepad1.right_stick_x
                    )
            );

            // telemetry.addData("Status", "Run Time: " + runtime.toString());
            //telemetry.addData("FrontMotors", "left (%.2f), right (%.2f)", leftFrontPower, rightFrontPower);
            //telemetry.addData("RearMotors", "left (%.2f), right (%.2f)", leftRearPower, rightRearPower);
            //telemetry.update();
        }
    }
}