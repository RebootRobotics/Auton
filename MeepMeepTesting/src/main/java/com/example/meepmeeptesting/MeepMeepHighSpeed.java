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

public class MeepMeepHighSpeed {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        double actionDuration = 0.1;

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(120, 120, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        Pose2d initialPose = new Pose2d(-10, 60, Math.toRadians(90));

        Action trajectory0 = myBot.getDrive().actionBuilder(initialPose)
                .lineToY(31)
                .build();

        Action trajectory01 = myBot.getDrive().actionBuilder(new Pose2d(-10, 31, Math.toRadians(90)))
//                .lineToYSplineHeading(45, Math.toRadians(-90))
                .splineToSplineHeading(new Pose2d(-37, 38, -90), Math.toRadians(180))
                .build();

        Action trajectory1 = myBot.getDrive().actionBuilder(new Pose2d(-37, 38, Math.toRadians(-90)))
//                .strafeTo(new Vector2d(-37,38))
                .strafeTo(new Vector2d(-37,10))
                .strafeTo(new Vector2d(-46,10))
                .strafeTo(new Vector2d(-46,52))
                .strafeTo(new Vector2d(-46,10))
                .strafeTo(new Vector2d(-58,10))
                .strafeTo(new Vector2d(-58,52))
                .strafeTo(new Vector2d(-58,10))
                .strafeTo(new Vector2d(-60,10))
//                .strafeTo(new Vector2d(-60,52))
//                .strafeTo(new Vector2d(-40, 52))
//                .strafeTo(new Vector2d(-40, 62))
                .strafeTo(new Vector2d(-60,62))
                .build();

        Action trajectory2 = myBot.getDrive().actionBuilder(new Pose2d(-60, 62, Math.toRadians(-90)))
                .splineToSplineHeading(new Pose2d(-8, 34, Math.toRadians(90)), Math.toRadians(-90))
                .build();

        Action trajectory21 = myBot.getDrive().actionBuilder(new Pose2d(-40, 62, Math.toRadians(-90)))
                .splineToSplineHeading(new Pose2d(-5, 34, Math.toRadians(90)), Math.toRadians(-90))
                .build();

        Action trajectory22 = myBot.getDrive().actionBuilder(new Pose2d(-40, 62, Math.toRadians(-90)))
                .splineToSplineHeading(new Pose2d(-2, 34, Math.toRadians(90)), Math.toRadians(-90))
                .build();

        Action trajectory23 = myBot.getDrive().actionBuilder(new Pose2d(-40, 62, Math.toRadians(-90)))
                .splineToSplineHeading(new Pose2d(1, 34, Math.toRadians(90)), Math.toRadians(-90))
                .build();

        Action trajectory3 = myBot.getDrive().actionBuilder(new Pose2d(-8, 34, Math.toRadians(90)))
                .splineToSplineHeading(new Pose2d(-40, 52, Math.toRadians(-90)), Math.toRadians(90))
                .lineToY(62)
                .build();

        Action trajectory31 = myBot.getDrive().actionBuilder(new Pose2d(-5, 34, Math.toRadians(90)))
                .splineToSplineHeading(new Pose2d(-40, 52, Math.toRadians(-90)), Math.toRadians(90))
                .lineToY(62)
                .build();

        Action trajectory32 = myBot.getDrive().actionBuilder(new Pose2d(-2, 34, Math.toRadians(90)))
                .splineToSplineHeading(new Pose2d(-40, 52, Math.toRadians(-90)), Math.toRadians(90))
                .lineToY(62)
                .build();

        myBot.runAction(
                new SequentialAction(
                        new ParallelAction(
//                                outtakeLift.liftUp(),
                                trajectory0
//                                vslide.raise(.40)
                        ),
//                        hangSpecimen,
                        new SleepAction(actionDuration),
                        new SleepAction(0.20),
                        new ParallelAction(
//                                outtakeLift.liftDown(),
                                trajectory01
                        ),
                        new ParallelAction(
                                new SequentialAction(
                                        new SleepAction(0.5),
//                                        outtakeLift.liftForward()
                                        new SleepAction(actionDuration)
                                ),
                                trajectory1
//                                vslide.lower(.40)
                        ),
//                        outtakeClaw.closeClaw(),
                        new SleepAction(actionDuration),
                        new SleepAction(0.45),
                        new ParallelAction(
                                trajectory2
//                                vslide.raise(.40),
//                                outtakeLift.liftAngleUp()
                        ),
//                        hangSpecimen,
//                        outtakeClaw.openClaw(),
//                        vslide.lower(.25),
                        new SleepAction(actionDuration),
                        new SleepAction(0.25),
//                        outtakeClaw.openClaw(),
                        new SleepAction(actionDuration),

                        new SleepAction(0.25),
                        new ParallelAction(
                                trajectory3,
//                                vslide.lower(.40),
                                new SequentialAction(
                                        new SleepAction(0.5),
//                                        outtakeLift.liftForward()
                                        new SleepAction(actionDuration)
                                )
                        ),
//                        outtakeClaw.closeClaw(),
                        new SleepAction(actionDuration),
                        new SleepAction(0.45),
                        new ParallelAction(
                                trajectory21
//                                vslide.raise(.40),
//                                outtakeLift.liftAngleUp()
                        ),
//                        hangSpecimen,
//                        outtakeClaw.openClaw(),
//                        vslide.lower(.25),
                        new SleepAction(actionDuration),
                        new SleepAction(0.25),
//                        outtakeClaw.openClaw(),
                        new SleepAction(actionDuration),
                        new SleepAction(0.25),
                        new ParallelAction(
                                trajectory31,
//                                vslide.lower(.40),
                                new SequentialAction(
                                        new SleepAction(0.5),
//                                        outtakeLift.liftForward()
                                        new SleepAction(actionDuration)
                                )
                        ),
//                        outtakeClaw.closeClaw(),
                        new SleepAction(actionDuration),
                        new SleepAction(0.45),
                        new ParallelAction(
                                trajectory22
//                                vslide.raise(.40),
//                                outtakeLift.liftAngleUp()
                        ),
                        //hang specimen
//                        vslide.lower(.25),
                        new SleepAction(actionDuration),
                        new SleepAction(0.25),
//                        outtakeClaw.openClaw()
                        new SleepAction(actionDuration),
                        new ParallelAction(
                                trajectory32,
//                                vslide.lower(.40),
                                new SequentialAction(
                                        new SleepAction(0.5),
//                                        outtakeLift.liftForward()
                                        new SleepAction(actionDuration)
                                )
                        ),
//                        outtakeClaw.closeClaw(),
                        new SleepAction(actionDuration),
                        new SleepAction(0.45),
                        new ParallelAction(
                                trajectory23
//                                vslide.raise(.40),
//                                outtakeLift.liftAngleUp()
                        ),
//                        hangSpecimen,
//                        outtakeClaw.openClaw(),
//                        vslide.lower(.25),
                        new SleepAction(actionDuration),
                        new SleepAction(0.25),
//                        outtakeClaw.openClaw(),
                        new SleepAction(actionDuration),
                        new SleepAction(0.25)
                )
        );

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}