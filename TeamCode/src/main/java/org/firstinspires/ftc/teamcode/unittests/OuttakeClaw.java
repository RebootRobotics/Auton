package org.firstinspires.ftc.teamcode.unittests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.Positions;

@TeleOp(name="Outtake Claw")
public class OuttakeClaw extends LinearOpMode {

    public void runOpMode() throws InterruptedException {
        // configure all motors and servos
        Servo outtakeClaw = hardwareMap.servo.get("VClaw");

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive() & !isStopRequested()) {
            // default positions and init
            if (gamepad1.a) {
                outtakeClaw.setPosition(Positions.OUTTAKE_CLAW_OPENED);
            }
            if (gamepad1.b) {
                outtakeClaw.setPosition(Positions.OUTTAKE_CLAW_CLOSED);
            }
        }
    }

}