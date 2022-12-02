package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.drive.BotMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Lifter;
import org.firstinspires.ftc.teamcode.subsystems.Vision;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

//import org.firstinspires.ftc.teamcode.subsystems.GrabberCRServo;
import org.firstinspires.ftc.teamcode.subsystems.GrabberServo;


@Config
@Autonomous(group = "drive")
public class AutonomousRight extends LinearOpMode {
    //private TrajectorySequence robotTrajectory;
    private final Pose2d startPose = new Pose2d(36, -61, Math.toRadians(90));
    private BotMecanumDrive drive;
    private Lifter lifter;
    private Vision vision;

    //private GrabberCRServo grabber;
    private GrabberServo grabber;



    @Override
    public void runOpMode() throws InterruptedException {
        drive = new BotMecanumDrive(hardwareMap);
        lifter = new Lifter(this);
        vision = new Vision( this );

        //grabber = new GrabberCRServo(this);
        grabber = new GrabberServo(this);


        lifter.init();
        grabber.init();
        vision.init();

        drive.setPoseEstimate(startPose);

        if (isStopRequested()) return;

        Pose2d finalPose = vision.detectFinalPosition("right");

        telemetry.addData("finalPositionX", finalPose.getX());
        telemetry.update();
        waitForStart();

        while (!isStopRequested()) {

            drive.followTrajectorySequence(defaultTrajectory(finalPose));

        }
    }

    private TrajectorySequence defaultTrajectory(Pose2d finalPose) {

        TrajectorySequence defualtSeq = drive
                .trajectorySequenceBuilder(startPose)
                .addTemporalMarker(() -> lifter.high())
                .waitSeconds(1)
                .lineToSplineHeading(new Pose2d(11, -61, Math.toRadians(180)))
                .strafeRight(43)
                .forward(3)
                .addTemporalMarker(() -> grabber.drop())
                .waitSeconds(1)
                .back(3)
                .strafeRight(11)
                .lineToLinearHeading(finalPose)
                .waitSeconds(20)
                .build();

        return defualtSeq;
    }

}

