package org.firstinspires.ftc.teamcode.autons;

// RR-specific imports
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

// Non-RR imports
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.MecanumDrive;

import org.firstinspires.ftc.teamcode.Positions;
import org.firstinspires.ftc.teamcode.autons.mechanismclasses.*;

@Autonomous(name = "BetterAutonDeposit")
public class BetterAutonDeposit extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        // road runner stuff
        Pose2d initialPose = new Pose2d(-10, 60, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        // all mechanism classes
        ActiveIntake activeIntake = new ActiveIntake(hardwareMap);
        Extension extension = new Extension(hardwareMap);
        IntakeStopper intakeStopper = new IntakeStopper(hardwareMap);
        OuttakeLift outtakeLift = new OuttakeLift(hardwareMap);
        OuttakeClaw outtakeClaw = new OuttakeClaw(hardwareMap);
        VSlide vslide = new VSlide(hardwareMap);

        // custom actions

//        Action preload = new SequentialAction(
//                outtakeClaw.closeClaw(),
//                new SleepAction(.25),
//                outtakeLift.liftUp()
//        );

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

        Action trajectory0 = drive.actionBuilder(initialPose)
                .lineToY(31)
                .lineToYSplineHeading(45, Math.toRadians(-90))
                .build();

        Action trajectory1 = drive.actionBuilder(new Pose2d(-10, 45, Math.toRadians(-90)))
                .strafeTo(new Vector2d(-37,38))
                .strafeTo(new Vector2d(-37,10))
                .strafeTo(new Vector2d(-46,10))
                .strafeTo(new Vector2d(-46,60))
                .strafeTo(new Vector2d(-46,10))
                .strafeTo(new Vector2d(-60,10))
                .strafeTo(new Vector2d(-60,62))
                .strafeTo(new Vector2d(-40, 62))
                .build();

        Action trajectory2 = drive.actionBuilder(new Pose2d(-40, 62, Math.toRadians(-90)))
                .splineToSplineHeading(new Pose2d(-10, 30, Math.toRadians(90)), Math.toRadians(-90))
                .build();

        Action trajectory21 = drive.actionBuilder(new Pose2d(-40, 62, Math.toRadians(-90)))
                .splineToSplineHeading(new Pose2d(-10, 30, Math.toRadians(90)), Math.toRadians(-90))
                .build();

        Action trajectory22 = drive.actionBuilder(new Pose2d(-40, 62, Math.toRadians(-90)))
                .splineToSplineHeading(new Pose2d(-10, 30, Math.toRadians(90)), Math.toRadians(-90))
                .build();

        Action trajectory3 = drive.actionBuilder(new Pose2d(-10, 30, Math.toRadians(90)))
                .splineToSplineHeading(new Pose2d(-40, 62, Math.toRadians(-90)), Math.toRadians(90))
                .build();

        Action trajectory31 = drive.actionBuilder(new Pose2d(-10, 30, Math.toRadians(90)))
                .splineToSplineHeading(new Pose2d(-40, 62, Math.toRadians(-90)), Math.toRadians(90))
                .build();

        // initial positions
        Actions.runBlocking(
                new SequentialAction(
                    extension.extendIn(),
                    outtakeClaw.closeClaw()
                )
        );

        waitForStart();

        if (isStopRequested()) return;

        // auton routine
        Actions.runBlocking(
                new SequentialAction(
                        outtakeLift.liftUp(),
                        trajectory0
                )
//                new SequentialAction(
//                        outtakeLift.liftUp()
//                        new ParallelAction(
//                                trajectory0,
//                                vslide.raise(.40)
//                        ),
//                        new SleepAction(.25),
//                        vslide.lower(.25),
//                        new SleepAction(0.25),
//                        vslide.raise(.25),
//                        new ParallelAction(
//                            trajectory1,
//                            vslide.lower(.3)
//                        ),
//                        outtakeClaw.closeClaw(),
//                        trajectory2,
//                        new SleepAction(0.5),
//                        trajectory3,
//                        trajectory21,
//                        new SleepAction(0.5),
//                        trajectory31,
//                        trajectory22,
//                        new SleepAction(0.5)
//                )
        );
    }
}