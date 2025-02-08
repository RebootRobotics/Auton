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

@Autonomous(name = "Straight Strafe Test")
public class StraightStrafeTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d initialPose = new Pose2d(0, 0, Math.toRadians(-90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        // all mechanism classes
        ActiveIntake activeIntake = new ActiveIntake(hardwareMap);
        Extension extension = new Extension(hardwareMap);
        IntakeStopper intakeStopper = new IntakeStopper(hardwareMap);
        OuttakeLift outtakeLift = new OuttakeLift(hardwareMap);
        OuttakeClaw outtakeClaw = new OuttakeClaw(hardwareMap);
        VSlide vslide = new VSlide(hardwareMap);

        // custom actions
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

        // hang trajectory
        Action trajectory1 = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(0, 25))
                .build();

        // go to first block
        Action trajectory2 = drive.actionBuilder(new Pose2d(0, 25, Math.toRadians(90)))
                .strafeTo(new Vector2d(0,20))
                .strafeTo(new Vector2d(-8,20))
                .strafeTo(new Vector2d(-8, 22))
                .build();

        // pickup first block
        Action trajectory3 = drive.actionBuilder(new Pose2d(-8, 22, Math.toRadians(90)))
                .strafeTo(new Vector2d(-8, 24))
                .build();

        // init
        outtakeClaw.closeClaw();

        waitForStart();

        if (isStopRequested()) return;

        // auton routine
        Actions.runBlocking(
                new SequentialAction(
                        transfer,
                        new ParallelAction(
                                trajectory1,
                                outtakeLift.liftUp(),
                                vslide.raise(.40)
                        )
//                        new SleepAction(.25),
//                        vslide.lower(.25),
//                        new SleepAction(.25),
//                        vslide.raise(.25),
//                        trajectory2,
//                        new ParallelAction(
//                                trajectory3,
//                                activeIntake.intake(1)
//                        )
                )
        );
    }
}