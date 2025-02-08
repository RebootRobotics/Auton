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
        Pose2d initialPose = new Pose2d(0, 60, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        // all mechanism classes
        ActiveIntake activeIntake = new ActiveIntake(hardwareMap);
        Extension extension = new Extension(hardwareMap);
        IntakeStopper intakeStopper = new IntakeStopper(hardwareMap);
        OuttakeLift outtakeLift = new OuttakeLift(hardwareMap);
        OuttakeClaw outtakeClaw = new OuttakeClaw(hardwareMap);
        VSlide vslide = new VSlide(hardwareMap);

        // custom actions
        Action preload = new SequentialAction(
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
                new SleepAction(.25),
                outtakeClaw.closeClaw(),
                new SleepAction(.25),
                outtakeLift.liftUp()
        );

        // trajectories
        Action trajectory1 = drive.actionBuilder(initialPose)
                .lineToY(35)
                .build();
        Action trajectory2 = drive.actionBuilder(new Pose2d(0, 35, Math.toRadians(90)))
                .strafeTo(new Vector2d(0, 40))
                .build();
        Action trajectory3 = drive.actionBuilder(new Pose2d(0, 40, Math.toRadians(-90)))
                .turn(Math.toRadians(90))
                .strafeTo(new Vector2d(8, 40))
                .build();

        Actions.runBlocking(
                new SequentialAction(
                        outtakeClaw.closeClaw(),
//                        outtakeLift.liftInit()
                        intakeStopper.lowerStopper()
                )
        );

        waitForStart();

        if (isStopRequested()) return;

        // auton routine
        Actions.runBlocking(
                new SequentialAction(
                        // hangs preload
                        preload,
                        new ParallelAction(
                                trajectory1,
                                outtakeLift.liftUp(),
                                vslide.raise(.40)
                        ),
                        new SleepAction(.25),
                        vslide.lower(.10),
                        new SleepAction(.25),
                        vslide.raise(.10),
                        // backs up
                        new SleepAction(.25),
                        outtakeClaw.openClaw(),
                        new SleepAction(.25),
                        new ParallelAction(
                                trajectory2,
                                outtakeLift.liftDown(),
                                vslide.lower(.40)
                        ),
                        // strafes to samples
                        new SleepAction(.25),
                        trajectory3
                )
        );
    }
}