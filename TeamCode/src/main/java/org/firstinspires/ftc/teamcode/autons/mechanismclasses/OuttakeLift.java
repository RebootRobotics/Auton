package org.firstinspires.ftc.teamcode.autons.mechanismclasses;

import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.Positions;

public class OuttakeLift {
    Servo outtakeLift1;
    Servo outtakeLift2;

    public OuttakeLift(HardwareMap hardwareMap) {
        this.outtakeLift1 = hardwareMap.get(Servo.class, "SlidePivot1");
        this.outtakeLift2 = hardwareMap.get(Servo.class, "SlidePivot2");
    }

    public class LiftUp implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            outtakeLift1.setPosition(Positions.OUTTAKE_LIFT1_UP);
            outtakeLift2.setPosition(Positions.OUTTAKE_LIFT2_UP);
            return false;
        }
    }

    public Action liftUp() {
        return new LiftUp();
    }

    public class LiftDown implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            outtakeLift1.setPosition(Positions.OUTTAKE_LIFT1_DOWN);
            outtakeLift2.setPosition(Positions.OUTTAKE_LIFT2_DOWN);
            return false;
        }
    }

    public Action liftDown() {
        return new LiftDown();
    }
}