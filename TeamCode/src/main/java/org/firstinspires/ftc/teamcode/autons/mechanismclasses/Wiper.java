package org.firstinspires.ftc.teamcode.autons.mechanismclasses;

import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.Positions;

public class Wiper {
    private Servo wiper;

    public Wiper(HardwareMap hardwareMap) {
        this.wiper = hardwareMap.get(Servo.class, "wiper");
    }

    public class OpenWiper implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            wiper.setPosition(Positions.WIPER_OPEN);
            return false;
        }
    }

    public Action openWiper() {
        return new OpenWiper();
    }

    public class CloseWiper implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            wiper.setPosition(Positions.WIPER_CLOSED);
            return false;
        }
    }

    public Action closeWiper() {
        return new CloseWiper();
    }
}
