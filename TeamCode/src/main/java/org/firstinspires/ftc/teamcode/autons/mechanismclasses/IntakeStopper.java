package org.firstinspires.ftc.teamcode.autons.mechanismclasses;

import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.Positions;

public class IntakeStopper {
    private Servo intakeStopper;

    public IntakeStopper(HardwareMap hardwareMap) {
        this.intakeStopper = hardwareMap.get(Servo.class, "stopper");
    }

    public class RaiseStopper implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            intakeStopper.setPosition(Positions.INTAKE_STOPPER_UP);
            return false;
        }
    }

    public Action raiseStopper() {
        return new RaiseStopper();
    }

    public class LowerStopper implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            intakeStopper.setPosition(Positions.INTAKE_STOPPER_DOWN);
            return false;
        }
    }

    public Action lowerStopper() {
        return new LowerStopper();
    }
}
