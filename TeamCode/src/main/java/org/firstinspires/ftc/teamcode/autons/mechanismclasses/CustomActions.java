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
    }

    public Action transfer() {
        return new SequentialAction(
                new ParallelAction(
                        extension.extendIn(),
                        outtakeLift.liftDown(),
                        outtakeClaw.openClaw(),
                        intakeLift.liftUp()
                ),


                intakeStopper.lowerStopper(),

                new SleepAction(.25),
                outtakeClaw.closeClaw(),
                new SleepAction(0.45),
                outtakeLift.liftUp()
        );
    }
    // outtakeLift1.setPosition(Positions.OUTTAKE_LIFT1_DOWN);
//                outtakeLift2.setPosition(Positions.OUTTAKE_LIFT2_DOWN);
//                outtakeClaw.setPosition(Positions.OUTTAKE_CLAW_OPENED);
// if (extension1.getPosition() < 0.2) {
//sleep(250);
//                }
// extension1.setPosition(Positions.EXTENSION1_IN);
//                extension2.setPosition(Positions.EXTENSION2_IN);
//                intakeLift1.setPosition(Positions.INTAKE_LIFT1_UP);
//                intakeLift2.setPosition(Positions.INTAKE_LIFT2_UP);
//sleep(500);
//                intakeStopper.setPosition(Positions.INTAKE_STOPPER_DOWN);
//sleep(100);
//                outtakeClaw.setPosition(Positions.OUTTAKE_CLAW_CLOSED);

//sleep(600);
//                outtakeLift1.setPosition(Positions.OUTTAKE_LIFT1_UP);
//                outtakeLift2.setPosition(Positions.OUTTAKE_LIFT2_UP);
//            }

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
                outtakeLift.liftUp(),
                new SleepAction(.25),
                outtakeClaw.closeClaw()
        );
    }

    public Action prepIntake() {
        return new ParallelAction(
                intakeLift.liftDown(),
                intakeStopper.raiseStopper()
        );
    }


}


