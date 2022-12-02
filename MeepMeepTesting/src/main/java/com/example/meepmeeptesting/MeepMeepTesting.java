package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)

                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 38.110287416570166, Math.toRadians(180), Math.toRadians(140.87475), 15.36)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(36, -61, Math.toRadians(90)))
                             .lineToSplineHeading(new Pose2d(11, -61, Math.toRadians(180)))
                             .strafeRight(37)
                             .forward(3)
                             .waitSeconds(1)
                             .back(3)
                             .strafeRight(12)
                             .waitSeconds(4)
                             .build()
                );

                             /*
                                                             .strafeRight(24)
                                                             .forward(37)
                                                             .turn(Math.toRadians(-90))
                                                             //.UNSTABLE_addTemporalMarkerOffset(-0.3, () -> lifter.high())
                                                             .forward(5)
                                                             //.addTemporalMarker(() -> grabber.drop())
                                                             .back(5)
                                                             .turn(Math.toRadians(90))
                                                             //.UNSTABLE_addTemporalMarkerOffset(-0.5, () -> lifter.cone())
                                                             .lineToSplineHeading(new Pose2d(-12, -11, Math.toRadians(180)))
                                                             .forward(50)
                                                             //.addTemporalMarker(() -> grabber.grab())
                                                             //.addTemporalMarker(() -> lifter.high())
                                                             .back(75)
                                                             .strafeLeft(12)
                                                             .forward(5)
                                                             //.addTemporalMarker(() -> grabber.drop())
                                                             .back(5)
                                                             .strafeRight(12)
                                                             .forward(75)
                                                             //.UNSTABLE_addTemporalMarkerOffset(-1.0, () -> lifter.cone())
                                                             //.addTemporalMarker(() -> grabber.grab())
                                                             //.addTemporalMarker(() -> lifter.high())
                                                             .back(75)
                                                             .strafeLeft(12)
                                                             .forward(5)
                                                             //.addTemporalMarker(() -> grabber.drop())
                                                             .back(5)
                                                             .strafeRight(12)
                                                             .forward(48)

                              */




        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}