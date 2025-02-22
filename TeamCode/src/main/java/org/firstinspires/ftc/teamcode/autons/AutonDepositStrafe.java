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

@Autonomous(name = "4SPEC")
public class AutonDepositStrafe extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        // road runner stuff
        Pose2d initialPose = new Pose2d(-10, 60, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        // all mechanism classes
        CustomActions customActions = new CustomActions(hardwareMap);
        Extension extension = new Extension(hardwareMap);
        OuttakeLift outtakeLift = new OuttakeLift(hardwareMap);
        OuttakeClaw outtakeClaw = new OuttakeClaw(hardwareMap);
        VSlide vslide = new VSlide(hardwareMap);
        Wiper wiper = new Wiper(hardwareMap);

        Action trajectory0 = drive.actionBuilder(initialPose)
                .lineToY(31)
                .build();

        Action trajectory01 = drive.actionBuilder(new Pose2d(-10, 31, Math.toRadians(90)))
//                .lineToYSplineHeading(45, Math.toRadians(-90))
                .splineToSplineHeading(new Pose2d(-37, 38, -90), Math.toRadians(180))
                .build();

        Action trajectory1 = drive.actionBuilder(new Pose2d(-37, 38, Math.toRadians(-90)))
//                .strafeTo(new Vector2d(-37,38))
                .strafeTo(new Vector2d(-37,10))
                .strafeTo(new Vector2d(-46,10))
                .strafeTo(new Vector2d(-46,50))
                .strafeTo(new Vector2d(-46,10))
                .strafeTo(new Vector2d(-58,10))
                .strafeTo(new Vector2d(-58,50))
                .strafeTo(new Vector2d(-40, 50))
                .strafeTo(new Vector2d(-41, 61))
                .build();

        Action trajectory2 = drive.actionBuilder(new Pose2d(-41, 61, Math.toRadians(-90)))
                .splineToSplineHeading(new Pose2d(-8, 34, Math.toRadians(90)), Math.toRadians(-90))
                .build();

        Action trajectory21 = drive.actionBuilder(new Pose2d(-41, 61, Math.toRadians(-90)))
                .splineToSplineHeading(new Pose2d(-6, 34, Math.toRadians(90)), Math.toRadians(-90))
                .build();

        Action trajectory22 = drive.actionBuilder(new Pose2d(-41, 61, Math.toRadians(-90)))
                .splineToSplineHeading(new Pose2d(-4, 34, Math.toRadians(90)), Math.toRadians(-90))
                .build();

        Action trajectory3 = drive.actionBuilder(new Pose2d(-8, 30, Math.toRadians(90)))
                .splineToSplineHeading(new Pose2d(-41, 59, Math.toRadians(-90)), Math.toRadians(90))
                .lineToY(61)
                .build();

        Action trajectory31 = drive.actionBuilder(new Pose2d(-6, 30, Math.toRadians(90)))
                .splineToSplineHeading(new Pose2d(-41, 59, Math.toRadians(-90)), Math.toRadians(90))
                .lineToY(61)
                .build();

        // initial positions
        Actions.runBlocking(
                new SequentialAction(
                        outtakeLift.liftDown(),
                        extension.extendIn(),
                        outtakeClaw.closeClaw(),
                        wiper.closeWiper()
                )
        );

        waitForStart();

        if (isStopRequested()) return;

        // auton routine
        Actions.runBlocking(
                new SequentialAction(
                        // hang preload
                        new ParallelAction(
                                outtakeLift.liftUp(),
                                trajectory0,
                                vslide.raise(.40)
                        ),
                        customActions.hangSpecimen(),
                        new SleepAction(0.20),
                        // push blocks back
                        new ParallelAction(
                                outtakeLift.liftDown(),
                                trajectory01
                        ),
                        new ParallelAction(
                                new SequentialAction(
                                        new SleepAction(0.5),
                                        outtakeLift.liftForward()
                                ),
                                trajectory1,
                                vslide.lower(.40)
                        ),
                        // hang specimen in the middle #1
                        outtakeClaw.closeClaw(),
                        new SleepAction(0.45),
                        new ParallelAction(
                                trajectory2,
                                vslide.raise(.40),
                                outtakeLift.liftAngleUp()
                        ),
                        customActions.hangSpecimen(),
                        new SleepAction(0.25),
                        // pickup specimen at the wall #1
                        new ParallelAction(
                                trajectory3,
                                vslide.lower(.40),
                                new SequentialAction(
                                        new SleepAction(0.5),
                                        outtakeLift.liftForward()
                                )
                        ),
                        new SleepAction(0.20),
                        // hang specimen in the middle #2
                        outtakeClaw.closeClaw(),
                        new SleepAction(0.25),
                        new ParallelAction(
                                trajectory21,
                                vslide.raise(.40),
                                outtakeLift.liftAngleUp()
                        ),
                        customActions.hangSpecimen(),
                        new SleepAction(0.25),
                        // pickup specimen at the wall #2
                        new ParallelAction(
                                trajectory31,
                                vslide.lower(.40),
                                new SequentialAction(
                                        new SleepAction(0.5),
                                        outtakeLift.liftForward()
                                )
                        ),
                        new SleepAction(0.2),
                        outtakeClaw.closeClaw(),
                        // hang specimen in the middle #3
                        new SleepAction(0.25),
                        new ParallelAction(
                                trajectory22,
                                vslide.raise(.40),
                                outtakeLift.liftAngleUp()
                        ),
                        customActions.hangSpecimen()
                )
        );
    }
}