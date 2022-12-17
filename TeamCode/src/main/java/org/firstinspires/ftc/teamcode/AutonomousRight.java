package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.drive.BotMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Lifter;
import org.firstinspires.ftc.teamcode.subsystems.Vision;

import org.firstinspires.ftc.teamcode.subsystems.GrabberServo;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

import java.util.ArrayList;


@Config
@Autonomous(group = "drive")
@SuppressWarnings({ "unused" })

public class AutonomousRight extends LinearOpMode {

    private BotMecanumDrive drive;
    private Lifter lifter;
    private GrabberServo grabber;
    // private ArrayList<Pose2d> finalPosePositions = new ArrayList<>();
    private ArrayList<Integer> finalStrafePositions = new ArrayList<>();

    @Override
    public void runOpMode () {

        drive = new BotMecanumDrive(hardwareMap);
        lifter = new Lifter(this);
        Vision vision = new Vision(this);
        grabber = new GrabberServo(this);

        lifter.init();
        grabber.init();
        vision.init();

        // default Right Pose2d positions

        // finalPosePositions.add(new Pose2d(12, -11, Math.toRadians(180)));   // Right Position 1
        // finalPosePositions.add(new Pose2d(36, -13, Math.toRadians(180)));   // Right Position 2
        // finalPosePositions.add(new Pose2d(55, -10, Math.toRadians(180)));   // Right Position 3

        // default Right Strafe positions

        finalStrafePositions.add(-11);   // Right Position 1
        finalStrafePositions.add(14);    // Right Position 2
        finalStrafePositions.add(39);    // Right Postionn 3

        if (isStopRequested()) return;

        waitForStart();

        // Pose2d finalPosition = vision.detectFinalPosePosition(finalPosePositions);

        int finalPosition = vision.detectFinalStrafePosition(finalStrafePositions);
        String detectedImage = vision.getDetectedImage();

        if (detectedImage == null) {
            detectedImage = "None: default";
        }

        while (!isStopRequested()) {

            telemetry.addData("Detected Image:", detectedImage);
            telemetry.addData("FinalPostion:", finalPosition);
            telemetry.update();

            drive.followTrajectorySequence(testTrajectory(finalPosition));
        }
    }

    private TrajectorySequence defaultTrajectory (Pose2d finalPose) {

        Pose2d startPose = new Pose2d(36, -61, Math.toRadians(90));

        drive.setPoseEstimate(startPose);

        return drive.trajectorySequenceBuilder(startPose)
                    .addTemporalMarker(() -> lifter.high())
                    .waitSeconds(.5)
                    .strafeLeft(28)
                    .turn(Math.toRadians(90))
                    .lineToSplineHeading(new Pose2d(12, -20, Math.toRadians(180)))
                    .forward(3)
                    .waitSeconds(1)
                    .addTemporalMarker(() -> lifter.downish())
                    .waitSeconds(.5)
                    .addTemporalMarker(() -> grabber.drop())
                    .waitSeconds(1)
                    .back(3)
                    .strafeRight(13)
                    .lineToLinearHeading(finalPose)
                    .addTemporalMarker(() -> lifter.ground())
                    .waitSeconds(25)
                    .build();

    }


    private TrajectorySequence testTrajectory (int finalPose) {

        Pose2d startPose = new Pose2d(36, -61.37, Math.toRadians(90));

        drive.setPoseEstimate(startPose);

        return drive.trajectorySequenceBuilder(startPose)
                    .addTemporalMarker(() -> lifter.high())
                    .forward(3)
                    .strafeLeft(25.5)
                    .turn(Math.toRadians(92))
                    .strafeRight(39)
                    .forward(3.75)
                    .waitSeconds(0.15)
                    .addTemporalMarker(() -> grabber.drop())
                    .addTemporalMarker(() -> lifter.cone())
                    .back(3.75)
                    .strafeRight(11.5)
                    .turn(Math.toRadians(178.75))
                    .forward(47.50)
                    .addTemporalMarker(() -> grabber.grab())
                    .addTemporalMarker(() -> lifter.high())
                    .waitSeconds(0.15)
                    .back(23.00)
                    .turn(Math.toRadians(90))
                    .strafeLeft(14)
                    .forward(3)
                    .waitSeconds(0.15)
                    .addTemporalMarker(() -> grabber.drop())
                    .back(3)
                    .addTemporalMarker(() -> lifter.ground())
                    .strafeRight(finalPose)
                    .waitSeconds(30)
                    .build();

    }



}
