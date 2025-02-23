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

@Autonomous(name = "HighSpeedAuton") // 120, 120, -150
public class HighSpeedAuton extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        // road runner stuff
        Pose2d initialPose = new Pose2d(-10, 60, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        // maxProfileAccel 120
        // maxWheelVel 120
        // minProfileAccel -160

        // all mechanism classes
        ActiveIntake activeIntake = new ActiveIntake(hardwareMap);
        Extension extension = new Extension(hardwareMap);
        IntakeStopper intakeStopper = new IntakeStopper(hardwareMap);
        OuttakeLift outtakeLift = new OuttakeLift(hardwareMap);
        OuttakeClaw outtakeClaw = new OuttakeClaw(hardwareMap);
        VSlide vslide = new VSlide(hardwareMap);
        Wiper wiper = new Wiper(hardwareMap);

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

        Action hangSpecimen = new SequentialAction(
                vslide.lower(.25),
                new SleepAction(0.25),
                outtakeClaw.openClaw()
        );

        Action hangSpecimen1 = new SequentialAction(
                vslide.lower(.25),
                new SleepAction(0.25),
                outtakeClaw.openClaw()
        );

        Action hangSpecimen2 = new SequentialAction(
                vslide.lower(.25),
                new SleepAction(0.25),
                outtakeClaw.openClaw()
        );

        Action hangSpecimen3 = new SequentialAction(
                vslide.lower(.25),
                new SleepAction(0.25),
                outtakeClaw.openClaw()
        );

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
                .strafeTo(new Vector2d(-58,10))
                .strafeTo(new Vector2d(-60,10))
                .strafeTo(new Vector2d(-60,50))
                .strafeTo(new Vector2d(-40, 50))
                .strafeTo(new Vector2d(-40, 62))
                .build();

        Action trajectory2 = drive.actionBuilder(new Pose2d(-40, 62, Math.toRadians(-90)))
                .splineToSplineHeading(new Pose2d(-4, 34, Math.toRadians(90)), Math.toRadians(-90))
                .build();

        Action trajectory21 = drive.actionBuilder(new Pose2d(-40, 62, Math.toRadians(-90)))
                .splineToSplineHeading(new Pose2d(0, 34, Math.toRadians(90)), Math.toRadians(-90))
                .build();

        Action trajectory22 = drive.actionBuilder(new Pose2d(-40, 62, Math.toRadians(-90)))
                .splineToSplineHeading(new Pose2d(4, 34, Math.toRadians(90)), Math.toRadians(-90))
                .build();

        Action trajectory23 = drive.actionBuilder(new Pose2d(-40, 62, Math.toRadians(-90)))
                .splineToSplineHeading(new Pose2d(8, 34, Math.toRadians(90)), Math.toRadians(-90))
                .build();

        Action trajectory3 = drive.actionBuilder(new Pose2d(-4, 34, Math.toRadians(90)))
                .splineToSplineHeading(new Pose2d(-40, 57, Math.toRadians(-90)), Math.toRadians(90))
                .lineToY(62)
                .build();

        Action trajectory31 = drive.actionBuilder(new Pose2d(0, 34, Math.toRadians(90)))
                .splineToSplineHeading(new Pose2d(-40, 57, Math.toRadians(-90)), Math.toRadians(90))
                .lineToY(62)
                .build();

        Action trajectory32 = drive.actionBuilder(new Pose2d(4, 34, Math.toRadians(90)))
                .splineToSplineHeading(new Pose2d(-40, 57, Math.toRadians(-90)), Math.toRadians(90))
                .lineToY(62)
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

        Actions.runBlocking(
                new SequentialAction(
                        new ParallelAction(
                                outtakeLift.liftUp(),
                                trajectory0,
                                vslide.raise(.40)
                        ),
                        hangSpecimen,
                        new SleepAction(0.20),
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
                        outtakeClaw.closeClaw(),
                        new SleepAction(0.45),
                        new ParallelAction(
                                trajectory2,
                                vslide.raise(.40),
                                outtakeLift.liftAngleUp()
                        ),
                        hangSpecimen,
                        outtakeClaw.openClaw(),
                        vslide.lower(.25),
                        new SleepAction(0.25),
                        outtakeClaw.openClaw(),

                        new SleepAction(0.25),
                        new ParallelAction(
                                trajectory3,
                                vslide.lower(.40),
                                new SequentialAction(
                                        new SleepAction(0.5),
                                        outtakeLift.liftForward()
                                )
                        ),
                        outtakeClaw.closeClaw(),
                        new SleepAction(0.45),
                        new ParallelAction(
                                trajectory21,
                                vslide.raise(.40),
                                outtakeLift.liftAngleUp()
                        ),
                        hangSpecimen,
                        outtakeClaw.openClaw(),
                        vslide.lower(.25),
                        new SleepAction(0.25),
                        outtakeClaw.openClaw(),
                        new SleepAction(0.25),
                        new ParallelAction(
                                trajectory31,
                                vslide.lower(.40),
                                new SequentialAction(
                                        new SleepAction(0.5),
                                        outtakeLift.liftForward()
                                )
                        ),
                        outtakeClaw.closeClaw(),
                        new SleepAction(0.45),
                        new ParallelAction(
                                trajectory22,
                                vslide.raise(.40),
                                outtakeLift.liftAngleUp()
                        ),
                        //hang specimen
                        vslide.lower(.25),
                        new SleepAction(0.25),
                        outtakeClaw.openClaw(),
                        new ParallelAction(
                                trajectory32,
                                vslide.lower(.40),
                                new SequentialAction(
                                        new SleepAction(0.5),
                                        outtakeLift.liftForward()
                                )
                        ),
                        outtakeClaw.closeClaw(),
                        new SleepAction(0.45),
                        new ParallelAction(
                                trajectory23,
                                vslide.raise(.40),
                                outtakeLift.liftAngleUp()
                        ),
                        hangSpecimen,
                        outtakeClaw.openClaw(),
                        vslide.lower(.25),
                        new SleepAction(0.25),
                        outtakeClaw.openClaw(),
                        new SleepAction(0.25)
                )
        );
    }
}