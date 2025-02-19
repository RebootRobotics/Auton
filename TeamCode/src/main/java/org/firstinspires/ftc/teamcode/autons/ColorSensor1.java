package org.firstinspires.ftc.teamcode.autons;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

@Autonomous(name = "ColorSensor")
public class ColorSensor1 extends LinearOpMode {

    private ColorSensor colorsensor;
    private ColorSensor colorsensor_REV_ColorRangeSensor;
    public void runOpMode() {
        double count;
        colorsensor = hardwareMap.get(ColorSensor.class, "colorsensor");
        colorsensor_REV_ColorRangeSensor = hardwareMap.get(ColorSensor.class, "colorsensor");

// Put initialization blocks here.
        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {
                // Put loop blocks here.
                while (true) {
                    telemetry.addData("Blue: ", colorsensor.blue());
                    telemetry.addData("Green: ", colorsensor.green());
                    telemetry.addData("Red: ", colorsensor.red());
                    telemetry.addData("Light: ", ((OpticalDistanceSensor) colorsensor_REV_ColorRangeSensor).getLightDetected());
                    telemetry.addData("NormalizedColors:", ((NormalizedColorSensor) colorsensor).getNormalizedColors());
                    telemetry.addData("ToText:", colorsensor.toString());
                    //telemetry.addData("Count:", count);
                    //count += 1;
                    telemetry.update();
                }
            }
        }
    }
}