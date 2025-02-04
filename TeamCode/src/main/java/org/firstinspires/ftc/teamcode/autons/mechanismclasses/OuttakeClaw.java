package org.firstinspires.ftc.teamcode.autons.mechanismclasses;

import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.Positions;

public class OuttakeClaw {
    Servo outtakeClaw;

    public OuttakeClaw(HardwareMap hardwareMap) {
        this.outtakeClaw = hardwareMap.get(Servo.class,"VClaw");
    }

    public class OpenClaw implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            outtakeClaw.setPosition(Positions.OUTTAKE_CLAW_OPENED);
            return false;
        }
    }

    public Action openClaw() {
        return new OpenClaw();
    }

    public class CloseClaw implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            outtakeClaw.setPosition(Positions.OUTTAKE_CLAW_CLOSED);
            return false;
        }
    }

    public Action closeClaw() {
        return new CloseClaw();
    }

}