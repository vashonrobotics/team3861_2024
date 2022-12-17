package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.drive.BotMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Lifter;
import org.firstinspires.ftc.teamcode.subsystems.Vision;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.subsystems.GrabberServo;

import java.util.ArrayList;

@Config
@Autonomous(group = "drive")
@SuppressWarnings({ "unused" })

public class AutonomousLeft extends LinearOpMode {

    private BotMecanumDrive drive;
    private Lifter lifter;
    private GrabberServo grabber;
    // private ArrayList<Pose2d> finalPosePositions = new ArrayList<>();
    private ArrayList<Integer> finalStrafePositions = new ArrayList<>();


    @Override
    public void runOpMode() {

        drive = new BotMecanumDrive(hardwareMap);
        lifter = new Lifter(this);
        Vision vision = new Vision(this);
        grabber = new GrabberServo(this);

        lifter.init();
        grabber.init();
        vision.init();

        // default Left Pose2d positions

        // finalPosePositions.add(new Pose2d(-57, -11, Math.toRadians(0)));   // Left Position 1
        // finalPosePositions.add(new Pose2d(-36, -11, Math.toRadians(0)));   // Left Position 2
        // finalPosePositions.add(new Pose2d(-12, -11, Math.toRadians(0)));   // Left Postiion 3

        // default Left Strafe positions
//40
        finalStrafePositions.add(39);   // Left Position 1
        finalStrafePositions.add(14);   // Left Position 2
        finalStrafePositions.add(-11);  // Left Postionn 3

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


    private TrajectorySequence defaultTrajectory(Pose2d finalPose) {

        Pose2d startPose = new Pose2d(-36, -61, Math.toRadians(90));

        drive.setPoseEstimate(startPose);

        return drive.trajectorySequenceBuilder(startPose)
                    .addTemporalMarker(() -> lifter.high())
                    .waitSeconds(5)
                    .lineToSplineHeading(new Pose2d(-11, -61, Math.toRadians(0)))
                    .strafeLeft(43)
                    .forward(2.5)
                    .waitSeconds(1)
                    .addTemporalMarker(() -> lifter.downish())
                    .waitSeconds(.5)
                    .addTemporalMarker(() -> grabber.drop())
                    .waitSeconds(1)
                    .back(2.5)
                    .strafeLeft(14)
                    .lineToLinearHeading(finalPose)
                    .addTemporalMarker(() -> lifter.ground())
                    .waitSeconds(20)
                    .build();

    }


    private TrajectorySequence testTrajectory(int finalPose) {

        Pose2d startPose = new Pose2d(-36, -61, Math.toRadians(90));

        drive.setPoseEstimate(startPose);

        return drive.trajectorySequenceBuilder(startPose)
                    .addTemporalMarker(() -> lifter.high())
                    .forward(3)
                    .strafeRight(25)
                    .turn(Math.toRadians(-90))
                    .strafeLeft(38.5)
                    .forward(2)
                    .waitSeconds(0.15)
                    .addTemporalMarker(() -> grabber.drop())
                    .addTemporalMarker(() -> lifter.cone())
                    .back(2)
                    .strafeLeft(12.25)
                    .turn(Math.toRadians(180))
                    .forward(49.75)
                    .addTemporalMarker(() -> grabber.grab())
                    .addTemporalMarker(() -> lifter.high())
                    .waitSeconds(0.5)
                    .back(23.00)
                    .turn(Math.toRadians(-90.50))
                    .strafeRight(14.75)
                    .forward(2.75)
                    .waitSeconds(0.15)
                    .addTemporalMarker(() -> grabber.drop())
                    .addTemporalMarker(() -> lifter.ground())
                    .back(2.75)
                    .strafeLeft(finalPose)
                    .waitSeconds(30)
                    .build();

    }

}

