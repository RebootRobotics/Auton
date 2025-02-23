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
        CustomActions customActions = new CustomActions(hardwareMap);
        ActiveIntake activeIntake = new ActiveIntake(hardwareMap);
        Extension extension = new Extension(hardwareMap);
        IntakeStopper intakeStopper = new IntakeStopper(hardwareMap);
        OuttakeLift outtakeLift = new OuttakeLift(hardwareMap);
        OuttakeClaw outtakeClaw = new OuttakeClaw(hardwareMap);
        VSlide vslide = new VSlide(hardwareMap);
        IntakeLift intakeLift = new IntakeLift(hardwareMap);

        // trajectories
        Action trajectory0 = drive.actionBuilder(initialPose)
                .lineToY(31)
                .build();

        Action trajectory1 = drive.actionBuilder(new Pose2d(10, 31, Math.toRadians(90)))
                .splineToSplineHeading(new Pose2d(48, 47, Math.toRadians(-90)), Math.toRadians(0))
                .build();

        Action trajectory2 = drive.actionBuilder(new Pose2d(48, 47, Math.toRadians(-90)))
                .lineToY(40)
                .build();

        Action trajectory3 = drive.actionBuilder(new Pose2d(48, 40, Math.toRadians(-90)))
                .splineToLinearHeading(new Pose2d(55, 55, Math.toRadians(225)), Math.toRadians(45))
                .build();

        Action trajectory4 = drive.actionBuilder(new Pose2d(55, 55, Math.toRadians(225)))
                .strafeToSplineHeading(new Vector2d(58, 47), Math.toRadians(-90))
                .build();

        Action trajectory5 = drive.actionBuilder(new Pose2d(58, 47, Math.toRadians(-90)))
                .lineToY(40)
                .build();

        Action trajectory6 = drive.actionBuilder(new Pose2d(58, 40, Math.toRadians(-90)))
                .splineToLinearHeading(new Pose2d(57, 55, Math.toRadians(225)), Math.toRadians(90))
                .build();

        Action trajectory7 = drive.actionBuilder(new Pose2d(57, 55, Math.toRadians(225)))
                .strafeToLinearHeading(new Vector2d(58, 47), Math.toRadians(300))
                .build();

        Action trajectory8 = drive.actionBuilder(new Pose2d(58, 47, Math.toRadians(300)))
                .strafeToLinearHeading(new Vector2d(61, 44), Math.toRadians(300))
                .build();

        Action trajectory9 = drive.actionBuilder(new Pose2d(61, 44, Math.toRadians(300)))
                .splineToLinearHeading(new Pose2d(57, 55, Math.toRadians(225)), Math.toRadians(90))
                .build();

        Action trajectory10 = drive.actionBuilder(new Pose2d(57, 55, Math.toRadians(225)))
                .splineToSplineHeading(new Pose2d(25, 10, Math.toRadians(180)), Math.toRadians(180))
                .build();

        // initialize positions
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
                        customActions.hangSpecimen(),
                        new SleepAction(.20),
                        // splines to first block
                        new ParallelAction(
                                trajectory1,
                                outtakeLift.liftDown(),
                                vslide.lower(.40)
                        ),
                        new SleepAction(0.25),
                        // intake first block
                        new ParallelAction(
                                extension.extendOut(),
                                customActions.prepIntake()
//                                customActions.highBasketDeposit()
                        ),
                        new SleepAction(0.25),
                        new ParallelAction(
                                trajectory2,
                                activeIntake.intake(2.5)
                        ),
                        new SleepAction(0.5),
                        // transfer while moving to high basket
                        new ParallelAction(
                                trajectory3,
                                new SequentialAction(
                                        customActions.transfer(),
                                        new SleepAction(0.25),
                                        vslide.raise(1.1)
                                )
                        ),
                        new SleepAction(0.25),
                        outtakeClaw.openClaw(),
                        new SleepAction(0.25),
                        // pick up second block
                        new ParallelAction(
                                trajectory4,
                                vslide.lower(1.0),
                                outtakeLift.liftDown()
                        ),
                        new SleepAction(0.25),
                        new ParallelAction(
                                extension.extendOut(),
                                customActions.prepIntake()
                        ),
                        new SleepAction(0.25),
                        new ParallelAction(
                                trajectory5,
                                activeIntake.intake(2.5)
                        ),
                        new SleepAction(0.25),
                        // drop second block
                        new ParallelAction(
                                trajectory6,
                                new SequentialAction(
                                        customActions.transfer(),
                                        new SleepAction(0.25),
                                        vslide.raise(1.1)
                                )
                        ),
                        new SleepAction(0.25),
                        outtakeClaw.openClaw(),
                        new SleepAction(0.25),
                        // pick up third block
                        new ParallelAction(
                                trajectory7,
                                vslide.lower(1.0),
                                outtakeLift.liftDown()
                        ),
                        new SleepAction(0.25),
                        new ParallelAction(
                                extension.extendOut(),
                                customActions.prepIntake()
                        ),
                        new SleepAction(0.25),
                        new ParallelAction(
                                trajectory8,
                                activeIntake.intake(2.5)
                        ),
                        new SleepAction(0.25),
                        // drop third block
                        new ParallelAction(
                                trajectory9,
                                new SequentialAction(
                                        customActions.transfer(),
                                        new SleepAction(0.25),
                                        vslide.raise(1.1)
                                )
                        ),
                        new SleepAction(0.25),
                        outtakeClaw.openClaw(),
                        new SleepAction(0.25),
                        new ParallelAction(
                                trajectory10,
                                outtakeLift.liftDown(),
                                vslide.lower(1.0)
                        )
                )
        );
    }
}