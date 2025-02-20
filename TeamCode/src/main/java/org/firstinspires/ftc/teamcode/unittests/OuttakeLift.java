package org.firstinspires.ftc.teamcode.unittests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.Positions;

@TeleOp(name="Outtake Lift Unit Test")
public class OuttakeLift extends LinearOpMode {

    public void runOpMode() throws InterruptedException {
        // configure all motors and servos
        Servo outtakeLift1 = hardwareMap.servo.get("SlidePivot1");
        Servo outtakeLift2 = hardwareMap.servo.get("SlidePivot2");

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive() & !isStopRequested()) {
            // default positions and init
            if (gamepad1.a) {
                outtakeLift1.setPosition(Positions.OUTTAKE_LIFT1_UP);
                outtakeLift2.setPosition(Positions.OUTTAKE_LIFT2_UP);
            }
            if (gamepad1.b) {
                outtakeLift1.setPosition(Positions.OUTTAKE_LIFT1_DOWN);
                outtakeLift2.setPosition(Positions.OUTTAKE_LIFT2_DOWN);
            }
            if (gamepad1.x) {
                outtakeLift1.setPosition(Positions.OUTTAKE_LIFT1_FORWARD);
                outtakeLift2.setPosition(Positions.OUTTAKE_LIFT2_FORWARD);
            }
        }
    }

}