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
    private MecanumDrive drive;

    @Override
    public void runOpMode() {
        drive = new MecanumDrive(
        hardwareMap.get(DcMotor.class, "left_front_drive"),
        hardwareMap.get(DcMotor.class, "right_front_drive"),
                hardwareMap.get(DcMotor.class, "left_back_drive"),
        hardwareMap.get(DcMotor.class, "right_back_drive")
        );

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

            Vector4 movement =
                    MecanumDrive.forward.mul(axial).add(
                    MecanumDrive.right.mul(lateral)).add(
                            MecanumDrive.clockwise.mul(yaw * HardwareConstants.yawSens)).cap(1);

            // This is test code:
            //
            // Uncomment the following code to test your motor directions.
            // Each button should make the corresponding motor run FORWARD.
            //   1) First get all the motors to take to correct positions on the robot
            //      by adjusting your Robot Configuration if necessary.
            //   2) Then make sure they run in the correct direction by modifying the
            //      the setDirection() calls above.
            // Once the correct motors move in the correct direction re-comment this code.

            /*
            leftFrontPower  = gamepad1.x ? 1.0 : 0.0;  // X gamepad
            leftBackPower   = gamepad1.a ? 1.0 : 0.0;  // A gamepad
            rightFrontPower = gamepad1.y ? 1.0 : 0.0;  // Y gamepad
            rightBackPower  = gamepad1.b ? 1.0 : 0.0;  // B gamepad
            */
        }
    }}
