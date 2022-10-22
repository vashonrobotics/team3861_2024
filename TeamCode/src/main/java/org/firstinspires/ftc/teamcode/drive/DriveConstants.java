package org.firstinspires.ftc.teamcode.drive;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@Config
public class DriveConstants {

    public static final double TICKS_PER_REV = 383.6;
    public static final double MAX_RPM = 435;


    public static final boolean RUN_USING_ENCODER = true;
    public static PIDFCoefficients MOTOR_VELO_PID = new PIDFCoefficients(17, 0, 7,
            getMotorVelocityF(MAX_RPM / 61 * TICKS_PER_REV));


    public static final double WHEEL_RADIUS = 1.9685; // in
    public static final double GEAR_RATIO = 0.5; // output (wheel) speed / input (motor) speed
    public static double TRACK_WIDTH = 15.36; // in


    public static double kV = 1.0 / rpmToVelocity(MAX_RPM);
    public static double kA = 0;
    public static double kStatic = 0;


    public static double MAX_VEL = 35.5973474612; // 95 % of MaxVelocity from RoadRunner OpMode
    public static double MAX_ACCEL = 38.110287416570166;
    public static double MAX_ANG_VEL = 4.774648435052468;
    public static double MAX_ANG_ACCEL = Math.toRadians(140.87475);


    public static double TRANSLATION_kP = 0.898753;
    public static double TRANSLATION_kI = 0;
    public static double TRANSLATION_kD = 0;

    public static double HEADING_kP = 0.22;
    public static double HEADING_kI = 0;
    public static double HEADING_kD = 0;


    public static double encoderTicksToInches(double ticks) {
        return WHEEL_RADIUS * 2 * Math.PI * GEAR_RATIO * ticks / TICKS_PER_REV;
    }

    public static double rpmToVelocity(double rpm) {
        return rpm * GEAR_RATIO * 2 * Math.PI * WHEEL_RADIUS / 60.0;
    }

    public static double getMotorVelocityF(double ticksPerSecond) {
        // see https://docs.google.com/document/d/1tyWrXDfMidwYyP_5H4mZyVgaEswhOC35gvdmP-V-5hA/edit#heading=h.61g9ixenznbx
        return 32767 / ticksPerSecond;
    }
}