package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

@SuppressWarnings("unused") // This looks nice and reduces the amount of warnings the code gives
public class Carosel {


    public double Power = 1;

    private final DcMotor caroselMotor;
    // public DigitalChannel digitalTouch;

    public Carosel(HardwareMap HardwareMap) {
        caroselMotor = HardwareMap.get(DcMotorEx.class, "caroselMotor");
        // digitalTouch = HardwareMap.get(DigitalChannel.class, "sensor_digital");

        caroselMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        caroselMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // digitalTouch.setMode(DigitalChannel.Mode.INPUT);
    }


    public void PowerOn() {
        caroselMotor.setPower(Power);
    }

    public void PowerOff() {
        caroselMotor.setPower(0);
    }

    public void setDirection(DcMotorSimple.Direction direction) {
        caroselMotor.setDirection(direction);
    }
    public void IncreasePower() {
        if (Power <= 0.90) {
            Power += 0.10;
            caroselMotor.setPower(Power);
        }
    }

    public void DecreasePower() {
        if (Power >= 0.10) {
            Power -= 0.10;
            caroselMotor.setPower(Power);
        }
    }


    //for while the op mode is active (in teleop, etc)
    //if (digitalTouch.getState() == true) {
    //                telemetry.addData("Digital Touch", "Is Not Pressed");
    //            } else {
    //                telemetry.addData("Digital Touch", "Is Pressed");
    //            }
    //
    //            telemetry.update();
}



