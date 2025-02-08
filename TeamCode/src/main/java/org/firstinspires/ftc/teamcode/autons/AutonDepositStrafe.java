package org.firstinspires.ftc.teamcode.autons;

// RR-specific imports
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

// Non-RR imports
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;

import org.firstinspires.ftc.teamcode.autons.mechanismclasses.*;

@Autonomous(name = "AutonDepositStrafe")
public class AutonDepositStrafe extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d initialPose = new Pose2d(-12, 60, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        // all mechanism classes
        ActiveIntake activeIntake = new ActiveIntake(hardwareMap);
        Extension extension = new Extension(hardwareMap);
        IntakeStopper intakeStopper = new IntakeStopper(hardwareMap);
        OuttakeLift outtakeLift = new OuttakeLift(hardwareMap);
        OuttakeClaw outtakeClaw = new OuttakeClaw(hardwareMap);
        VSlide vslide = new VSlide(hardwareMap);

        // trajectories
        Action trajectory1 = drive.actionBuilder(initialPose)
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
                .build();

        waitForStart();

        if (isStopRequested()) return;

        // auton routine
        Actions.runBlocking(
                trajectory1
        );
    }
}