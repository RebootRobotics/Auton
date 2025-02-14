package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepPushTest {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(700);

        Drive drive = new Drive(hardwareMap, beginPose, telemetry);

        double HPDeposit = -53;
        double spikeBack = -12;
        double waits = 0.2;
        double intakeWait = 0.3;

        TrajectoryCommand preload = drive.trajectoryBuilder(beginPose)
                .lineToY(-30)
                .build();

        TrajectoryCommand allSpikes = drive.trajectoryBuilder(preload.endPose())
                .addPoint(new Pose2d(18, -42, Math.toRadians(-90)))
                .addPoint(new Pose2d(36, spikeBack, Math.toRadians(-90)))

                .addPoint(new Pose2d(46, spikeBack, Math.toRadians(-90)))

                .lineToY(HPDeposit)

                .addPoint(new Pose2d(46, spikeBack, Math.toRadians(-90)))
                .addPoint(new Pose2d(55, spikeBack, Math.toRadians(-90)))

                .lineToY(HPDeposit)

                .addPoint(new Pose2d(55, spikeBack, Math.toRadians(-90)))
                .addPoint(new Pose2d(61, spikeBack, Math.toRadians(-90)))

                .lineToY(HPDeposit)
                .build();

        TrajectoryCommand intakeSpec2 = drive.trajectoryBuilder(allSpikes.endPose())
                .addPoint(new Pose2d(19, -48, Math.toRadians(-45)))
                .build();

        TrajectoryCommand depositSpec2 = drive.trajectoryBuilder(intakeSpec2.endPose())
                .addPoint(new Pose2d(3, -30, Math.toRadians(-92)))
                .build();

        TrajectoryCommand intakeSpec3 = drive.trajectoryBuilder(depositSpec2.endPose())
                .addPoint(new Pose2d(19, -48, Math.toRadians(-45)))
                .build();

        TrajectoryCommand depositSpec3 = drive.trajectoryBuilder(intakeSpec3.endPose())
                .addPoint(new Pose2d(0, -30, Math.toRadians(-92)))
                .build();

        TrajectoryCommand intakeSpec4 = drive.trajectoryBuilder(depositSpec3.endPose())
                .addPoint(new Pose2d(19, -48, Math.toRadians(-45)))
                .build();

        TrajectoryCommand depositSpec4 = drive.trajectoryBuilder(intakeSpec4.endPose())
                .addPoint(new Pose2d(-3, -30, Math.toRadians(-92)))
                .build();

        TrajectoryCommand intakeSpec5 = drive.trajectoryBuilder(depositSpec4.endPose())
                .addPoint(new Pose2d(19, -48, Math.toRadians(-45)))
                .build();

        TrajectoryCommand depositSpec5 = drive.trajectoryBuilder(intakeSpec5.endPose())
                .addPoint(new Pose2d(-6, -30, Math.toRadians(-92)))
                .build();


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}