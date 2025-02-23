package org.firstinspires.ftc.teamcode.autons;

// RR-specific imports
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

// Non-RR imports
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;

import org.firstinspires.ftc.teamcode.autons.mechanismclasses.*;

@Autonomous(name = "High Basket Auto")
public class HighBasketAuton extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d initialPose = new Pose2d(10, 60, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        // all mechanism classes
        ActiveIntake activeIntake = new ActiveIntake(hardwareMap);
        Extension extension = new Extension(hardwareMap);
        IntakeStopper intakeStopper = new IntakeStopper(hardwareMap);
        OuttakeLift outtakeLift = new OuttakeLift(hardwareMap);
        OuttakeClaw outtakeClaw = new OuttakeClaw(hardwareMap);
        VSlide vslide = new VSlide(hardwareMap);
        IntakeLift intakeLift = new IntakeLift(hardwareMap);

        // custom actions
//        Action preload = new SequentialAction(
//                new ParallelAction(
//                        extension.extendIn(),
//                        outtakeLift.liftDown()
//                ),
//                new SleepAction(.25),
//                outtakeClaw.closeClaw(),
//                new SleepAction(.25),
//                outtakeLift.liftUp()
//        );
        Action hangSpecimen = new SequentialAction(
                vslide.lower(.25),
                new SleepAction(0.25),
                outtakeClaw.openClaw()
        );

        //high basket deposit
        Action highdep = new SequentialAction(
                new ParallelAction(
                        extension.extendIn(),
                        outtakeLift.liftDown()
                ),
                new SleepAction(.25),
                outtakeClaw.closeClaw(),
                new SleepAction(.25),
                outtakeLift.liftUp()
        );

        Action transfer = new SequentialAction(
                new ParallelAction(
                        extension.extendIn(),
                        outtakeLift.liftDown(),
                        outtakeClaw.openClaw()
                ),
                new SleepAction(0.5),
                intakeStopper.lowerStopper(),
                new SleepAction(2),
                outtakeClaw.closeClaw(),
                new SleepAction(.25),
                outtakeLift.liftAngleUp()
        );

        Action intaking = new SequentialAction(

                new ParallelAction(
                        activeIntake.intake(2.5),
                        intakeStopper.raiseStopper(),
                        intakeLift.liftUp()
                )

        );

        // trajectories
        Action trajectory0 = drive.actionBuilder(initialPose)
                .lineToY(31)
                .build();

        Action trajectory01 = drive.actionBuilder(initialPose)
                .lineToY(38)
                .build();

        Action trajectory1 = drive.actionBuilder(new Pose2d(10, 38, Math.toRadians(90)))
                .splineToSplineHeading(new Pose2d(48, 47, Math.toRadians(-90)), Math.toRadians(0))
                .build();

        Action trajectory2 = drive.actionBuilder(new Pose2d(48, 47, Math.toRadians(-90)))
                .lineToY(38)
                .build();

        Action trajectory3 = drive.actionBuilder(new Pose2d(48, 40, Math.toRadians(-90)))
                .splineToLinearHeading(new Pose2d(55, 55, Math.toRadians(225)), Math.toRadians(45))
                .build();

        Action trajectory4 = drive.actionBuilder(new Pose2d(55, 55, Math.toRadians(225)))
                .strafeToSplineHeading(new Vector2d(58, 47), Math.toRadians(-90))
                .build();

        Action trajectory5 = drive.actionBuilder(new Pose2d(58, 47, Math.toRadians(-90)))
                .lineToY(35)
                .build();

        Action trajectory6 = drive.actionBuilder(new Pose2d(58, 40, Math.toRadians(-90)))
                .splineToLinearHeading(new Pose2d(57, 55, Math.toRadians(225)), Math.toRadians(90))
                .build();

////        Action trajectory7 = myBot.getDrive().actionBuilder(new Pose2d(57, 55, Math.toRadians(225)))
////                .strafeToLinearHeading(new Vector2d(58, 47), Math.toRadians(300))
////                .build();
//
//        Action trajectory7 = myBot.getDrive().actionBuilder(new Pose2d(57, 55, Math.toRadians(225)))
//                .strafeToLinearHeading(new Vector2d(61, 44), Math.toRadians(300))
//                .build();

        Action trajectory7 = drive.actionBuilder(new Pose2d(57, 55, Math.toRadians(225)))
                .strafeToLinearHeading(new Vector2d(58, 47), Math.toRadians(285))
                .build();

        Action trajectory8 = drive.actionBuilder(new Pose2d(58, 47, Math.toRadians(300)))
                .strafeToLinearHeading(new Vector2d(61, 42), Math.toRadians(285))
                .build();

        Action trajectory9 = drive.actionBuilder(new Pose2d(61, 44, Math.toRadians(300)))
                .splineToLinearHeading(new Pose2d(57, 55, Math.toRadians(225)), Math.toRadians(90))
                .build();


        Actions.runBlocking(
                new SequentialAction(
                        outtakeClaw.closeClaw(),
                        outtakeLift.liftDown(),
                        intakeStopper.lowerStopper(),
                        extension.extendIn()
                )
        );

        waitForStart();

        if (isStopRequested()) return;

        // auton routine
        Actions.runBlocking(
                new SequentialAction(
                        // hangs preload
                        new ParallelAction(
                                outtakeLift.liftUp(),
                                trajectory0,
                                vslide.raise(.40)
                        ),
                        hangSpecimen,
                        new SleepAction(.20),
                        trajectory01,
                        new ParallelAction(
                                outtakeLift.liftDown(),
                                trajectory1,
                                vslide.lower(.40)
                        ),
//                        new SleepAction(0.25),
                        new ParallelAction(
                        extension.extendOut(),
                                intakeLift.liftDown()
                                ),
                        new SleepAction(0.25),
                        new ParallelAction(
                                trajectory2,
                                intaking
                        ),
                        new SleepAction(2),

                        transfer,
                        new SleepAction(2),
                        new ParallelAction(
                                trajectory3,
                                vslide.raise(1.1)
                        ),
                        outtakeClaw.openClaw(),
                        new SleepAction(0.75),
                        new ParallelAction(
                                trajectory4,
                                vslide.lower(1.0),
                                outtakeLift.liftDown(),
                                outtakeClaw.openClaw()
                        ),







                        new SleepAction(0.25),
                        new ParallelAction(
                                extension.extendOut(),
                                intakeLift.liftDown()
                        ),
                        new ParallelAction(
                                trajectory5,
                                intaking
                        ),
                        new SleepAction(0.25),
                        transfer,
                        new SleepAction(2),
                        new ParallelAction(
                                trajectory6,
                                vslide.raise(1.1)
                        ),


                        outtakeClaw.openClaw(),
                        new SleepAction(0.75),
                        new ParallelAction(
                                trajectory7,
                                vslide.lower(1.0),
                                outtakeLift.liftDown(),
                                outtakeClaw.openClaw()
                        ),







                        new SleepAction(0.25),
                        new ParallelAction(
                                extension.extendOut(),
                                intakeLift.liftDown()
                        ),
                        new ParallelAction(
                                trajectory8,
                                intaking
                        ),
                        new SleepAction(0.25),
                        transfer,
                        new SleepAction(2),
                        new ParallelAction(
                                trajectory9,
                                vslide.raise(1.1)
                        ),
                        outtakeClaw.openClaw()









//                        vslide.lower(.10),
//                        new SleepAction(.25),
//                        vslide.raise(.10),
//                        // backs up
//                        new SleepAction(.25),
//                        outtakeClaw.openClaw(),
//                        new SleepAction(.25),
//                        new ParallelAction(
//                                trajectory2,
//                                outtakeLift.liftDown(),
//                                vslide.lower(0.40)
//                        ),
//                        // strafes to samples
//                        new SleepAction(.25),
//                        trajectory3,
//                        new SleepAction(.25),
//                        new ParallelAction(
//
//                                trajectory4,
//////                                activeIntake.intake(0.75),
//////                                intakeStopper.raiseStopper()
//                                intaking
//                        ),
//                        new SleepAction(.25),
//                        new ParallelAction(
//                                intakeLift.liftUp(),
//                                intakeStopper.lowerStopper()
//                        ),
//                        new SleepAction(.25),
//                        new ParallelAction(
//                                transfer
//                        ),
//                        new SleepAction(.25),
//                            new ParallelAction(
//                                    vslide.raise(0.9),
//                                    intakeLift.liftDown(),
//                                    trajectory5
//                            )



                )
        );
    }
}