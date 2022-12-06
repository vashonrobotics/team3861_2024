package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");
        MeepMeep meepMeep = new MeepMeep(800);

        Pose2d startRightPose = new Pose2d(36, -61, Math.toRadians(90));

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 38.110287416570166, Math.toRadians(180), Math.toRadians(140.87475), 15.36)
                .followTrajectorySequence(drive -> drive
                        .trajectorySequenceBuilder(startRightPose)
                        //.addTemporalMarker(() -> lifter.high())
                        .waitSeconds(1)
                        .forward(1)
                        .strafeLeft(24)
                        .lineToSplineHeading(new Pose2d(12, -24, Math.toRadians(180)))
                        .forward(2)
                        .waitSeconds(.5)
                        //.addTemporalMarker(() -> lifter.downish())
                        //.addTemporalMarker(() -> grabber.drop())
                        .waitSeconds(.5)
                        .back(2)
                        .strafeRight(12)
                        //.lineToLinearHeading(finalPose)
                        //.addTemporalMarker(() -> lifter.ground())
                        .waitSeconds(20)
                        .build()
                );


        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}



