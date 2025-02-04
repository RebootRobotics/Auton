package org.firstinspires.ftc.teamcode.autons.mechanismclasses;

import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.Positions;

public class Extension {
    private Servo extension1;
    private Servo extension2;

    public Extension(HardwareMap hardwareMap) {
        this.extension1 = hardwareMap.get(Servo.class, "extension1");
        this.extension2 = hardwareMap.get(Servo.class, "extension2");
    }

    public class ExtendOut implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            extension1.setPosition(Positions.EXTENSION1_OUT);
            extension2.setPosition(Positions.EXTENSION2_OUT);
            return false;
        }
    }

    public Action extendOut() {
        return new ExtendOut();
    }

    public class ExtendIn implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            extension1.setPosition(Positions.EXTENSION1_IN);
            extension2.setPosition(Positions.EXTENSION2_IN);
            return false;
        }
    }

    public Action extendIn() {
        return new ExtendIn();
    }
}
