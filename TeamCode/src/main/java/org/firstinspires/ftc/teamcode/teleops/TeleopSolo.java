package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Positions;

@TeleOp(name="SOLO TELEOP")
public class TeleopSolo extends LinearOpMode {

    public void runOpMode() throws InterruptedException {
        // configure all motors and servos
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("upperLeft");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("lowerLeft");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("upperRight");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("lowerRight");

        DcMotor activeIntake = hardwareMap.dcMotor.get("activeIntake");
        Servo intakeStopper = hardwareMap.servo.get("stopper");
        Servo intakeLift1 = hardwareMap.servo.get("lift1");
        Servo intakeLift2 = hardwareMap.servo.get("lift2");
        Servo extension1 = hardwareMap.servo.get("extension1");
        Servo extension2 = hardwareMap.servo.get("extension2");
        Servo wiper = hardwareMap.servo.get("wiper");

        Servo outtakeClaw = hardwareMap.servo.get("VClaw");
        Servo outtakeLift1 = hardwareMap.servo.get("SlidePivot1");
        Servo outtakeLift2 = hardwareMap.servo.get("SlidePivot2");
        DcMotor vslide1 = hardwareMap.dcMotor.get("VSlide1");
        DcMotor vslide2 = hardwareMap.dcMotor.get("VSlide2");

        // set up timers
        ElapsedTime transferTimer;

        waitForStart();

        if (isStopRequested()) return;

        // default positions and init
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        vslide1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        vslide2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intakeLift1.setPosition(Positions.INTAKE_LIFT1_UP);
        intakeLift2.setPosition(Positions.INTAKE_LIFT2_UP);
        extension1.setPosition(Positions.EXTENSION1_IN);
        extension2.setPosition(Positions.EXTENSION2_IN);
        wiper.setPosition(Positions.WIPER_CLOSED);

        outtakeClaw.setPosition(Positions.OUTTAKE_CLAW_OPENED);
        outtakeLift1.setPosition(Positions.OUTTAKE_LIFT1_DOWN);
        outtakeLift2.setPosition(Positions.OUTTAKE_LIFT2_DOWN);

        while (opModeIsActive() & !isStopRequested()) {
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x; //* 1.1; // Counteract imperfect strafing
            double rx = -gamepad1.right_stick_x;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower, backLeftPower, frontRightPower, backRightPower;

            frontLeftPower = (y + x * Positions.STRAFE_MODIFIER - rx) / denominator;
            backLeftPower = (y - x * Positions.STRAFE_MODIFIER - rx) / denominator;
            frontRightPower = (y - x * Positions.STRAFE_MODIFIER + rx) / denominator;
            backRightPower = (y + x * Positions.STRAFE_MODIFIER + rx) / denominator;

            frontLeftMotor.setPower(frontLeftPower * Positions.SPEED_MODIFIER);
            backLeftMotor.setPower(backLeftPower * Positions.SPEED_MODIFIER);
            frontRightMotor.setPower(frontRightPower * Positions.SPEED_MODIFIER);
            backRightMotor.setPower(backRightPower * Positions.SPEED_MODIFIER);

            // right buttons
            if (gamepad1.a) { // move intake lift down
                intakeLift1.setPosition(Positions.INTAKE_LIFT1_DOWN);
                intakeLift2.setPosition(Positions.INTAKE_LIFT2_DOWN);
            }
            if (gamepad1.b) { // move intake lift up
                intakeLift1.setPosition(Positions.INTAKE_LIFT1_UP);
                intakeLift2.setPosition(Positions.INTAKE_LIFT2_UP);
            }
            if (gamepad1.x) { // transfer
                outtakeLift1.setPosition(Positions.OUTTAKE_LIFT1_DOWN);
                outtakeLift2.setPosition(Positions.OUTTAKE_LIFT2_DOWN);
                outtakeClaw.setPosition(Positions.OUTTAKE_CLAW_OPENED);
                transferTimer = new ElapsedTime();
                if (transferTimer.milliseconds() > 250) {
                    transferTimer.reset();
                    extension1.setPosition(Positions.EXTENSION1_IN);
                    extension2.setPosition(Positions.EXTENSION2_IN);
                    intakeLift1.setPosition(Positions.INTAKE_LIFT1_UP);
                    intakeLift2.setPosition(Positions.INTAKE_LIFT2_UP);
                    final double extensionSleep;
                    if (extension1.getPosition() < Positions.EXTENSION1_IN && extension2.getPosition() > Positions.EXTENSION2_IN) {
                        extensionSleep = 500;
                    } else {
                        extensionSleep = 250;
                    }
                    if (transferTimer.milliseconds() > 250 + extensionSleep) {
                        intakeStopper.setPosition(Positions.INTAKE_STOPPER_DOWN);
                        if (transferTimer.milliseconds() > 500 + extensionSleep) {
                            outtakeClaw.setPosition(Positions.OUTTAKE_CLAW_CLOSED);
                            if (transferTimer.milliseconds() > 1000 + extensionSleep) {
                                outtakeLift1.setPosition(Positions.OUTTAKE_LIFT1_UP);
                                outtakeLift2.setPosition(Positions.OUTTAKE_LIFT2_UP);
                            }
                        }
                    }
                }
            }
            if (gamepad1.y) { // drop or hang
                outtakeClaw.setPosition(Positions.OUTTAKE_CLAW_OPENED);
                sleep(500);
                outtakeLift1.setPosition(Positions.OUTTAKE_LIFT1_DOWN);
                outtakeLift2.setPosition(Positions.OUTTAKE_LIFT2_DOWN);
            }

            // dpad - vslide and extension
            if (gamepad1.dpad_up) {
                vslide1.setPower(Positions.VSLIDE_POWER);
                vslide2.setPower(-Positions.VSLIDE_POWER);
                sleep(Positions.VSLIDE_DURATION);
                vslide1.setPower(0);
                vslide2.setPower(0);
            }
            if (gamepad1.dpad_down) {
                vslide1.setPower(-Positions.VSLIDE_POWER);
                vslide2.setPower(Positions.VSLIDE_POWER);
                sleep(Positions.VSLIDE_DURATION);
                vslide1.setPower(0);
                vslide2.setPower(0);
                if (gamepad1.a) { // hang
                    vslide1.setPower(-Positions.VSLIDE_POWER);
                    vslide2.setPower(Positions.VSLIDE_POWER);
                    sleep(20000);
                    vslide1.setPower(0);
                    vslide2.setPower(0);
                    vslide1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                    vslide2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                }
            }
            if (gamepad1.dpad_left) { // extend in
                extension1.setPosition(Positions.EXTENSION1_OUT);
                extension2.setPosition(Positions.EXTENSION2_OUT);
            }
            if (gamepad1.dpad_right) { // extend out
                extension1.setPosition(Positions.EXTENSION1_IN);
                extension2.setPosition(Positions.EXTENSION2_IN);
            }

            // bumper - active intake
            if (gamepad1.left_bumper) { // intake
                intakeStopper.setPosition(Positions.INTAKE_STOPPER_UP);
                activeIntake.setPower(Positions.INTAKE_POWER);
                sleep(Positions.INTAKE_DURATION);
                activeIntake.setPower(0);
            }
            if (gamepad1.right_bumper) { // release
                intakeStopper.setPosition(Positions.INTAKE_STOPPER_DOWN);
                activeIntake.setPower(-Positions.RELEASE_POWER);
                sleep(Positions.RELEASE_DURATION);
                activeIntake.setPower(0);
            }

            if (gamepad2.a) {
                wiper.setPosition(Positions.WIPER_OPEN);
            }
            if (gamepad2.b) {
                wiper.setPosition(Positions.WIPER_CLOSED);
            }
            // old transfer
            if (gamepad2.x) {
//                FORWARD = false;
//                Positions.SPEED_MODIFIER = 0.6;
                outtakeLift1.setPosition(Positions.OUTTAKE_LIFT1_DOWN);
                outtakeLift2.setPosition(Positions.OUTTAKE_LIFT2_DOWN);
                outtakeClaw.setPosition(Positions.OUTTAKE_CLAW_OPENED);
                sleep(250);
                extension1.setPosition(Positions.EXTENSION1_IN);
                extension2.setPosition(Positions.EXTENSION2_IN);
                intakeLift1.setPosition(Positions.INTAKE_LIFT1_UP);
                intakeLift2.setPosition(Positions.INTAKE_LIFT2_UP);
                sleep(500);
                intakeStopper.setPosition(Positions.INTAKE_STOPPER_DOWN);
                sleep(250);
                outtakeClaw.setPosition(Positions.OUTTAKE_CLAW_CLOSED);
                sleep(1000);
                outtakeLift1.setPosition(Positions.OUTTAKE_LIFT1_UP);
                outtakeLift2.setPosition(Positions.OUTTAKE_LIFT2_UP);
            }
        }
    }

}