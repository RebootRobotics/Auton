package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.Positions;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
@TeleOp(name="Teleop2025Blue")
public class Teleop2024Blue extends LinearOpMode {

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

        Servo wiper = hardwareMap.servo.get("wiper");

        ColorSensor colorsensor = hardwareMap.get(ColorSensor.class, "colorsensor");
        ColorSensor colorsensor_REV_ColorRangeSensor = hardwareMap.get(ColorSensor.class, "colorsensor");

        waitForStart();

        if (isStopRequested()) return;

        // default positions and init
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        intakeLift1.setPosition(Positions.INTAKE_LIFT1_UP);
        intakeLift2.setPosition(Positions.INTAKE_LIFT2_UP);

        extension2.setPosition(Positions.EXTENSION2_IN);

        outtakeClaw.setPosition(Positions.OUTTAKE_CLAW_OPENED);
        outtakeLift1.setPosition(Positions.OUTTAKE_LIFT1_DOWN);
        outtakeLift2.setPosition(Positions.OUTTAKE_LIFT2_DOWN);
        wiper.setPosition(Positions.WIPER_CLOSE);
//        wiper.setPosition(0.5);
        vslide1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        vslide2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        while (opModeIsActive() & !isStopRequested()) {
            double y = gamepad1.left_stick_y; // Remember, Y stick value is reversed
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
            if (gamepad1.a) { // intake lift down
                intakeLift1.setPosition(Positions.INTAKE_LIFT1_DOWN);
                intakeLift2.setPosition(Positions.INTAKE_LIFT2_DOWN);

            }
            if (gamepad1.b) { // intake lift up
                intakeLift1.setPosition(Positions.INTAKE_LIFT1_UP);
                intakeLift2.setPosition(Positions.INTAKE_LIFT2_UP);
                //FORWARD = !FORWARD;
            }
            if (gamepad2.x) { // transfer
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

            if (gamepad2.a) { // open outtake claw
                outtakeClaw.setPosition(Positions.OUTTAKE_CLAW_OPENED);
            }
            if (gamepad2.b) {
                outtakeClaw.setPosition(Positions.OUTTAKE_CLAW_CLOSED);
            }

            // dpad - vslide and extension
            if (gamepad2.dpad_up) {
                vslide1.setTargetPosition(1);
                vslide2.setTargetPosition(1);
                //value of how far up you want it to go

                vslide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                vslide2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                vslide2.setPower(0.5);
                vslide1.setPower(0.5);
                while(vslide1.isBusy() && vslide2.isBusy() && opModeIsActive()) {

                }
                vslide1.setPower(0);
                vslide2.setPower(0);

                /*vslide1.setPower(Positions.VSLIDE_POWER);
                vslide2.setPower(-Positions.VSLIDE_POWER);
                sleep(Positions.VSLIDE_DURATION);
                vslide1.setPower(0);
                vslide2.setPower(0);*/
            }
            if (gamepad2.dpad_down) {
                vslide1.setTargetPosition(1);
                vslide2.setTargetPosition(1);
                //value of how far up you want it to go

                vslide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                vslide2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                vslide2.setPower(0.5);
                vslide1.setPower(0.5);
                while(vslide1.isBusy() && vslide2.isBusy() && opModeIsActive()) {

                }
                vslide1.setPower(0);
                vslide2.setPower(0);
                /*vslide1.setPower(-Positions.VSLIDE_POWER);
                vslide2.setPower(Positions.VSLIDE_POWER);
                sleep(Positions.VSLIDE_DURATION);
                vslide1.setPower(0);
                vslide2.setPower(0);*/
            }
            if (gamepad1.dpad_left) { // extend out
                extension1.setPosition(Positions.EXTENSION1_OUT);
                extension2.setPosition(Positions.EXTENSION2_OUT);
            }
            if (gamepad1.dpad_right) { // extend in
                extension1.setPosition(Positions.EXTENSION1_IN);
                extension2.setPosition(Positions.EXTENSION2_IN);
            }
            if (gamepad2.dpad_left) { // extend out
                if (gamepad2.a) {
                    vslide1.setPower(-Positions.VSLIDE_POWER);
                    vslide2.setPower(Positions.VSLIDE_POWER);
                    sleep(30000);
                    vslide1.setPower(0);
                    vslide2.setPower(0);
                }
            }
            if (gamepad2.dpad_right) { // extend out
                if (gamepad2.a) {
                    vslide1.setPower(0);
                    vslide2.setPower(0);
                }
            }

            //arm rotation
            if (gamepad2.right_bumper){
                outtakeLift1.setPosition(Positions.OUTTAKE_LIFT1_UP_ALL);
                outtakeLift2.setPosition(Positions.OUTTAKE_LIFT2_UP_ALL);



            }

            if (gamepad2.left_bumper){
                outtakeLift1.setPosition(Positions.OUTTAKE_LIFT1_DOWN);
                outtakeLift2.setPosition(Positions.OUTTAKE_LIFT2_DOWN);


            }
            // bumper - active intake
            if (gamepad1.left_bumper) { // intake
                intakeStopper.setPosition(Positions.INTAKE_STOPPER_UP);
                activeIntake.setPower(Positions.INTAKE_POWER);
                sleep(Positions.INTAKE_DURATION);
                activeIntake.setPower(0);
            }
            while(colorsensor.red() > 150) {
                intakeStopper.setPosition(Positions.INTAKE_STOPPER_DOWN);
                activeIntake.setPower(-Positions.RELEASE_POWER);
                sleep(Positions.RELEASE_DURATION);
                activeIntake.setPower(0);
            }
            if (gamepad1.right_bumper) { // release
                intakeStopper.setPosition(Positions.INTAKE_STOPPER_DOWN);
                activeIntake.setPower(-Positions.RELEASE_POWER);
                sleep(Positions.RELEASE_DURATION);
                activeIntake.setPower(0);
            }
            if (gamepad1.x) {
                wiper.setPosition(Positions.WIPER_OPEN);
                sleep(750);
                wiper.setPosition(Positions.WIPER_CLOSE);

            }

            telemetry.addData("Blue: ", colorsensor.blue());
            telemetry.addData("Green: ", colorsensor.green());
            telemetry.addData("Red: ", colorsensor.red());
            telemetry.addData("Light: ", ((OpticalDistanceSensor) colorsensor_REV_ColorRangeSensor).getLightDetected());
            telemetry.addData("NormalizedColors:", ((NormalizedColorSensor) colorsensor).getNormalizedColors());
            telemetry.addData("ToText:", colorsensor.toString());
            //telemetry.addData("Count:", count);
            //count += 1;
            telemetry.update();


        }
    }

}