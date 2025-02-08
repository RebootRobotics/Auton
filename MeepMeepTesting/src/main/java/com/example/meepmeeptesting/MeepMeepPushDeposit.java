package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
import com.acmerobotics.roadrunner.Vector2d;

public class MeepMeepPushDeposit {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(700);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-12, 60, Math.toRadians(90)))
                //drive to sub (preload)
//                .lineToY(30)
//                        .lineToY(38)
//                .turn(Math.toRadians(-90))
//                        .lineToX(-30)
//                        .turn(Math.toRadians(90))
//                        .lineToY(10)
//                        .turn(Math.toRadians(90))
//                .lineToX(-45)
//
//                .turn(Math.toRadians(90))
//                .lineToY(62)
//
//                .build());
                .lineToY(30)
                .lineToY(38)
                        .strafeTo(new Vector2d(-40,38))
//                .turn(Math.toRadians(-90))
//                .lineToX(-40)
//                .turn(Math.toRadians(90))
//                .lineToY(10)
                        .strafeTo(new Vector2d(-40,10))
//                .turn(Math.toRadians(90))
//                .lineToX(-53)
                                .strafeTo(new Vector2d(-53,10))

                                .strafeTo(new Vector2d(-53,60))
                .strafeTo(new Vector2d(-53,10))

                                .strafeTo(new Vector2d(-57,10))

                                .strafeTo(new Vector2d(-57,60))

//                .turn(Math.toRadians(90))
//                .lineToY(60)
//
//
////                        .splineTo(new Vector2d(10,10),0)
//
//                .lineToY(10)
//                .turn(Math.toRadians(90))
//                .lineToX(-57)
//                .turn(Math.toRadians(-90))
//                .lineToY(60)
                        .build());

//                .lineToY(10)
//                .turn(Math.toRadians(90))
//                .lineToX(-75)
//                .turn(Math.toRadians(90))
//                .lineToY(62)
//                        .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}