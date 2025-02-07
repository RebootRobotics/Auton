package org.firstinspires.ftc.teamcode.unittests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.Positions;

@TeleOp(name="Drive Train")
public class DriveTrain extends LinearOpMode {

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

            if (gamepad2.a) {
                frontLeftMotor.setPower(1);
                backLeftMotor.setPower(-1);
                frontRightMotor.setPower(1);
                backRightMotor.setPower(-1);
                sleep(500);
                frontLeftMotor.setPower(0);
                backLeftMotor.setPower(0);
                frontRightMotor.setPower(0);
                backRightMotor.setPower(0);
            }
        }
    }

}