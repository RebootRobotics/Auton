package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TrajectoryBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
import com.acmerobotics.roadrunner.Action;

public class MeepMeepPushDeposit {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(1000);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        Pose2d initialPose = new Pose2d(-10, 60, Math.toRadians(90));

        Action trajectory0 = myBot.getDrive().actionBuilder(initialPose)
                .lineToY(31)
                .build();

        Action trajectory01 = myBot.getDrive().actionBuilder(new Pose2d(-10, 31, Math.toRadians(90)))
                .lineToYSplineHeading(45, Math.toRadians(-90))
                .build();

        Action trajectory1 = myBot.getDrive().actionBuilder(new Pose2d(-10, 45, Math.toRadians(-90)))
                .strafeTo(new Vector2d(-37,38))
                .strafeTo(new Vector2d(-37,10))
                .strafeTo(new Vector2d(-46,10))
                .strafeTo(new Vector2d(-46,55))
                .strafeTo(new Vector2d(-46,10))
                .strafeTo(new Vector2d(-58,10))
                .strafeTo(new Vector2d(-58,55))
                .strafeTo(new Vector2d(-40, 55))
                .strafeTo(new Vector2d(-40, 60))
                .build();

        Action trajectory2 = myBot.getDrive().actionBuilder(new Pose2d(-40, 62, Math.toRadians(-90)))
                .splineToSplineHeading(new Pose2d(-8, 30, Math.toRadians(90)), Math.toRadians(-90))
                .build();

        Action trajectory21 = myBot.getDrive().actionBuilder(new Pose2d(-40, 62, Math.toRadians(-90)))
                .splineToSplineHeading(new Pose2d(-6, 30, Math.toRadians(90)), Math.toRadians(-90))
                .build();

        Action trajectory22 = myBot.getDrive().actionBuilder(new Pose2d(-40, 62, Math.toRadians(-90)))
                .splineToSplineHeading(new Pose2d(-4, 30, Math.toRadians(90)), Math.toRadians(-90))
                .build();

        Action trajectory3 = myBot.getDrive().actionBuilder(new Pose2d(-8, 30, Math.toRadians(90)))
                .splineToSplineHeading(new Pose2d(-40, 62, Math.toRadians(-90)), Math.toRadians(90))
                .build();

        Action trajectory31 = myBot.getDrive().actionBuilder(new Pose2d(-12, 30, Math.toRadians(90)))
                .splineToSplineHeading(new Pose2d(-40, 62, Math.toRadians(-90)), Math.toRadians(90))
                .build();

        myBot.runAction(
                new SequentialAction(
                        new ParallelAction(
//                                outtakeLift.liftUp(),
                                trajectory0
//                                vslide.raise(.40)
                        ),
//                        hangSpecimen,
                        new SleepAction(0.25),
                        new ParallelAction(
//                                outtakeLift.liftDown(),
                                trajectory01
                        ),
                        new ParallelAction(
//                                outtakeLift.liftForward(),
                                trajectory1
//                                vslide.lower(.35)
                        ),
//                        outtakeClaw.closeClaw(),
                        new SleepAction(0.25),
                        new ParallelAction(
                                trajectory2
//                                vslide.raise(.40)
                        ),
//                        hangSpecimen,
                        new SleepAction(0.25),
                        new ParallelAction(
                                trajectory3
//                                vslide.lower(.25)
                        ),
//                        outtakeClaw.closeClaw(),
                        new SleepAction(.25),
                        new ParallelAction(
                                trajectory21
//                                vslide.raise(.40)
                        ),
//                        hangSpecimen,
                        new SleepAction(0.5),
                        new ParallelAction(
                                trajectory31
//                                vslide.lower(.25)
                        ),
                        new SleepAction(0.25),
                        new ParallelAction(
                                trajectory22
//                                vslide.raise(.40)
                        ),
//                        hangSpecimen,
                        new SleepAction(0.5)
                )
        );

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}