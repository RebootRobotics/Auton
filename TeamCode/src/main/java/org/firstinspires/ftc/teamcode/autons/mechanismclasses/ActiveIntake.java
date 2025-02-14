package org.firstinspires.ftc.teamcode.autons.mechanismclasses;

import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
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
            activeIntake.setPower(Positions.INTAKE_POWER_AUTON);
            return false;
        }
    }

    public Action intake(double duration) {
        return new SequentialAction(
                new Intake(),
                new SleepAction(duration),
                new StopIntake()
        );
    }

    public class Release implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            activeIntake.setPower(-Positions.RELEASE_POWER);
            return false;
        }
    }

    public Action release(double duration) {
        return new SequentialAction(
                new Release(),
                new SleepAction(duration),
                new StopIntake()
        );
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
