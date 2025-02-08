package org.firstinspires.ftc.teamcode.unittests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.Positions;

@TeleOp(name="Wheels")
public class Wheels extends LinearOpMode {

    public void runOpMode() throws InterruptedException {
        // configure all motors and servos
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("upperLeft");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("lowerLeft");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("upperRight");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("lowerRight");

        waitForStart();

        if (isStopRequested()) return;

        // default positions and init
//        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
//        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        while (opModeIsActive() & !isStopRequested()) {
            // right buttons
            if (gamepad1.a) {
                frontLeftMotor.setPower(1);
                sleep(250);
                frontLeftMotor.setPower(0);
            }

            if (gamepad1.b) {
                backLeftMotor.setPower(1);
                sleep(250);
                backLeftMotor.setPower(0);
            }

            if (gamepad1.x) {
                frontRightMotor.setPower(1);
                sleep(250);
                frontRightMotor.setPower(0);
            }

            if (gamepad1.y) {
                backRightMotor.setPower(1);
                sleep(250);
                backRightMotor.setPower(0);
            }
        }
    }

}