package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.Positions;

@TeleOp(name="Teleop2025")
public class Teleop2024 extends LinearOpMode {

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

        Servo outtakeClaw = hardwareMap.servo.get("VClaw");
        Servo outtakeLift1 = hardwareMap.servo.get("SlidePivot1");
        Servo outtakeLift2 = hardwareMap.servo.get("SlidePivot2");
        DcMotor vslide1 = hardwareMap.dcMotor.get("VSlide1");
        DcMotor vslide2 = hardwareMap.dcMotor.get("VSlide2");

        waitForStart();

        if (isStopRequested()) return;

        // default positions and init
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        intakeLift1.setPosition(Positions.INTAKE_LIFT1_UP);
        intakeLift2.setPosition(Positions.INTAKE_LIFT2_UP);
        extension1.setPosition(Positions.EXTENSION1_IN);
        extension2.setPosition(Positions.EXTENSION2_IN);

        outtakeClaw.setPosition(Positions.OUTTAKE_CLAW_OPENED);
        outtakeLift1.setPosition(Positions.OUTTAKE_LIFT1_DOWN);
        outtakeLift2.setPosition(Positions.OUTTAKE_LIFT2_DOWN);

        while (opModeIsActive() & !isStopRequested()) {
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x; //* 1.1; // Counteract imperfect strafing
            double rx = -gamepad1.right_stick_x;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower, backLeftPower, frontRightPower, backRightPower;

            frontLeftPower = (y - x - rx) / denominator;
            backLeftPower = (y + x - rx) / denominator;
            frontRightPower = (y - x + rx) / denominator;
            backRightPower = (y + x + rx) / denominator;

            frontLeftMotor.setPower(frontLeftPower * Positions.SPEED_MODIFIER);
            backLeftMotor.setPower(backLeftPower * Positions.SPEED_MODIFIER);
            frontRightMotor.setPower(frontRightPower * Positions.SPEED_MODIFIER);
            backRightMotor.setPower(backRightPower * Positions.SPEED_MODIFIER);

            // right buttons
            if (gamepad2.a) { // pick up
                intakeLift1.setPosition(Positions.INTAKE_LIFT1_DOWN);
                intakeLift2.setPosition(Positions.INTAKE_LIFT2_DOWN);

            }


            if (gamepad2.b) { // toggle intake lift
                intakeLift1.setPosition(Positions.INTAKE_LIFT1_UP);
                intakeLift2.setPosition(Positions.INTAKE_LIFT2_UP);
                //FORWARD = !FORWARD;
            }
            if (gamepad1.x) { // transfer  ,  ps4 control; square
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
            if (gamepad2.y) { // drop or hang
//                FORWARD = true;
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
            }
            if (gamepad2.dpad_left) { // extend in
                extension1.setPosition(Positions.EXTENSION1_OUT);
                extension2.setPosition(Positions.EXTENSION2_OUT);
            }
            if (gamepad2.dpad_right) { // extend out
                extension1.setPosition(Positions.EXTENSION1_IN);
                extension2.setPosition(Positions.EXTENSION2_IN);
            }

            // bumper - active intake
            if (gamepad2.left_bumper) { // intake
                intakeStopper.setPosition(Positions.INTAKE_STOPPER_UP);
                activeIntake.setPower(Positions.INTAKE_POWER);
                sleep(Positions.INTAKE_DURATION);
                activeIntake.setPower(0);
            }
            if (gamepad2.right_bumper) { // release
                intakeStopper.setPosition(Positions.INTAKE_STOPPER_DOWN);
                activeIntake.setPower(-Positions.RELEASE_POWER);
                sleep(Positions.RELEASE_DURATION);
                activeIntake.setPower(0);
            }
            if (gamepad1.a) { // hang
                vslide1.setPower(-Positions.VSLIDE_POWER);
                vslide2.setPower(Positions.VSLIDE_POWER);
                sleep(5000);
                vslide1.setPower(0);
                vslide2.setPower(0);
            }
            if (gamepad1.b) { // hang
                vslide1.setPower(-Positions.VSLIDE_POWER);
                vslide2.setPower(Positions.VSLIDE_POWER);
                sleep(10000);
                vslide1.setPower(0);
                vslide2.setPower(0);
            }
        }
    }

}