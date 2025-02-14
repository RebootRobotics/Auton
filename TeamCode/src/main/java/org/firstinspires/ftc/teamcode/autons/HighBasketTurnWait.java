package org.firstinspires.ftc.teamcode.autons;

// RR-specific imports

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.autons.mechanismclasses.ActiveIntake;
import org.firstinspires.ftc.teamcode.autons.mechanismclasses.Extension;
import org.firstinspires.ftc.teamcode.autons.mechanismclasses.IntakeLift;
import org.firstinspires.ftc.teamcode.autons.mechanismclasses.IntakeStopper;
import org.firstinspires.ftc.teamcode.autons.mechanismclasses.OuttakeClaw;
import org.firstinspires.ftc.teamcode.autons.mechanismclasses.OuttakeLift;
import org.firstinspires.ftc.teamcode.autons.mechanismclasses.VSlide;

@Autonomous(name = "High Basket Turn Wait")
public class HighBasketTurnWait extends LinearOpMode {

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
        IntakeLift intakeLift = new IntakeLift(hardwareMap);

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

        //high basket deposit
        Action highdep = new SequentialAction(
                new ParallelAction(
                        extension.extendIn(),
                        outtakeLift.liftDown()
                ),
                new SleepAction(.25),
                outtakeClaw.closeClaw(),
                new SleepAction(2),
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
                new SleepAction(2.0),
                outtakeLift.liftUp()
        );

        Action intaking = new SequentialAction(

                new ParallelAction(
                        activeIntake.intake(3.5),
                        intakeStopper.raiseStopper()
                )

        );

        // trajectories
        Action trajectory1 = drive.actionBuilder(initialPose)
                .lineToY(35)
                .build();
        Action trajectory2 = drive.actionBuilder(new Pose2d(0, 35, Math.toRadians(90)))
               // .strafeTo(new Vector2d(0, 40))
                .lineToY(40)
                .build();
        Action trajectory3 = drive.actionBuilder(new Pose2d(0, 40, Math.toRadians(90)))
                .turn(Math.toRadians(-90))
               // .strafeTo(new Vector2d(12, 40))
               // .setTangent(Math.toRadians(90))
                .lineToX(41)

                .build();
        Action trajectory4 = drive.actionBuilder(new Pose2d(41, 40, Math.toRadians(0)))
                .turn(Math.toRadians(-90))
                .build();
        Action trajectory5 = drive.actionBuilder(new Pose2d(41, 40, Math.toRadians(-90)))
//                .strafeTo(new Vector2d(27, 35))
//                .strafeToConstantHeading(new Vector2d(12,24))
//                .strafeToConstantHeading(new Vector2d(12,24))


                .lineToY(25)
//                .splineToConstantHeading(new Vector2d(9,37), Math.toRadians(0))
                .build();
        Action trajectory6 = drive.actionBuilder(new Pose2d(41, 25, Math.toRadians(-90)))
                .lineToY(56)
                .turn(Math.toRadians(-90))
                //.setTangent(Math.toRadians(90))
//                .strafeToConstantHeading(new Vector2d(12, 45))

               // .strafeTo(new Vector2d(15,45))
//                .strafeTo(new Vector2d())


//                .splineToLinearHeading(new Pose2d(8, 40, Math.toRadians(90)), Math.toRadians(-90))
                .build();
        Action trajectory7 = drive.actionBuilder(new Pose2d(41, 56 , Math.toRadians(180)))
                .lineToX(52)
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
                        new SleepAction(5.5),

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
                                outtakeLift.liftDown()

                        ), vslide.lower(0.1),
                        // strafes to samples
                        new SleepAction(.25),
                        trajectory3,
                        new SleepAction(.25),
                        new SequentialAction(

                                trajectory4,
////                                activeIntake.intake(0.75),
////                                intakeStopper.raiseStopper()
                                new ParallelAction(
                                intaking,
                                 trajectory5
                                )
                        ),
                        new SleepAction(.25),
                        new ParallelAction(
                                intakeLift.liftUp(),
                                intakeStopper.lowerStopper()
                        ),
                        new SleepAction(.25),
                        transfer,
                        new SleepAction(.25),
                        new ParallelAction(

                                trajectory6
                        ),
                        new SleepAction(.25),
                        vslide.raise(1.5),
                        intakeLift.liftDown(),
                        trajectory7




                )
        );
    }
}