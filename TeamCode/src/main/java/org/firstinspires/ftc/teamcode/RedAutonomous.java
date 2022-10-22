package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.drive.BaseBotMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.DriveFactory;
import org.firstinspires.ftc.teamcode.subsystems.Carosel;
import org.firstinspires.ftc.teamcode.subsystems.Vision;

@Autonomous(name = "RedAutonomous")
public class RedAutonomous extends LinearOpMode {

    Vision vision = null;
    Carosel carousel = null;
    BaseBotMecanumDrive drive = null;

    @Override
    public void runOpMode() throws InterruptedException {

        vision = new Vision(this, hardwareMap, telemetry);

        drive = DriveFactory.getDrive(hardwareMap);
        // drive.setVisionLocalizer(vision.getVuforia());

        carousel = new Carosel(hardwareMap);
        carousel.setDirection(DcMotorSimple.Direction.FORWARD);

        waitForStart();

        sleep(3000);
        drive.setWeightedDrivePower(new Pose2d(0, 1, 0));
        sleep(100);
        drive.setWeightedDrivePower(new Pose2d(-0.5, 0, 0.01));
        sleep(3800); // Change this time
        // drive.setWeightedDrivePower(new Pose2d(0,0.05,-0.2));
        // sleep(300);
        drive.setWeightedDrivePower(new Pose2d(0,0,0));

        carousel.PowerOn();
        sleep(4000);
        carousel.PowerOff();

        // drive.setWeightedDrivePower(new Pose2d(0,-0.05,0.2));
        // sleep(450);
        drive.setWeightedDrivePower(new Pose2d(1, -0.05, -0.025));
        sleep(3400);
        drive.setWeightedDrivePower(new Pose2d(0,0,0));
    }
}