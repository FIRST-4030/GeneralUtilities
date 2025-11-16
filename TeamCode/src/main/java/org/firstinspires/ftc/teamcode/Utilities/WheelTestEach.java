package org.firstinspires.ftc.teamcode.Utilities;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Description of WheelTestEach.
 *
 * @author Dennis O'Brien
 * @date 11/15/2025
 */
@TeleOp(name="Wheel Test Each", group="Util")
public class WheelTestEach extends OpMode
{
    private DcMotor leftBack, rightBack, leftFront, rightFront;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        // Initialize motors
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");

        // Set motor directions (adjust these based on your robot's configuration)
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.FORWARD);
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.FORWARD);

        // Set all motors to brake when power is zero
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit START
     */
    @Override
    public void init_loop() {}

    /*
     * Code to run ONCE when the driver hits START
     */
    @Override
    public void start() {}

    /*
     * Code to run REPEATEDLY after the driver hits START but before they hit STOP
     */
    @Override
    public void loop() {
        if (gamepad1.xWasPressed()) {
            leftFront.setPower(0.5);
            telemetry.addLine("Left Front");
        }

        if (gamepad1.xWasReleased()) {
            leftFront.setPower(0);
            telemetry.addLine("");
        }

        if (gamepad1.yWasPressed()) {
            rightFront.setPower(0.5);
            telemetry.addLine("Right Front");
        }

        if (gamepad1.yWasReleased()) {
            rightFront.setPower(0);
            telemetry.addLine("");
        }

        if (gamepad1.aWasPressed()) {
            leftBack.setPower(0.5);
            telemetry.addLine("Left Back");
        }

        if (gamepad1.aWasReleased()) {
            leftBack.setPower(0);
            telemetry.addLine("");
        }

        if (gamepad1.bWasPressed()) {
            rightBack.setPower(0.5);
            telemetry.addLine("Right Back");
        }

        if (gamepad1.bWasReleased()) {
            rightBack.setPower(0);
            telemetry.addLine("");
        }
        telemetry.update();
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {}
}