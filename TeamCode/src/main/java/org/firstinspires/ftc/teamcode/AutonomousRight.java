package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.drive.BotMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Grabber;
import org.firstinspires.ftc.teamcode.subsystems.Lifter;
import org.firstinspires.ftc.teamcode.subsystems.Vision;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;


@Config
@Autonomous(group = "drive")
public class AutonomousRight extends LinearOpMode {
    public static double DISTANCE = 48; // in
    private TrajectorySequence robotTrajectory;
    private Pose2d startPose = new Pose2d(36, -61, Math.toRadians(180));
    private BotMecanumDrive drive;
    private Lifter lifter;
    private Grabber grabber;
    private Vision vision;


    @Override
    public void runOpMode() throws InterruptedException {
        drive = new BotMecanumDrive(hardwareMap);
        lifter = new Lifter(this);
        grabber = new Grabber(this);
        vision = new Vision( this );

        lifter.init();
        grabber.init();
        vision.init();

        drive.setPoseEstimate(startPose);

        if (isStopRequested()) return;

        Pose2d finalPose = vision.getRightFinalPosition();

        waitForStart();

        while (!isStopRequested()) {

            drive.followTrajectorySequence(defaultTrajectory(finalPose));

        }
    }


    private TrajectorySequence defaultTrajectory(Pose2d finalPose) {

        TrajectorySequence defualtSeq = drive.trajectorySequenceBuilder(startPose)
                                             .addTemporalMarker(() -> lifter.high())
                                             .waitSeconds(1)
                                             .lineToSplineHeading(new Pose2d(11, -61, Math.toRadians(180)))
                                             .strafeRight(44)
                                             .forward(3)
                                             .addTemporalMarker(() -> grabber.drop())
                                             .waitSeconds(1)
                                             .back(3)
                                             .strafeRight(11)
                                             .lineToLinearHeading(finalPose)
                                             .waitSeconds(15)
                                             .build();

        return defualtSeq;
    }

}

