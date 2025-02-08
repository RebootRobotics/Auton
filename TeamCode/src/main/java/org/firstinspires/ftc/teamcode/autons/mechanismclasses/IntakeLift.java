package org.firstinspires.ftc.teamcode.autons.mechanismclasses;

import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.Positions;

public class IntakeLift {
    Servo intakeLift1;
    Servo intakeLift2;

    public IntakeLift(HardwareMap hardwareMap) {
        this.intakeLift1 = hardwareMap.get(Servo.class, "lift1");
        this.intakeLift2 = hardwareMap.get(Servo.class, "lift2");
    }

    public class LiftUp implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            intakeLift1.setPosition(Positions.INTAKE_LIFT1_UP);
            intakeLift2.setPosition(Positions.INTAKE_LIFT2_UP);
            return false;
        }
    }

    public Action liftUp() {
        return new LiftUp();
    }

    public class LiftDown implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            intakeLift1.setPosition(Positions.INTAKE_LIFT1_DOWN);
            intakeLift2.setPosition(Positions.INTAKE_LIFT2_DOWN);
            return false;
        }
    }

    public Action liftDown() {
        return new LiftDown();
    }
}