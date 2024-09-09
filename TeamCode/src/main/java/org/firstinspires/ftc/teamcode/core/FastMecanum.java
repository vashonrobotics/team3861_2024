package org.firstinspires.ftc.teamcode.core;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

// for high-speed, high-precision teleop
public class FastMecanum extends MecanumDrive {
    private Vector4 prevPower = new Vector4();
    private ElapsedTime t;
    public static final double counterForce = 0.5;

    public FastMecanum(DcMotor a, DcMotor b, DcMotor c, DcMotor d) {
        super(a, b, c, d);
    }

    @Override
    public void init() {
        t.reset();
        for(int i = 0; i < 4; i++) {
            motors[i].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
    }

    @Override
    public void setPower(Vector4 power) {
        if(t.seconds() > 1) {
            super.setPower(power);
            return;
        }
        super.setPower(power.add(power.sub(prevPower).mul(counterForce)));
        t.reset();
    }
}
