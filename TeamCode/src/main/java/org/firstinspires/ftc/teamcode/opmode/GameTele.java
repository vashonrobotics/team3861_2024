package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.*;
import org.firstinspires.ftc.teamcode.core.*;

@TeleOp(name="TeleOp", group="Linear Opmode")
@Disabled
public class GameTele extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private FastMecanum drive;

    @Override
    public void runOpMode() {
        drive = new FastMecanum(
        hardwareMap.get(DcMotor.class, "left_front_drive"),
        hardwareMap.get(DcMotor.class, "right_front_drive"),
                hardwareMap.get(DcMotor.class, "left_back_drive"),
        hardwareMap.get(DcMotor.class, "right_back_drive")
        );

        Button dampenRoll = new Button();

        drive.setDirection(HardwareConstants.driveDirs);

        // Wait for the game to start (driver presses PLAY)
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            double axial   = -gamepad1.left_stick_y;  // pushing stick forward gives negative value
            double lateral =  gamepad1.left_stick_x;
            double yaw     =  gamepad1.right_stick_x;

            drive.setPower(lateral, axial, yaw);

            dampenRoll.update(gamepad1.right_bumper);
            if(dampenRoll.pressed())
                drive.dampenRoll = !drive.dampenRoll;
            telemetry.addData("Roll Dampening", drive.dampenRoll ? "on" : "off");
            telemetry.update();
        }
    }}
