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
public class AutonomousLeft extends LinearOpMode {
    public static double DISTANCE = 48; // in
    private TrajectorySequence robotTrajectory;
    private Pose2d startPose = new Pose2d(-36, -61, Math.toRadians(90));
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

        Pose2d finalPose = vision.getLeftFinalPosition();

        waitForStart();

        while (!isStopRequested()) {

            drive.followTrajectorySequence(defaultTrajectory(finalPose));

        }
    }


    private TrajectorySequence defaultTrajectory(Pose2d finalPose) {

        TrajectorySequence defualtSeq = drive.trajectorySequenceBuilder(startPose)
                                             .addTemporalMarker(() -> lifter.high())
                                             .waitSeconds(1)
                                             .lineToSplineHeading(new Pose2d(-11, -61, Math.toRadians(0)))
                                             .strafeLeft(44)
                                             .forward(3)
                                             .addTemporalMarker(() -> grabber.drop())
                                             .waitSeconds(1)
                                             .back(3)
                                             .strafeLeft(11)
                                             .lineToLinearHeading(finalPose)
                                             .waitSeconds(15)
                                             .build();

        return defualtSeq;
    }

}





                                         /*
                                             .back(3)
                                             .strafeLeft(11)
                                             .turn(Math.toRadians(180))
                                             .addTemporalMarker(() -> lifter.cone())
                                             .waitSeconds(1.5)
                                             .forward(47)
                                             .addTemporalMarker(() -> grabber.grab())
                                             .waitSeconds(1)
                                             .addTemporalMarker(() -> lifter.mid())
                                             .waitSeconds(1)
                                             .back(45)
                                             //backing up for the middle pole drop
                                             .strafeLeft(14)
                                             .forward(2)
                                             .addTemporalMarker(() -> grabber.drop())
                                             .waitSeconds(30)
                                             .build();

                                          */


                                             /*


                                             .turn(Math.toRadians(-90))
                                             .forward(5)
                                             .addTemporalMarker(() -> grabber.drop())
                                             .back(5)
                                             .turn(Math.toRadians(90))
                                             .UNSTABLE_addTemporalMarkerOffset(-0.5, () -> lifter.cone())
                                             .lineToSplineHeading(new Pose2d(-12, -11, Math.toRadians(180)))
                                             .forward(50)
                                             .addTemporalMarker(() -> grabber.grab())
                                             .addTemporalMarker(() -> lifter.high())
                                             .back(75)
                                             .strafeLeft(12)
                                             .forward(5)
                                             .addTemporalMarker(() -> grabber.drop())
                                             .back(5)
                                             .strafeRight(12)
                                             .forward(75)
                                             .UNSTABLE_addTemporalMarkerOffset(-1.0, () -> lifter.cone())
                                             .addTemporalMarker(() -> grabber.grab())
                                             .addTemporalMarker(() -> lifter.high())
                                             .back(75)
                                             .strafeLeft(12)
                                             .forward(5)
                                             .addTemporalMarker(() -> grabber.drop())
                                             .back(5)
                                             .strafeRight(12)
                                             .forward(48)

 */



/*


        if (finalPose == null){
            robotTrajectory = scoreTrajectory();
        }

        else {
            robotTrajectory = defaultTrajectory(finalPose);
        }


    private TrajectorySequence scoreTrajectory() {
        TrajectorySequence defualtSeq = drive.trajectorySequenceBuilder(startPose)
                                             .waitSeconds(30)
                                             .build();

        return defualtSeq;

    }

 */