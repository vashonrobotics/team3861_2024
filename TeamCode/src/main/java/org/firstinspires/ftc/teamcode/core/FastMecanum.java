package org.firstinspires.ftc.teamcode.core;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotor.RunMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.HardwareConstants;

// for high-speed, high-precision teleop
public class FastMecanum extends MecanumDrive {
    private Vector4 lastPos = new Vector4();
    private ElapsedTime t;
    public static final double counterForce = 0.5;
    public boolean dampenRoll = true;

    public FastMecanum(DcMotor a, DcMotor b, DcMotor c, DcMotor d) {
        super(a, b, c, d);
    }

    @Override
    public void init() {
        t.reset();
        for(int i = 0; i < 4; i++) {
            DcMotor m = motors[i];
            m.setMode(RunMode.STOP_AND_RESET_ENCODER);
            m.setMode(RunMode.RUN_WITHOUT_ENCODER);
            m.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }
    }

    @Override
    public void setPower(Vector4 power) {
        double elapsed = t.milliseconds();
        Vector4 pos = getPosition();

        if(elapsed > HardwareConstants.motionThreshold || !dampenRoll) {
            super.setPower(power);
        }
        else {
            Vector4 vel = pos.sub(lastPos).mul(1/elapsed); // counts per millisecond
            super.setPower(power.sub(vel.mul(counterForce)));
        }
        lastPos = pos;
        t.reset();
    }
}
