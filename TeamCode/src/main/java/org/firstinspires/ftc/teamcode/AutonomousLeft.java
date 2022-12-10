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

@Config
@Autonomous(group = "drive")
@SuppressWarnings({ "unused" })

public class AutonomousLeft extends LinearOpMode {

    private BotMecanumDrive drive;
    private Lifter lifter;
    private GrabberServo grabber;

    @Override
    public void runOpMode() {

        drive = new BotMecanumDrive(hardwareMap);
        lifter = new Lifter(this);
        Vision vision = new Vision(this);
        grabber = new GrabberServo(this);

        lifter.init();
        grabber.init();
        vision.init();

        if (isStopRequested()) return;

        waitForStart();

        Pose2d finalPosition = vision.detectFinalPosition("left");
        String detectedImage = vision.getDetectedImage();

        while (!isStopRequested()) {
            telemetry.addData("Detected Image:", detectedImage);
            telemetry.addData("FinalPostion:", finalPosition);
            telemetry.update();

            drive.followTrajectorySequence(defaultTrajectory(finalPosition));
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

}

