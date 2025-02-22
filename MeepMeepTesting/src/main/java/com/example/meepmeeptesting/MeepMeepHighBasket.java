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

public class MeepMeepHighBasket {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(1000);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        Pose2d initialPose = new Pose2d(10, 60, Math.toRadians(90));

        Action trajectory0 = myBot.getDrive().actionBuilder(initialPose)
                .lineToY(31)
                .build();

        Action trajectory1 = myBot.getDrive().actionBuilder(new Pose2d(10, 31, Math.toRadians(90)))
                .splineToSplineHeading(new Pose2d(48, 47, Math.toRadians(-90)), Math.toRadians(0))
                .build();

        Action trajectory2 = myBot.getDrive().actionBuilder(new Pose2d(48, 47, Math.toRadians(-90)))
                .lineToY(40)
                .build();

        Action trajectory3 = myBot.getDrive().actionBuilder(new Pose2d(48, 40, Math.toRadians(-90)))
                .splineToLinearHeading(new Pose2d(55, 55, Math.toRadians(225)), Math.toRadians(45))
                .build();

        Action trajectory4 = myBot.getDrive().actionBuilder(new Pose2d(55, 55, Math.toRadians(225)))
                .strafeToSplineHeading(new Vector2d(58, 47), Math.toRadians(-90))
                .build();

        Action trajectory5 = myBot.getDrive().actionBuilder(new Pose2d(58, 47, Math.toRadians(-90)))
                .lineToY(40)
                .build();

        Action trajectory6 = myBot.getDrive().actionBuilder(new Pose2d(58, 40, Math.toRadians(-90)))
                .splineToLinearHeading(new Pose2d(57, 55, Math.toRadians(225)), Math.toRadians(90))
                .build();

////        Action trajectory7 = myBot.getDrive().actionBuilder(new Pose2d(57, 55, Math.toRadians(225)))
////                .strafeToLinearHeading(new Vector2d(58, 47), Math.toRadians(300))
////                .build();
//
//        Action trajectory7 = myBot.getDrive().actionBuilder(new Pose2d(57, 55, Math.toRadians(225)))
//                .strafeToLinearHeading(new Vector2d(61, 44), Math.toRadians(300))
//                .build();

        Action trajectory7 = myBot.getDrive().actionBuilder(new Pose2d(57, 55, Math.toRadians(225)))
                .strafeToLinearHeading(new Vector2d(58, 47), Math.toRadians(300))
                .build();

        Action trajectory8 = myBot.getDrive().actionBuilder(new Pose2d(58, 47, Math.toRadians(300)))
                .strafeToLinearHeading(new Vector2d(61, 44), Math.toRadians(300))
                .build();

        Action trajectory9 = myBot.getDrive().actionBuilder(new Pose2d(61, 44, Math.toRadians(300)))
                .splineToLinearHeading(new Pose2d(57, 55, Math.toRadians(225)), Math.toRadians(90))
                .build();


        myBot.runAction(
                new SequentialAction(
                        trajectory0,
                        new SleepAction(0.5),
                        trajectory1,
                        trajectory2,
                        new SleepAction(0.5),
                        trajectory3,
                        new SleepAction(0.5),
                        trajectory4,
                        new SleepAction(0.5),
                        trajectory5,
                        new SleepAction(0.5),
                        trajectory6,
                        new SleepAction(0.5),
                        trajectory7,
                        new SleepAction(0.5),
                        trajectory8,
                        new SleepAction(0.5),
                        trajectory9
                )
        );

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}