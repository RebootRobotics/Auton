package org.firstinspires.ftc.teamcode.autons.mechanismclasses;

import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.Positions;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;

public class VSlide {
    private DcMotor vslide1;
    private DcMotor vslide2;

    public VSlide(HardwareMap hardwareMap) {
        this.vslide1 = hardwareMap.get(DcMotor.class, "VSlide1");
        this.vslide2 = hardwareMap.get(DcMotor.class, "VSlide2");
    }

    public class Raise implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            vslide1.setPower(Positions.VSLIDE_POWER);
            vslide2.setPower(-Positions.VSLIDE_POWER);
            return false;
        }
    }

    public Action raise(double duration) {
        return new SequentialAction(
                new Raise(),
                new SleepAction(duration),
                new StopVSlide()
        );
    }

    public class Lower implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            vslide1.setPower(-Positions.VSLIDE_POWER);
            vslide2.setPower(Positions.VSLIDE_POWER);
            return false;
        }
    }

    public Action lower(double duration) {
        return new SequentialAction(
                new Lower(),
                new SleepAction(duration),
                new StopVSlide()
        );
    }

    public class StopVSlide implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            vslide1.setPower(0);
            vslide2.setPower(0);
            return false;
        }
    }

    public Action stopVSlide() {
        return new StopVSlide();
    }
}
