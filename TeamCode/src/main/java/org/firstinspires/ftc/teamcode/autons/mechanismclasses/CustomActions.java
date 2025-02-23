package org.firstinspires.ftc.teamcode.autons.mechanismclasses;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class CustomActions {
    Extension extension;
    IntakeStopper intakeStopper;
    OuttakeLift outtakeLift;
    OuttakeClaw outtakeClaw;
    VSlide vslide;
    IntakeLift intakeLift;
    ActiveIntake activeIntake;


    public CustomActions(HardwareMap hardwareMap) {
        extension = new Extension(hardwareMap);
        intakeStopper = new IntakeStopper(hardwareMap);
        outtakeLift = new OuttakeLift(hardwareMap);
        outtakeClaw = new OuttakeClaw(hardwareMap);
        vslide = new VSlide(hardwareMap);
        intakeLift = new IntakeLift(hardwareMap);
        activeIntake = new ActiveIntake(hardwareMap);
    }

    public Action transfer() {
        return new SequentialAction(
                new ParallelAction(
                        extension.extendIn(),
                        outtakeLift.liftDown(),
                        outtakeClaw.openClaw()
                ),
                intakeStopper.lowerStopper(),
                new SleepAction(.25),
                outtakeClaw.closeClaw(),
                new SleepAction(.25),
                outtakeLift.liftUp()
        );
    }

    public Action hangSpecimen() {
        return new SequentialAction(
                vslide.lower(.25),
                new SleepAction(0.25),
                outtakeClaw.openClaw()
        );
    }

    public Action highBasketDeposit() {
        return new SequentialAction(
                new ParallelAction(
                        extension.extendIn(),
                        outtakeLift.liftDown()
                ),
                new SleepAction(.25),
                outtakeClaw.closeClaw(),
                new SleepAction(.25),
                outtakeLift.liftUp()
        );
    }

    public Action prepIntake() {
        return new ParallelAction(
            intakeLift.liftDown(),
            intakeStopper.raiseStopper()
        );
    }


}
