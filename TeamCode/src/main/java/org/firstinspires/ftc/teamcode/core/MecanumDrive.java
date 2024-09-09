package org.firstinspires.ftc.teamcode.core;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;

// base mecanum class - works for basic teleop, but please extend for specific functionality
public class MecanumDrive {
    protected DcMotor[] motors;

    public static final Vector4 forward = new Vector4(1);
    public static final Vector4 right = new Vector4(-1, 1, 1, -1);
    public static final Vector4 clockwise = new Vector4(1, -1, 1, -1);

    public double powerMultiplier = 0.5;

    public MecanumDrive() {
        motors = new DcMotor[4];
    }

    public MecanumDrive(DcMotor a, DcMotor b, DcMotor c, DcMotor d) {
        this();
        motors[0] = a;
        motors[1] = b;
        motors[2] = c;
        motors[3] = d;
    }

    public MecanumDrive(DcMotor[] motors) {
        this.motors = motors;
    }

    public void setPowerRaw(@NonNull Vector4 power) {
        for(int i = 0; i < 4; i++) {
            motors[i].setPower(power.get(i));
        }
    }

    public void setPower(@NonNull Vector4 power) {
        setPowerRaw(power.mul(powerMultiplier));
    }

    public void setDirection(Direction[] dirs) {
        for(int i = 0; i < 4; i++) {
            motors[i].setDirection(dirs[i]);
        }
    }

    public void setPower(double x, double y, double yaw) {
        setPower(right.mul(x).add(forward.mul(y).add(clockwise.mul(yaw))));
    }

    public Vector4 getPosition() {
        Vector4 ret = new Vector4();
        for(int i = 0; i < 4; i++) {
            ret.set(i, motors[i].getCurrentPosition());
        }
        return ret;
    }

    public void init() {} // virtual
}
