package org.firstinspires.ftc.teamcode.autons.mechanismclasses;

import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.Positions;

public class ActiveIntake {
    private DcMotor activeIntake;

    public ActiveIntake(HardwareMap hardwareMap) {
        this.activeIntake = hardwareMap.get(DcMotor.class, "activeIntake");
    }

    public class Intake implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            activeIntake.setPower(Positions.INTAKE_POWER);
            return false;
        }
    }

    public Action intake() {
        return new Intake();
    }

    public class Release implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            activeIntake.setPower(-Positions.RELEASE_POWER);
            return false;
        }
    }

    public Action release() {
        return new Release();
    }

    public class StopIntake implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            activeIntake.setPower(0);
            return false;
        }
    }

    public Action stopIntake() {
        return new StopIntake();
    }
}
